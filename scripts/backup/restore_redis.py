import redis
import json

host = "114.55.174.108"  # redis服务地址
port = 6379  # redis服务端口
password = "EUMbnnhVDr9gRtET3uyhCxZxq09yGXaX"

r = redis.StrictRedis(host=host, port=port, password=password, db=0)

with open("test.json", "r") as f:
    data = f.read()
    json_data_list = json.loads(data)
    for json_data in json_data_list:
        item = (json_data["key"], json_data["ua"])
        key = item[0].replace("device:", "")
        print(item)
        r.set(
            item[0],
            '{"@type":"com.moyin.tts.domain.MoyinLoginDevice","browser":"Chrome 12","device":"Computer","ip":"127.0.0.1","loginTime":"2024-08-14 19:18:18.212","online":true,"os":"Windows 10","userAgent":#{userAgent},"userId":"#{userId}"}'.replace(
                "#{userAgent}", item[1]
            ).replace(
                "#{userId}", key 
            ),
        )
