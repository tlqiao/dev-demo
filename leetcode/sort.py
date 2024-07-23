# list.sort() 是一个方法，只能用于列表（list）。
# sorted() 是一个内置函数，可以对任何可迭代对象进行排序，包括列表、元组、字典、集合等。
# 原地排序 vs 返回新对象:

# list.sort() 对原列表进行排序，不创建新的列表对象。这意味着排序后原列表的内容会被改变。
# sorted() 对任何可迭代对象进行排序，并返回一个新的列表。原始的可迭代对象不会被修改。
# 返回值:

# list.sort() 没有返回值（或者说返回 None），它修改原列表并直接在原列表上进行排序。
# sorted() 返回一个新的列表，这个列表是排序后的结果。

#sort list
sorted([3, 1, 2])
#sort trup,return list type
print(type(sorted((3, 1, 2))))
#sort string
sorted("bca")

result = sorted({'b': 1, 'c': 2, 'a': 3}.items())
for key, value in result:
        print(key)
        print(value)
# 默认排序是对字典的key进行排序,且返回字典类型
sorted_by_key = dict(sorted(my_dict.items(), key=lambda item: item[0]))
sorted_by_value = dict(sorted(my_dict.items(), key=lambda item: item[1]))

#对集合进行排序
sorted({3, 1, 2}) 

# lambda 参数: 表达式
add = lambda x, y: x + y
print(add(3, 5))  # 输出: 8

# 按每个元组的第二个元素排序
data = [(1, 3), (4, 2), (2, 1)]
sorted_data = sorted(data, key=lambda x: x[1])
print(sorted_data)  # 输出: [(2, 1), (4, 2), (1, 3)]

