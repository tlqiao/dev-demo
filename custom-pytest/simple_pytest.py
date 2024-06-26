import os
import importlib.util
import inspect
import traceback
import argparse

# 发现测试文件
def discover_tests(start_dir):
    test_files = []
    for root, _, files in os.walk(start_dir):
        for file in files:
            if file.startswith('test_') and file.endswith('.py'):
                test_files.append(os.path.join(root, file))
    return test_files

# 查找测试函数
def find_test_functions(module):
    test_functions = []
    for name, obj in inspect.getmembers(module):
        if inspect.isfunction(obj) and name.startswith('test_'):
            test_functions.append(obj)
    return test_functions

# 运行测试函数
def run_tests(test_functions):
    results = []
    for test_func in test_functions:
        result = {'name': test_func.__name__}
        try:
            test_func()
            result['status'] = 'pass'
        except AssertionError as e:
            result['status'] = 'fail'
            result['error'] = traceback.format_exc()
        except Exception as e:
            result['status'] = 'error'
            result['error'] = traceback.format_exc()
        results.append(result)
    return results

# 打印测试结果
def print_results(results):
    for result in results:
        print(f'Test: {result["name"]} - {result["status"]}')
        if result.get('error'):
            print(result['error'])
        print('-' * 40)

# 主函数
def main():    
    parser = argparse.ArgumentParser(description='A simple pytest-like tool')
    parser.add_argument('test_path', type=str, help='Path to the test file or directory')

    args = parser.parse_args()
    test_path = args.test_path

    if os.path.isdir(test_path):
        test_files = discover_tests(test_path)
    elif os.path.isfile(test_path):
        test_files = [test_path]
    else:
        print(f"Invalid path: {test_path}")
        exit(1)

    for test_file in test_files:
        # 根据测试文件路径创建模块规范
        spec = importlib.util.spec_from_file_location("module.name", test_file)
        # 根据模块规范创建一个模块对象
        module = importlib.util.module_from_spec(spec)
        # 加载并执行模块代码
        spec.loader.exec_module(module)
        # 在模块中查找测试函数
        test_functions = find_test_functions(module)
        # 运行所有找到的测试函数，并记录结果
        results = run_tests(test_functions)
        # 输出测试结果
        print_results(results)

if __name__ == '__main__':
    main()        
