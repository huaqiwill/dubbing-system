import redis
import json
import mysql.connector

host = "114.55.174.108"  # redis服务地址
port = 6379  # redis服务端口
password = "EUMbnnhVDr9gRtET3uyhCxZxq09yGXaX"

r = redis.StrictRedis(host=host, port=port, password=password, db=0)
keys = r.keys("device:*")
print(keys)

connection = mysql.connector.connect(
    host="114.55.174.108",  # 数据库主机地址
    user="moyin",  # 数据库用户名
    password="VSf7FCiS3ltrxFzDpnGxwBuQetxU7J5Y",  # 数据库密码
    database="spring_moyin",  # 数据库名
)
cursor = connection.cursor(dictionary=True)

for key in keys:
    print("======" + key.decode("utf-8"))
    data = r.get(key).decode("utf-8")
    item = json.loads(data)
    sql = "select user_name from sys_user where user_id=" + str(item["userId"])
    cursor.execute(sql)
    results = cursor.fetchone()
    if results:
        print(sql)
        item["userName"] = results.get("user_name")
        print(item)
        r.set(key.decode("utf-8"), json.dumps(item))
