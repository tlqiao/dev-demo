import * as babel from '@babel/core';
import { readFileSync } from 'fs';

export const loadModule = (filePath) => {
    const code = readFileSync(filePath, 'utf8');
    const { code: transformedCode } = babel.transformSync(code, {
        presets: ['@babel/preset-env'],
    });
    return transformedCode;
};
