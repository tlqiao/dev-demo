class Person:
    def __init__(self, name, age, gender):
        self.name = name  # 姓名
        self.age = age    # 年龄
        self.gender = gender  # 性别

    def greet(self):
        # 打招呼的方法
        print(f"Hello, my name is {self.name} and I am {self.age} years old.")

    def celebrate_birthday(self):
        # 庆祝生日的方法，增加年龄
        self.age += 1
        print(f"Happy {self.age}th Birthday, {self.name}!")

    def introduce(self):
        # 自我介绍的方法
        print(f"I am {self.name}, a {self.gender} who is {self.age} years old.")

# 使用Person类创建一个实例
person = Person("Alice", 30, "female")

# 调用Person类的方法
person.greet()
person.celebrate_birthday()
person.introduce()

# in another python File

from person import Person  # 从person模块导入Person类

# 使用Person类
person1 = Person("Alice", 30)
person1.greet()