# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution:
    def deleteNodes(self, head: ListNode, m: int, n: int) -> ListNode:
        current = head
        
        while current:
            # 保留m个节点
            for _ in range(1, m):
                if current is None:
                    return head
                current = current.next
            
            if current is None:
                break

            # 删除n个节点
            to_delete = current.next
            for _ in range(n):
                if to_delete is None:
                    break
                to_delete = to_delete.next
            
            # 调整指针
            current.next = to_delete
            current = to_delete
        
        return head

# 辅助函数，用于创建链表和打印链表
def create_linked_list(arr):
    if not arr:
        return None
    head = ListNode(arr[0])
    current = head
    for val in arr[1:]:
        current.next = ListNode(val)
        current = current.next
    return head

def print_linked_list(head):
    current = head
    while current:
        print(current.val, end=" -> ")
        current = current.next
    print("None")

# 测试用例
head_arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]
m, n = 2, 3
head = create_linked_list(head_arr)

# 打印原始链表
print("原始链表:")
print_linked_list(head)

# 创建Solution对象并调用deleteNodes方法
solution = Solution()
new_head = solution.deleteNodes(head, m, n)

# 打印修改后的链表
print("修改后的链表:")
print_linked_list(new_head)
