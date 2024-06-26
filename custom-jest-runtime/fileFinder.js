import { readdirSync, statSync } from 'fs';
import { join } from 'path';

export const findTestFiles = (dir, testFiles = []) => {
    const files = readdirSync(dir);
    files.forEach((file) => {
        const filePath = join(dir, file);
        if (statSync(filePath).isDirectory()) {
            findTestFiles(filePath, testFiles);
        } else if (file.endsWith('.test.js')) {
            testFiles.push(filePath);
        }
    });
    return testFiles;
};
