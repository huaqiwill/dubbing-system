const { Client } = require("ssh2");
const SFTPClient = require("ssh2-sftp-client");
require('dotenv').config();
const { spawn } = require('child_process');
const fs = require('fs');

const conn = new Client();
const sftp = new SFTPClient();

const hostname = process.env.SERVER_IP;
const port = process.env.SERVER_PORT;
const username = process.env.USER_NAME;
const password = process.env.PASSWORD;

async function uploadFile(localFilePath, remoteFilePath) {
    try {
        console.log("等待SFTP连接");
        await sftp.connect({
            host: hostname,
            port: port,
            username: username,
            password: password,
        });
        console.log("SFTP连接成功");
        console.log(`开始上传文件：${localFilePath}`);

        // 获取文件大小
        const localFileSize = fs.statSync(localFilePath).size;

        // 创建一个可读流
        const readStream = fs.createReadStream(localFilePath);

        // 上传进度
        let uploadedBytes = 0;
        const startTime = Date.now();

        // 监听数据流
        readStream.on('data', (chunk) => {
            uploadedBytes += chunk.length;
            const elapsedTime = (Date.now() - startTime) / 1000; // 转换为秒
            const speed = uploadedBytes / elapsedTime; // 字节/秒
            const speedKB = (speed / 1024).toFixed(2); // KB/秒
            const progress = ((uploadedBytes / localFileSize) * 100).toFixed(2);
            console.log(`上传进度: ${progress}% (${speedKB} KB/s)`);
        });

        // 上传文件
        await sftp.put(readStream, remoteFilePath);
        console.log(`文件上传成功：从本地 ${localFilePath} 上传到 ${remoteFilePath}`);
    } catch (error) {
        console.error("文件上传失败：", error);
    } finally {
        await sftp.end();
    }
}


async function downloadFile() {
    try {
        await sftp.connect({
            host: hostname,
            port: port,
            username: username,
            password: password,
        });
        await sftp.get(remoteFilePath, localFilePath);
        console.log(`Downloaded ${remoteFilePath} to ${localFilePath}`);
    } catch (error) {
        console.error("Download error:", error);
    } finally {
        await sftp.end();
    }
}

async function execScript(scriptPath) {
    try {
        await execCommand(`sh ${scriptPath}`);
    } catch (error) {
        console.error('Error:', error);
    }
}

async function execCommand(command) {
    console.log(`🚀执行命令: ${command} `);
    return new Promise((resolve, reject) => {
        conn.exec(command, (err, stream) => {
            if (err) {
                return reject(`执行命令时发生错误: ${err.message}`);
            }

            let stdoutData = '';
            let stderrData = '';

            stream.on('close', (code, signal) => {
                if (code === 0) {
                    resolve(stdoutData);
                } else {
                    reject(`命令失败，退出码: ${code}\n${stderrData}`);
                }
            }).on('data', (data) => {
                stdoutData += data.toString();
                process.stdout.write(data); // 保留输出格式
            }).stderr.on('data', (data) => {
                stderrData += data.toString();
                process.stderr.write(data); // 保留错误格式
            });
        });
    });
}

async function syncDatabase() {
    console.log("开始同步数据库...")

    let localFilePath = "./store/spring_moyin.sql"
    let remoteFilePath = "/root/server/spring_moyin.sql"
    let mysqlUser = process.env.MYSQL_REMOTE_USER
    let mysqlPassword = process.env.MYSQL_REMOTE_PASSWORD
    let database = process.env.MYSQL_REMOTE_DATABASE

    await uploadFile(localFilePath, remoteFilePath);
    await execCommand(`docker exec -i mysql mysql -u${mysqlUser} -p${mysqlPassword} -e "drop database if exists ${database};create database if not exists ${database};"`)
    await execCommand(`docker exec -i mysql mysql -u${mysqlUser} -p${mysqlPassword} --force ${database} < ${remoteFilePath}`).catch(err => { })

    await execCommand(`rm -rf ${remoteFilePath}`)
    console.log("数据库同步完成")
}

