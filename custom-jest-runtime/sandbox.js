import { Script, createContext } from 'vm';
import assert from 'assert';
export const runInSandbox = (code, context = {}) => {
    const sandbox = createContext({
        ...context,
        assert,
        console,
        setTimeout,
        setInterval,
        clearTimeout,
        clearInterval,
        Buffer,
        process,
    });

    const script = new Script(code);
    script.runInContext(sandbox);
};
