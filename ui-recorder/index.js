import express from 'express';
import http from 'http';
import { Server } from 'socket.io';
import puppeteer from 'puppeteer';

const app = express();
const server = http.createServer(app);
const io = new Server(server);

app.use(express.static('public'));

io.on('connection', (socket) => {
    socket.on('start-recording', async () => {
        const browser = await puppeteer.launch({ headless: false });
        const page = await browser.newPage();
        await page.exposeFunction('emitClickEvent', (selector) => {
            socket.emit('record-action', `await page.click('${selector}');\n`);
        });
        await page.exposeFunction('emitHoverSelector', (selector) => {
            socket.emit('record-selector', selector);
        });
        await page.evaluateOnNewDocument(() => {
            window.socket = {
                emit: (event, data) => {
                    if (event === 'click')
                    {
                        window.emitClickEvent(data);
                    } else if (event === 'hover') {
                        window.emitHoverSelector(data);
                    }
                }
            };
        });
        await page.goto('https://angularjs.realworld.io/#/login');
        await page.evaluate(() => {
            let path = [];
            const tooltip = document.createElement('div');
            tooltip.style.position = 'absolute';
            tooltip.style.background = 'rgba(0, 0, 0, 0.7)';
            tooltip.style.color = '#fff';
            tooltip.style.padding = '5px';
            tooltip.style.borderRadius = '3px';
            tooltip.style.zIndex = '9999';
            tooltip.style.display = 'none';
            document.body.appendChild(tooltip);
            document.addEventListener('mouseover', (event) => {
                const path = [];
                let el = event.target;
                while (el) {
                    let selector = el.nodeName.toLowerCase();
                    if (el.id) {
                        selector += `#${el.id}`;
                    } else if (el.className) {
                        selector += `.${Array.from(el.classList).join('.')}`;
                    } else {
                        let sib = el, nth = 1;
                        while (sib = sib.previousElementSibling) {
                            if (sib.nodeName.toLowerCase() === selector) nth++;
                        }
                        selector += `:nth-of-type(${nth})`;
                    }
                    path.unshift(selector);
                    el = el.parentNode;
                }
                const selectorPath = path.join(' > ');
                tooltip.innerText = selectorPath;
                tooltip.style.top = `${event.pageY}px`;
                tooltip.style.left = `${event.pageX}px`;
                tooltip.style.display = 'block';
                window.socket.emit('hover', selectorPath);
            });

            // Hide tooltip on mouseout
            document.addEventListener('mouseout', () => {
                tooltip.style.display = 'none';
            });
            document.addEventListener('click', (event) => {
                let el = event.target;
                while (el) {
                    let selector = el.nodeName.toLowerCase();
                    if (el.id) {
                        selector += `#${el.id}`;
                    } else if (el.className) {
                        selector += `.${Array.from(el.classList).join('.')}`;
                    } else {
                        let sib = el, nth = 1;
                        while (sib = sib.previousElementSibling) {
                            if (sib.nodeName.toLowerCase() === selector) nth++;
                        }
                        selector += `:nth-of-type(${nth})`;
                    }
                    path.unshift(selector);
                    el = el.parentNode;
                }
                window.socket.emit('click', path.join(' > '));
            }, true);
        });

        socket.on('disconnect', async () => {
            await browser.close();
            console.log('Client disconnected');
        });
    });
});

server.listen(3001, () => {
    console.log('Listening on port 3001');
});
