"""
密码生成工具
密码长度: 32位
"""

import secrets
import string
import random


def generate_password(length=32):
    characters = string.ascii_letters + string.digits
    password = "".join(random.choice(characters) for _ in range(length))
    return password


def generate_strong_password(length=32):
    """
    生成一个强密码，包括字母（大写和小写）、数字和特殊字符。

    :param length: 密码长度，默认32
    :return: 生成的强密码
    """
    if length < 8:
        raise ValueError("密码长度不能少于8个字符")

    # 定义密码字符集
    characters = string.ascii_letters + string.digits + string.punctuation
    password = "".join(secrets.choice(characters) for _ in range(length))

    return password


def main():
    # 用户输入密码长度
    try:
        length = int(input("请输入密码长度（至少8个字符）："))
        password = generate_password(length)
        print(f"生成的强密码：{password}")
    except ValueError as ve:
        print(f"输入错误: {ve}")


if __name__ == "__main__":
    main()
