"""
导入魔音的年龄数据到数据库
"""
import requests
import mysql.connector


class ImportMoyinage:
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

    def insert_age(self, name, value):
        sql = "insert into moyin_age(name,value,status) values(%s,%s,1)"
        data = (name, value)
        print(sql, data)
        self.cursor.execute(sql, data)

    def run(self):
        self.connect()
        self.cursor.execute("delete from moyin_age")

        json_data = self.get_data()
        print(json_data)
        for item in json_data:
            self.insert_age(
                item["name"],
                item["value"],
            )
        self.close()

    def get_data(self):
        url = "https://openapi.mobvoi.com/apis/tts-web-api/v1/storeSpeakerV2/getStoreSearchCriteria"
        response = requests.get(url)
        return response.json()["data"]["全部年龄:age"]


if __name__ == "__main__":
    ImportMoyinage().run()
