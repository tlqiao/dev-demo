import * as vscode from 'vscode';
import * as child_process from 'child_process';

export function activate(context: vscode.ExtensionContext) {
        const testController = vscode.tests.createTestController('jestTestController', 'Jest Tests');
        context.subscriptions.push(testController);
        context.subscriptions.push(vscode.commands.registerCommand('extension.runJestTests', async () => {
                await runAllTests(testController);
        }));

        async function runAllTests(testController: vscode.TestController) {
                const testItems: vscode.TestItem[] = [];
                testController.items.forEach(testItem => testItems.push(testItem));
                const request = new vscode.TestRunRequest(testItems);
                const run = testController.createTestRun(request);
                let allTestsPassed = true;
                const testResults: { [key: string]: boolean } = {};
                for (const test of testItems) {
                        run.started(test);
                        const result = await runJestTest(test);
                        testResults[test.id] = result;
                        if (result) {
                                run.passed(test);
                        } else {
                                run.failed(test, new vscode.TestMessage('Test failed'));
                                allTestsPassed = false;
                        }
                }
                run.end();
                if (allTestsPassed) {
                        vscode.window.showInformationMessage('All tests passed.');
                } else {
                        vscode.window.showInformationMessage('Some tests failed. Check test results for details.');
                }
        }

        context.subscriptions.push(vscode.workspace.onDidOpenTextDocument(doc => {
                if (doc.languageId === 'typescript' || doc.languageId === 'javascript') {
                        loadTestsFromDocument(testController, doc);
                }
        }));

        vscode.workspace.textDocuments.forEach(doc => {
                if (doc.languageId === 'typescript' || doc.languageId === 'javascript') {
                        loadTestsFromDocument(testController, doc);
                }
        });
}

function loadTestsFromDocument(testController: vscode.TestController, document: vscode.TextDocument) {
        const tests = parseTests(document.getText());
        for (const test of tests) {
                const testItem = testController.createTestItem(test.name, test.name, document.uri);
                testController.items.add(testItem);
        }
}

interface Test {
        name: string;
}

function parseTests(text: string): Test[] {
        const testRegex = /test\(['"](.+)['"]/g;
        const tests: Test[] = [];
        let match;

        while ((match = testRegex.exec(text)) !== null) {
                tests.push({
                        name: match[1]
                });
        }

        return tests;
}

async function runJestTest(testItem: vscode.TestItem): Promise<boolean> {
        return new Promise((resolve) => {
                const options = {
                        cwd: vscode.workspace.workspaceFolders ? vscode.workspace.workspaceFolders[0].uri.fsPath : undefined,
                };
                child_process.exec(`npx jest -t "${testItem.label}" --json`, options, (err, stdout, stderr) => {
                        if (err) {
                                console.error(stderr);
                                vscode.window.showErrorMessage(`Test failed to run: ${stderr}`);
                                resolve(false);
                        } else {
                                try {
                                        const result = JSON.parse(stdout);
                                        vscode.window.showInformationMessage(`${testItem.label}: ${result.numFailedTests === 0 ? 'Passed' : 'Failed'}`);
                                        resolve(result.numFailedTests === 0);
                                } catch (parseError) {
                                        console.error(`Failed to parse test result: ${parseError}`);
                                        vscode.window.showErrorMessage(`Failed to parse test result: ${parseError}`);
                                        resolve(false);
                                }
                        }
                });
        });
}

export function deactivate() { }
