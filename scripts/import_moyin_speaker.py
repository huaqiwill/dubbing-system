"""
导入魔音的配音员数据
"""

import requests
import mysql.connector


class ImportMoyinSpeaker:
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

    def insert_speaker(
        self,
        age,
        behavior,
        gender,
        headerImage,
        name,
        speaker,
    ):
        gender = 1 if gender == "FEMALE" else 0
        sql = "insert into moyin_speaker(name,header_image,behavior,gender,age,style_call_name) values(%s,%s,%s,%s,%s,%s)"
        data = (name, headerImage, behavior, gender, age, speaker)
        print(sql, data)
        self.cursor.execute(sql, data)

    def run(self):
        self.connect()
        self.cursor.execute("delete from moyin_speaker")

        json_data = self.get_data()
        for item in json_data:
            self.insert_speaker(
                item["age"],
                item["behavior"],
                item["gender"],
                item["headerImage"],
                item["name"],
                item["speaker"],
            )
        self.close()

    def get_data(self):
        url = "https://openapi.mobvoi.com/open-apis/speaker/searchSpeakers?pageSize=663&pageNum=1"
        response = requests.get(url)
        return response.json()["data"]["results"]


if __name__ == "__main__":
    ImportMoyinSpeaker().run()
