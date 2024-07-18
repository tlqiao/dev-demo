import os
from collections import defaultdict, Counter
from pathlib import Path
import networkx as nx
from tree_sitter import Language, Parser

# 加载编译的tree-sitter解析器
Language.build_library(
    'build/my-languages.so',
    [
        '/Users/taoli/study/py-tree-sitter',
    ]
)

PYTHON_LANGUAGE = Language('build/my-languages.so', 'python')

class RepoMap:
    CACHE_VERSION = 3
    TAGS_CACHE_DIR = f".aider.tags.cache.v{CACHE_VERSION}"

    def __init__(self, root=None, verbose=False):
        self.verbose = verbose

        if not root:
            root = os.getcwd()
        self.root = root

        self.load_tags_cache()

    def load_tags_cache(self):
        path = Path(self.root) / self.TAGS_CACHE_DIR
        if not path.exists():
            self.cache_missing = True
        self.TAGS_CACHE = {}

    def get_tags(self, fname):
        # 使用tree-sitter解析Python代码文件
        parser = Parser()
        parser.set_language(PYTHON_LANGUAGE)

        with open(fname, 'r') as f:
            code = f.read()
        
        tree = parser.parse(bytes(code, 'utf8'))

        # 获取根节点
        root_node = tree.root_node

        tags = []

        # 遍历树，提取函数定义和变量引用
        def extract_tags(node, tags):
            if node.type == 'function_definition':
                function_name_node = node.child_by_field_name('name')
                if function_name_node:
                    tags.append({
                        'name': function_name_node.text.decode('utf8'),
                        'kind': 'def',
                        'line': node.start_point[0] + 1  # 行号从0开始，需要加1
                    })
            elif node.type == 'identifier':
                tags.append({
                    'name': node.text.decode('utf8'),
                    'kind': 'ref',
                    'line': node.start_point[0] + 1
                })

            # 递归遍历子节点
            for child in node.children:
                extract_tags(child, tags)

        extract_tags(root_node, tags)

        return tags

    def get_ranked_tags(self, chat_fnames, other_fnames):
        defines = defaultdict(set)
        references = defaultdict(list)

        for fname in chat_fnames + other_fnames:
            tags = self.get_tags(fname)
            for tag in tags:
                if tag['kind'] == "def":
                    defines[tag['name']].add(fname)
                elif tag['kind'] == "ref":
                    references[tag['name']].append(fname)

        G = nx.MultiDiGraph()
        for ident in defines:
            definers = defines[ident]
            referencers = references.get(ident, [])
            for definer in definers:
                for referencer in referencers:
                    G.add_edge(referencer, definer, weight=1)

        ranked = nx.pagerank(G, weight="weight")
        ranked_files = sorted(ranked.items(), key=lambda x: x[1], reverse=True)

        return ranked_files

    def get_repo_map(self, chat_files, other_files):
        ranked_tags = self.get_ranked_tags(chat_files, other_files)
        return ranked_tags