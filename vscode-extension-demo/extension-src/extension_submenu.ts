import * as vscode from 'vscode';
import * as path from 'path';
import { info } from './info';

class MyWebviewViewProvider implements vscode.WebviewViewProvider {
    public static readonly viewName = 'demoSidebar';
    constructor(private readonly _extensionPath: string) { }

    public resolveWebviewView(
        webviewView: vscode.WebviewView,
        context: vscode.WebviewViewResolveContext,
        _token: vscode.CancellationToken
    ) {
        webviewView.webview.options = {
            enableScripts: true,
            localResourceRoots: [vscode.Uri.file(path.join(this._extensionPath, 'dist'))]
        };

        const onDiskPath = vscode.Uri.file(
            path.join(this._extensionPath, 'dist', 'bundle.js')
        );
        const webviewUri = webviewView.webview.asWebviewUri(onDiskPath);
        webviewView.webview.html = this.getWebviewContent(webviewUri);
        webviewView.webview.onDidReceiveMessage((message: any) => {
            if (message.command === "nameChange") {
                info.name = message.content
            } else if (message.command === "ageChange") {
                info.age = message.content
            } else if (message.command === "sexChange") {
                info.sex = message.content
            }
        })

    }

    private getWebviewContent(webviewUri: vscode.Uri): string {
        return `
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Taoli Webview</title>
            </head>
            <body>
            <div id="root"></div>
               <script>
                    const vscode = acquireVsCodeApi();
                </script>
             <script src="${webviewUri}"></script>
            </body>
            </html>`;
    }
}

export function activate(context: vscode.ExtensionContext) {
    context.subscriptions.push(
        vscode.window.registerWebviewViewProvider(
            MyWebviewViewProvider.viewName,
            new MyWebviewViewProvider(context.extensionPath)
        )
    );
    context.subscriptions.push(
        vscode.commands.registerCommand('demo.showOne', () => {
            vscode.window.showInformationMessage('I am showOne.');
            vscode.window.showInformationMessage(JSON.stringify(info))
        })
    );

    context.subscriptions.push(
        vscode.commands.registerCommand('demo.showTwo', () => {
            vscode.window.showInformationMessage('I am showTwo.');
        })
    );
}

export function deactivate() { }