function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function syncServer() {
    console.log("开始启动服务...")

    await killJavaServerProcess()

    let localFilePath = "./store/moyin-admin.jar"
    let remoteFilePath = "/root/server/moyin-admin.jar"
    await execCommand("rm -rf /root/server/moyin-admin.jar")
    await uploadFile(localFilePath, remoteFilePath);

    await startJavaServerProcess()

    console.log("服务端更新完成")
}

async function syncUser() {
    console.log("开始同步前端...")

    let localFilePath = "./store/user.zip"
    let remoteFilePath = "/usr/share/nginx/html/user.zip"
    await uploadFile(localFilePath, remoteFilePath);
    await execCommand("rm -rf /usr/share/nginx/html/user")
    await execCommand("unzip -o /usr/share/nginx/html/user.zip -d /usr/share/nginx/html/user")
    await execCommand("rm -rf /usr/share/nginx/html/user.zip")
    await execCommand("cd /usr/share/nginx/html/user && mv dist/* .")
    console.log("前端同步完成")

}

async function syncAdmin() {
    console.log("开始同步后台...")

    let localFilePath = "./store/admin.zip"
    let remoteFilePath = "/usr/share/nginx/html/admin.zip"
    await execCommand("rm -rf /usr/share/nginx/html/admin.zip")
    await uploadFile(localFilePath, remoteFilePath);
    await execCommand("rm -rf /usr/share/nginx/html/admin")
    await execCommand("unzip -o /usr/share/nginx/html/admin.zip -d /usr/share/nginx/html/admin")
    await execCommand("cd /usr/share/nginx/html/admin && mv dist/* .")
    console.log("后台同步完成")

}

async function clearRedis() {
    console.log("开始清理缓存...")
    let password = process.env.REDIS_PASSWORD
    await execCommand(`docker exec -i redis redis-cli FLUSHDB -a ${password}`)
    console.log("缓存清理完成")
}

async function sync() {

}

async function startJavaServerProcess() {
    await execCommand("rm -rf /root/server/running.log")
    // await execCommand("ls -l /root/server/start_back.sh")
    await execCommand("cd /root/server && nohup /root/java/bin/java -jar ./moyin-admin.jar -Dspring.config.location=./application.yml > running.log 2>&1 &")
    await delay(2000);  // 等待 2 秒
    try {
        await execCommand("cat /root/server/running.log")
        console.log("服务启动成功")
    } catch (err) {
        console.log("服务启动失败，请手动启动服务！")
    }
}

async function killJavaServerProcess() {
    try {
        const searchCommand = "ps aux | grep 'moyin-admin.jar' | grep -v grep | awk '{print $2}'";
        const pid = await execCommand(searchCommand);

        if (pid) {
            console.log('PID: ' + pid);
            const killCommand = `kill ${pid.trim()}`;
            await execCommand(killCommand);
            console.log('Process terminated');
        } else {
            console.log('Process not found');
            killJavaServerProcessByPs()
        }
    } catch (error) {
        console.error('Error:', error);
        killJavaServerProcessByPs()
    }
}

async function killJavaServerProcessByPs() {
    try {
        const searchCommand = "ps -ef | grep 'java' | grep -v grep | awk '{print $2}'";
        const pid = await execCommand(searchCommand);

        if (pid) {
            console.log('PID: ' + pid);
            const killCommand = `kill ${pid.trim()}`;
            await execCommand(killCommand);
            console.log('Process terminated');
        } else {
            console.log('Process not found');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

module.exports = {
    syncUser,
    syncAdmin,
    syncServer,
    clearRedis,
    startJavaServerProcess,
    killJavaServerProcess,
    killJavaServerProcessByPs,
    syncDatabase
}
// conn
//     .on("ready", async () => {
//         console.log("== 客户端准备 ==");

//         await syncDatabase()
//         await syncUser()
//         await syncAdmin()
//         await clearRedis()
//         await syncServer()

//         console.log("== 服务端更新完成 ==");
//         conn.end()
//     })
//     .connect({
//         host: hostname,
//         port: port,
//         username: username,
//         password: password,
//     });
