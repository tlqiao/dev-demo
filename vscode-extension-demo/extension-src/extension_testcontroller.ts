import * as vscode from 'vscode';
import * as fs from 'fs';
import * as path from 'path';

export function activate(context: vscode.ExtensionContext) {
        const testController = vscode.tests.createTestController('markdownTestController', 'Markdown Tests');
        context.subscriptions.push(testController);
        context.subscriptions.push(vscode.workspace.onDidChangeTextDocument(e => {
                loadTestsFromDocument(testController, e.document);
        }));
        context.subscriptions.push(vscode.workspace.onDidOpenTextDocument(doc => {

                loadTestsFromDocument(testController, doc);

        }));

        vscode.workspace.textDocuments.forEach(doc => {
                loadTestsFromDocument(testController, doc);
        });

        vscode.commands.registerCommand('markdownTestController.runTests', async () => {
                const tests = Array.from(testController.items)
                const results: string[] = [];
                for (const [, testItem] of tests) {
                        vscode.window.showInformationMessage(JSON.stringify(testItem));
                        const resultMessage = await runTest(testItem);
                        results.push(resultMessage);

                }
                if (results.length > 0) {
                        vscode.window.showInformationMessage(results.join('\n'));
                } else {
                        vscode.window.showInformationMessage('No tests executed.');
                }
        });
}

function loadTestsFromDocument(testController: vscode.TestController, document: vscode.TextDocument) {
        const tests = parseTests(document.getText());
        for (const test of tests) {
                const testItem = testController.createTestItem(test.name, test.expression + "=" + test.expected);
                testController.items.add(testItem);
        }
}

interface Test {
        name: string;
        expression: string;
        expected: number;
}

function parseTests(text: string): Test[] {
        const testRegex = /^(\d+ \+ \d+) = (\d+) \/\/ (.+)$/gm;
        const tests: Test[] = [];
        let match;

        while ((match = testRegex.exec(text)) !== null) {
                tests.push({
                        name: match[3],
                        expression: match[1],
                        expected: parseInt(match[2])
                });
        }

        return tests;
}

async function runTest(testItem: vscode.TestItem) {
        const expression = testItem.label.split('=')[0].trim();
        const expected = parseInt(testItem.label.split('=')[1].trim());
        const actual = eval(expression);
        const result = actual === expected;

        if (result) {
                testItem.busy = false;
                return `${testItem.label}: PASSED`;
        } else {
                testItem.busy = false;
                return `${testItem.label}: FAILED`;
        }
}
