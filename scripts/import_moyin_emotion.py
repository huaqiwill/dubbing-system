""" 2024.8.5
导入魔音的情绪数据
"""

import requests
import mysql.connector

# 连接到MySQL数据库


class ImportMoyinEmotion:

    def connect(self):
        self.connection = mysql.connector.connect(
            host="localhost",  # 数据库主机地址
            user="root",  # 数据库用户名
            password="123456",  # 数据库密码
            database="spring_moyin",  # 数据库名
        )
        self.cursor = self.connection.cursor()

    def close(self):
        self.connection.commit()
        self.cursor.close()
        self.connection.close()

    def insert_emotion(self, image_url, emotion_name, emotion_value):
        sql = "insert into moyin_emotion(image_url,emotion_name,emotion_value,emotion_status,display_order) values(%s,%s,%s,1,1)"
        data = (image_url, emotion_name, emotion_value)
        self.cursor.execute(sql, data)
        print(sql, data)

    def need_clear(self):
        sql = "delete from moyin_emotion"
        self.cursor.execute(sql)

    def get_data(self):
        # 发送 GET 请求
        res = requests.get(
            "https://openapi.mobvoi.com/apis/tts-web-api/v1/speaker/getSpeakerEmotionList",
        )

        # 检查请求是否成功
        if res.status_code != 200:
            print(f"请求失败，状态码：{res.status_code}")
            print(res.text)  # 打印原始响应内容以进行调试

        json_data = res.json()
        return json_data

    def run(self, need_clear=False):
        self.connect()

        if need_clear:
            self.need_clear()

        json_data = self.get_data()
        for item in json_data["data"]:
            self.insert_emotion(item["imageUrl"], item["emotionName"], item["emotion"])

        self.close()


if __name__ == "__main__":
    ImportMoyinEmotion().run(True)
