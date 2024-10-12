import redis
import json

host = "114.55.174.108"  # redis服务地址
port = 6379  # redis服务端口
password = "EUMbnnhVDr9gRtET3uyhCxZxq09yGXaX"

r = redis.StrictRedis(host=host, port=port, password=password, db=0)
keys = r.keys("device:*")
print(keys)

with open("test.json", "w") as f:
    data_list = []
    for key in keys:
        data = r.get(key.decode("utf-8"))
        print(data.decode("utf-8"))
        data_list.append({"key": key.decode("utf-8"), "ua": data.decode("utf-8") + "\n"})
    f.write(json.dumps(data_list))
