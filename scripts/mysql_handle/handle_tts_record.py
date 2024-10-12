import json
import mysql.connector


def calc_text_size(s):
    length = 0
    for char in s:
        if ord(char) < 128:
            length += 1
        else:
            length += 2
    return length


connection = mysql.connector.connect(
    host="114.55.174.108",  # 数据库主机地址
    user="moyin",  # 数据库用户名
    password="VSf7FCiS3ltrxFzDpnGxwBuQetxU7J5Y",  # 数据库密码
    database="spring_moyin",  # 数据库名
)
cursor = connection.cursor(dictionary=True)

sql = "select * from moyin_tts_record"
cursor.execute(sql)
data_list = cursor.fetchall()
for data in data_list:
    text_size = calc_text_size(data["text"])
    sql = f"update moyin_tts_record set text_length={text_size} where id={data['id']}"
    print(sql)
    cursor.execute(sql)
    
connection.commit()

