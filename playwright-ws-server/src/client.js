const WebSocket = require('ws');
const path = require('path');


const ws = new WebSocket('ws://localhost:8085');

ws.on('open', () => {
        const message = {
                action: 'runtest',
                testFilePath: path.resolve('./src/tests/test1.spec.ts'),
                configPath: 'playwright.config.js',
                options: { "project": "chromium", "repeatEach": 1, "retries": 0, "timeout": 0, "workers": 1 }
        };
        ws.send(JSON.stringify(message));
});

ws.on('message', data => {
        console.log('Received:', data);
});
