# 定义二叉树节点类
class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

class Solution:
    def closestValue(self, root: TreeNode, target: float) -> int:
        closest = root.val
        
        current = root
        while current:
            # 更新最接近的值
            if abs(current.val - target) < abs(closest - target):
                closest = current.val
            
            # 根据目标值移动到左子树或右子树
            if target < current.val:
                current = current.left
            elif target > current.val:
                current = current.right
            else:
                break  # 如果目标值等于当前节点值，直接返回
        
        return closest

# 辅助函数，用于创建二叉搜索树
def insert_into_bst(root, val):
    if not root:
        return TreeNode(val)
    if val < root.val:
        root.left = insert_into_bst(root.left, val)
    else:
        root.right = insert_into_bst(root.right, val)
    return root

def create_bst_from_list(values):
    root = None
    for val in values:
        root = insert_into_bst(root, val)
    return root

# 测试用例
values = [4, 2, 5, 1, 3]
target = 3.714286
root = create_bst_from_list(values)

# 创建Solution对象并调用closestValue方法
solution = Solution()
result = solution.closestValue(root, target)

print(f"最接近目标值 {target} 的数值是: {result}")
