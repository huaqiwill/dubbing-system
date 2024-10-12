"""
导入魔音的语言数据
"""

import requests
import mysql.connector


class ImportMoyinLanguage:
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

    def insert_language(self, name, value):
        sql = "insert into moyin_language(name,value,status) values(%s,%s,1)"
        data = (name, value)
        print(sql, data)
        self.cursor.execute(sql, data)

    def run(self):
        self.connect()
        self.cursor.execute("delete from moyin_language")

        json_data = self.get_data()
        print(json_data)
        for item in json_data:
            self.insert_language(
                item["name"],
                item["value"],
            )
        self.close()

    def get_data(self):
        url = "https://openapi.mobvoi.com/apis/tts-web-api/v1/storeSpeakerV2/getStoreSearchCriteria"
        response = requests.get(url)
        return response.json()["data"]["全部语言:specificLanguage"]


if __name__ == "__main__":
    ImportMoyinLanguage().run()
