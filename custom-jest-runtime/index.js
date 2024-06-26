import { findTestFiles } from './fileFinder.js';
import { runTestFile } from './testRuntime.js';

const testFiles = findTestFiles(new URL('./tests', import.meta.url).pathname);
testFiles.forEach(runTestFile);
