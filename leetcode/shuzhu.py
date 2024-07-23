def two_sum(nums, target):
    # 创建一个哈希表来存储数值和对应的索引
    num_to_index = {}
    
    # 遍历数组
    for index, num in enumerate(nums):
        # 计算目标值与当前数的差值
        complement = target - num
        # 检查这个差值是否在哈希表中
        if complement in num_to_index:
            # 如果存在，返回它们的索引
            return [num_to_index[complement], index]
        # 如果不存在，把当前数和它的索引存入哈希表
        num_to_index[num] = index
    # 如果没有找到满足条件的两个数，返回空数组
    return []

# 示例
nums = [11,15,2,7]
target = 9
print(two_sum(nums, target))  # 输出: [0, 1]
