"""
关联魔音的配音员
"""
import re
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
        self.cursor = self.connection.cursor(dictionary=True)
        self.error_sort_speaker = []

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

    def associated_domain(self, speaker_id, domain_set):
        for domain_id in domain_set:
            sql = "select id,name,value from moyin_domain where value=%s"
            data = (domain_id,)
            self.cursor.execute(sql, data)
            results = self.cursor.fetchall()
            for row in results:
                # print(row)
                sql = "insert into moyin_speaker_domain(speaker_id,domain_id) values(%s,%s)"
                data = (speaker_id, row["id"])
                self.cursor.execute(sql, data)
                print(sql % data)

    def associated_language(self, speaker_id, language_set):
        for language in language_set:
            sql = "select id,name,value from moyin_language where name=%s"
            data = (language,)
            self.cursor.execute(sql, data)
            results = self.cursor.fetchall()
            for row in results:
                # print(row)
                sql = "insert into moyin_speaker_language(speaker_id,language_id) values(%s,%s)"
                data = (speaker_id, row["id"])
                self.cursor.execute(sql, data)
                print(sql % data)

    def resort_emotion(self, emotion_set, speaker_emotion_list):
        # 注意假设 emotion_set 的长度为 9 ，则 speaker_emotin_list 长度为 10
        # 必须以speaker_emotin_list为准
        temp = None
        em = {}

        for speaker_emotion in speaker_emotion_list:
            speaker = speaker_emotion["speaker"]
            match = re.search("@(.*)", speaker)
            emotion = match.group(1) if match else None
            if emotion:
                em[emotion] = speaker_emotion
            else:
                temp = speaker_emotion

        sorted = []
        for emo in emotion_set:
            d = em.get(emo)
            if d:
                sorted.append(d)

        return temp, sorted

    def associated_emotion(self, speaker_id, emotion_set, speaker_emotion_list):
        print("当前speaker_id进行排序：", speaker_id)
        # 在插入之前需要对序号进行校正
        a_size = len(speaker_emotion_list)
        first, speaker_emotion_list = self.resort_emotion(
            emotion_set, speaker_emotion_list
        )
        b_size = len(speaker_emotion_list)
        if a_size != b_size:
            self.error_sort_speaker.append(speaker_id)

        print("当前speaker_id排序完成：", speaker_id)
        # print("排序后", emotion_set, speaker_emotion_list)

        # 第一个没有emotion
        if first:
            sql = "insert into moyin_speaker_emotion(speaker_id,emotion_id,demo,demo_url,style_call_name,name) values(%s,%s,%s,%s,%s,%s)"
            data = (
                speaker_id,
                0,
                first["demo"],
                first["demoUrl"],
                first["speaker"],
                first["name"],
            )
            self.cursor.execute(sql, data)
            print(sql % data)
        else:
            self.error_sort_speaker.append(speaker_id)

        for emotion, speaker_emotion in zip(emotion_set, speaker_emotion_list):
            sql = "select id,emotion_name,emotion_value from moyin_emotion where emotion_value=%s"
            data = (emotion,)
            self.cursor.execute(sql, data)
            results = self.cursor.fetchall()
            for row in results:
                print(row)
                sql = "insert into moyin_speaker_emotion(speaker_id,emotion_id,demo,demo_url,style_call_name,name) values(%s,%s,%s,%s,%s,%s)"
                data = (
                    speaker_id,
                    row["id"],
                    speaker_emotion["demo"],
                    speaker_emotion["demoUrl"],
                    speaker_emotion["speaker"],
                    speaker_emotion["name"],
                )
                self.cursor.execute(sql, data)
                print(sql % data)

    def get_speaker_id(self, speaker):
        sql = "select id from moyin_speaker where style_call_name = %s"
        data = (speaker,)
        self.cursor.execute(sql, data)
        return self.cursor.fetchone()["id"]

    def run(self):
        self.connect()
        # self.cursor.execute("delete from moyin_speaker")

        sql = "delete from moyin_speaker_domain"
        self.cursor.execute(sql)

        sql = "delete from moyin_speaker_language"
        self.cursor.execute(sql)

        sql = "delete from moyin_speaker_emotion"
        self.cursor.execute(sql)

        json_data = self.get_data()
        for item in json_data:
            speaker = item["speaker"]
            speaker_id = self.get_speaker_id(speaker)
            # print(speaker_id)
            domain_id_set = item["domainIdSet"]
            emotion_set = item["emotionSet"]
            speaker_emotion_list = item["speakerEmotionCacheVOList"]
            specific_language_set = item["specificLanguageSet"]
            self.associated_domain(speaker_id, domain_id_set)
            self.associated_emotion(speaker_id, emotion_set, speaker_emotion_list)
            self.associated_language(speaker_id, specific_language_set)
            # print(domain_id_set, emotion_set, specific_language_set)

        # self.close()
        print("存在异常的speakerid", self.error_sort_speaker)

    def get_data(self):
        url = "https://openapi.mobvoi.com/open-apis/speaker/searchSpeakers?pageSize=663&pageNum=1"
        response = requests.get(url)
        return response.json()["data"]["results"]


if __name__ == "__main__":
    ImportMoyinSpeaker().run()
