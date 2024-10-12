/**
 * 如何解决开发数据库与生产数据库不一致的问题
 * 1.开发前：同步一次数据库
 * 2.开发中：有新数据怎么办？
 * 2.生产时：同步一次数据库
 * 
 * 写一个监控程序，当有数据新增、修改、删除的时候，通知我，并且自动同步到本机
 */

const { exec } = require('child_process');
const fs = require('fs');
const path = require('path');
import { execCommand } from '../common/common.build'

// 配置 MySQL 和 Docker
const dockerContainerName = 'mysql'; // Docker 容器名称
const databaseName = process.env.MYSQL_REMOTE_DATABASE; // MySQL 数据库名称
const username = process.env.MYSQL_REMOTE_USER; // MySQL 用户名
const password = process.env.MYSQL_REMOTE_PASSWORD; // MySQL 密码
const backupFilePathInContainer = '/tmp/backup.sql'; // 容器内备份文件路径
const remoteBackupFilePath = path.join(__dirname, 'backup.sql'); // 本地备份文件路径
const localBackupFilePath = "./backup"

async function downloadSqlFile() {
  await execCommand(`docker exec ${dockerContainerName} /usr/bin/mysqldump -u ${username} -p${password} ${databaseName} > ${backupFilePathInContainer}`);
  await execCommand(`docker cp ${dockerContainerName}:${backupFilePathInContainer} ${localBackupFilePath}`);
}

downloadSqlFile();
