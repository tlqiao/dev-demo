import { runInSandbox } from './sandbox.js';
import { loadModule } from './moduleLoader.js';

export const runTestFile = (testFile) => {
    const code = loadModule(testFile);
    const context = {
        test: (name, fn) => {
            try {
                fn();
                console.log(`Test passed: ${name}`);
            } catch (error) {
                console.log(`Test failed: ${name}`);
                console.error(error);
            }
        },
    };
    runInSandbox(code, context);
};
