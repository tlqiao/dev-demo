import { remote } from 'webdriverio';
import fs from 'fs';
import xml2js from 'xml2js';
import express from 'express';
import cors from 'cors';
import path from 'path';
import { fileURLToPath } from 'url';

// 获取当前文件的目录名
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
// 加载配置文件
const config = JSON.parse(fs.readFileSync('./src/config.json', 'utf-8'));
// 配置连接参数
const opts = {
    path: '/',
    port: 4723,
    capabilities: {
        'appium:platformName': config.platformName,
        'appium:platformVersion': config.platformVersion,
        'appium:deviceName': config.deviceName,
        'appium:app': config.app,
        'appium:automationName': config.automationName,
        'appium:appWaitActivity':config.appActivity
    },
};

const app = express();
app.use(cors());
app.use(express.json());
app.use(express.static(path.join(__dirname, 'public')));
let client;


const initializeAppiumClient = async () => {
    try {
        client = await remote(opts);
        console.log('Connected to Appium server');
    } catch (err) {
        console.error('Failed to connect to Appium server:', err);
    }
};
//解决session过期的问题
const ensureClient = async () => {
    if (!client) {
        await initializeAppiumClient();
    } else {
        try {
            await client.status();
        } catch (err) {
            if (err.message.includes('invalid session id')) {
                console.log('Session expired, reinitializing Appium client');
                await initializeAppiumClient();
            } else {
                throw err;
            }
        }
    }
};


app.get('/page-source', async (req, res) => {
    try {
        await ensureClient();
        // 获取页面源代码
        const pageSource = await client.getPageSource();
        const parser = new xml2js.Parser();
        const result = await parser.parseStringPromise(pageSource);
        res.json(result);
    } catch (err) {
        console.error('Error occurred:', err);
        res.status(500).send('Error occurred');
    }
});

app.get('/screenshot', async (req, res) => {
    try {
        await ensureClient();
        // 获取截图
        const screenshot = await client.takeScreenshot();
        res.send(screenshot);
    } catch (err) {
        console.error('Error occurred:', err);
        res.status(500).send('Error occurred');
    }
});

app.post('/tap', async (req, res) => {
    try {
        await ensureClient();
        const { x, y } = req.body;
        await client.touchAction({
            action: 'tap',
            x,
            y
        });
        res.send({ status: 'success', x, y });
    } catch (err) {
        console.error('Error occurred while tapping element:', err);
        res.status(500).send('Error occurred');
    }
});

app.listen(9096, async() => {
    await initializeAppiumClient();
    console.log('Appium Inspector server running at http://localhost:9096');
});

process.on('exit', async () => {
    if (client) {
        await client.deleteSession();
        console.log('Appium client session closed');
    }
});
