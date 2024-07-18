from tree_sitter import Language, Parser
import os

# 构建解析器库
# Language.build_library(
#     'build/my-languages.so',
#     [
#         '/Users/taoli/study/py-tree-sitter/',
#     ]
# )

# 加载语言
PYTHON_LANGUAGE = Language('build/tree_sitter_python.so', 'python')

# 创建解析器
parser = Parser()
parser.set_language(PYTHON_LANGUAGE)  # 解析 Python 代码
# parser.set_language(C_LANGUAGE)  # 解析 C 代码

def parse_code(code, language):
    parser = Parser()
    parser.set_language(language)
    tree = parser.parse(bytes(code, "utf8"))
    return tree

def extract_entities(tree):
    root_node = tree.root_node
    entities = []

    def visit(node):
        if node.type == 'function_definition':  # Python 中的函数定义节点类型
            func_name = node.child_by_field_name('name')
            entities.append({
                'type': 'function_definition',
                'name': func_name.text.decode('utf-8'),
                'start': node.start_point,
                'end': node.end_point
            })
        # 递归访问子节点
        for child in node.children:
            visit(child)

    visit(root_node)
    return entities

# 解析代码
code = """
def hello_world():
    print("Hello, world!")
"""
tree = parse_code(code, PYTHON_LANGUAGE)
entities = extract_entities(tree)
print(entities)
