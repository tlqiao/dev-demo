import importlib.util
# 获取模块文件路径
file_path = "example_module.py"
# 创建模块规范对象
spec = importlib.util.spec_from_file_location("example_module", file_path)
# 打印ModuleSpec对象的信息
print("ModuleSpec Information:")
print(f"Name: {spec.name}")
print(f"Loader: {spec.loader}")
print(f"Origin: {spec.origin}")
print(f"Has Location: {spec.has_location}")
print(f"Submodule Search Locations: {spec.submodule_search_locations}")
# 创建模块对象
module = importlib.util.module_from_spec(spec)
# 加载并执行模块
spec.loader.exec_module(module)
# 调用模块中的函数
module.hello()
module.test_addition()
module.test_failure()
