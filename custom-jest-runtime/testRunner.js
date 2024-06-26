import { runInSandbox } from './sandbox.js';

const testCode = `
  const sum = (a, b) => a + b;
  function test(name, fn) {
    try {
      fn();
      console.log(\`Test passed: \${name}\`);
    } catch (error) {
      console.error(\`Test failed: \${name}\`);
      console.error(error);
    }
  }
    test('adds 1 + 2 to equal 3', () => {
    assert.strictEqual(sum(1, 2), 3);
  });
`;

// 上下文对象，包括 expect
const context = {}
;

// 在沙盒环境中执行测试代码
runInSandbox(testCode, context);
