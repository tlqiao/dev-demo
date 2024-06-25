const WebSocket = require('ws');
const { spawn } = require('child_process');
const path = require('path');

// WebSocket server setup
const wss = new WebSocket.Server({ port: 8085 });

// Event listener for new connections
wss.on('connection', ws => {
        console.log('Client connected');

        // Event listener for incoming messages
        ws.on('message', message => {
                console.log(`Received message: ${message}`);

                // Parse the received message
                let command;
                try {
                        command = JSON.parse(message);
                } catch (e) {
                        console.error('Invalid JSON:', e);
                        ws.send(JSON.stringify({ error: 'Invalid JSON' }));
                        return;
                }

                // Check if the command is "runtest"
                if (command.action === 'runtest') {
                        const testFilePath = command.testFilePath;
                        const options = command.options;

                        // Construct the Playwright test command
                        const nodePath = '/opt/homebrew/bin/node';
                        const cliPath = path.resolve('./node_modules/@playwright/test/cli.js');
                        const configPath = 'playwright.config.js';

                        const args = [
                                cliPath,
                                'test',
                                '-c', configPath,
                                testFilePath,
                                `--headed`,
                                `--project=${options.project}`,
                                `--repeat-each=${options.repeatEach}`,
                                `--retries=${options.retries}`,
                                `--timeout=${options.timeout}`,
                                `--workers=${options.workers}`
                        ];

                        console.log('Executing command:', `${nodePath} ${args.join(' ')}`);

                        // Spawn the Playwright test process
                        const testProcess = spawn(nodePath, args, { stdio: 'pipe' });

                        // Capture stdout and stderr
                        testProcess.stdout.on('data', data => {
                                console.log(`stdout: ${data}`);
                                ws.send(JSON.stringify({ output: data.toString() }));
                        });

                        testProcess.stderr.on('data', data => {
                                console.error(`stderr: ${data}`);
                                ws.send(JSON.stringify({ error: data.toString() }));
                        });

                        // Handle process exit
                        testProcess.on('close', code => {
                                console.log(`Child process exited with code ${code}`);
                                ws.send(JSON.stringify({ exitCode: code }));
                        });
                } else {
                        ws.send(JSON.stringify({ error: 'Unknown action' }));
                }
        });

        // Event listener for connection close
        ws.on('close', () => {
                console.log('Client disconnected');
        });
});

console.log('WebSocket server is running on ws://localhost:8085');
