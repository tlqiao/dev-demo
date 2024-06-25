import * as vscode from 'vscode';
import * as path from 'path';
import * as fs from 'fs';

export function activate(context: vscode.ExtensionContext) {
	const sampleProvider = new SampleProvider(vscode.workspace.rootPath || '');
	vscode.window.registerTreeDataProvider('sample', sampleProvider);
	vscode.commands.registerCommand('sampleExplorer.refresh', () => sampleProvider.refresh());
}

class SampleProvider implements vscode.TreeDataProvider<SampleItem> {
	private _onDidChangeTreeData: vscode.EventEmitter<SampleItem | undefined | null | void> = new vscode.EventEmitter<SampleItem | undefined | null | void>();
	readonly onDidChangeTreeData: vscode.Event<SampleItem | undefined | null | void> = this._onDidChangeTreeData.event;

	constructor(private workspaceRoot: string) { }

	refresh(): void {
		this._onDidChangeTreeData.fire();
	}

	getTreeItem(element: SampleItem): vscode.TreeItem {
		return element;
	}

	getChildren(element?: SampleItem): Thenable<SampleItem[]> {
		if (!this.workspaceRoot) {
			vscode.window.showInformationMessage('No file in empty workspace');
			return Promise.resolve([]);
		}

		const testDirPath = path.join(this.workspaceRoot, 'tests');
		if (element) {
			console.log(element.label)
			return Promise.resolve(this.getFiles(path.join(testDirPath, element.label)));
		} else {
			return Promise.resolve(this.getFiles(testDirPath));
		}
	}

	private getFiles(dir: string): SampleItem[] {
		if (!fs.existsSync(dir)) {
			vscode.window.showInformationMessage('Test directory does not exist');
			return [];
		}

		const files: string[] = fs.readdirSync(dir);
		if (files.length === 0) {
			vscode.window.showInformationMessage('No files found in test directory');
			return [];
		}

		return files.map(file => {
			const filePath = path.join(dir, file);
			const stat = fs.lstatSync(filePath);
			return new SampleItem(file, stat.isDirectory() ? vscode.TreeItemCollapsibleState.Collapsed : vscode.TreeItemCollapsibleState.None);
		});
	}
}

class SampleItem extends vscode.TreeItem {
	constructor(
		public readonly label: string,
		public readonly collapsibleState: vscode.TreeItemCollapsibleState
	) {
		super(label, collapsibleState);
	}

	iconPath = {
		light: path.join(__filename, '..', '..', 'resources', 'light', 'dependency.svg'),
		dark: path.join(__filename, '..', '..', 'resources', 'dark', 'dependency.svg')
	};
}

export function deactivate() { }
