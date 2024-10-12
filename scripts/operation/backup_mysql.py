"""
2024.8.3
MySQL备份脚本
"""
import os
import time
import schedule
from datetime import datetime

# 数据库配置
db_host = 'localhost'
db_user = 'your_username'
db_password = 'your_password'
db_name = 'your_database'

# 备份文件夹路径
backup_dir = 'path/to/your/backup/dir'

def backup_db():
    # 确保备份目录存在
    if not os.path.exists(backup_dir):
        os.makedirs(backup_dir)
    
    # 当前时间戳
    timestamp = datetime.now().strftime('%Y%m%d%H%M%S')
    # 备份文件名
    backup_file = os.path.join(backup_dir, f'mysql_backup_{timestamp}.sql')
    
    # 使用mysqldump命令备份数据库
    dump_command = f"mysqldump -h {db_host} -u {db_user} -p{db_password} {db_name} > {backup_file}"
    os.system(dump_command)
    print(f'Backup created at {backup_file}')

# 安排任务每小时执行一次
schedule.every().hour.do(backup_db)

if __name__ == '__main__':
    # 立即执行一次备份
    backup_db()
    while True:
        schedule.run_pending()
        time.sleep(1)
