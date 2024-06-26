const sum = (a, b) => a + b;

test('adds 1 + 2 to equal 3', () => {
    assert.strictEqual(sum(1, 2), 3);
});
