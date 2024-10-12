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
    # print("======" + key.decode("utf-8"))
    data = r.get(key).decode("utf-8")
    item = json.loads(data)
    if item.get("userName"):
        online = 1 if  item["online"] else 0  
        loginTime = item["loginTime"].split(".")[0]
        sql = f"insert into moyin_login_device(user_id,user_name,ip,user_agent,browser,os,device,login_time,online) values('{item["userId"]}','{item["userName"]}','{item["ip"]}','{item["userAgent"]}','{item["browser"]}','{item["os"]}','{item["device"]}','{loginTime}','{online}')"
        print(sql)
        cursor.execute(sql)

connection.commit()

