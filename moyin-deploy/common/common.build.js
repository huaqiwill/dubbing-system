require('dotenv').config();
const path = require('path');
const fs = require('fs');
const { spawn } = require('child_process');

const targetStore = "./store"
const mvnPath = path.resolve(process.env.MAVEN_HOME, 'bin/mvn')

// 
const mavenProjectPath = process.env.PROJECT_MAVEN
const userProjectPath = path.resolve(process.env.PROJECT_USER, 'dist');
const adminProjectPath = path.resolve(process.env.PROJECT_ADMIN, 'dist');

// 压缩
const zipPath = process.env.BRAND_ZIP

// 数据库
const mysqlDumpPath = path.resolve(process.env.MYSQL_HOME, 'bin/mysqldump.exe')
const mysqlUser = process.env.MYSQL_USER
const mysqlPassword = process.env.MYSQL_PASSWORD
const mysqlDatabase = process.env.MYSQL_DATABASE

/**
 * 清理目标文件或文件夹
 * @param {string} targetPath - 目标文件或文件夹的路径
 * @returns {Promise<void>}
 */
function cleanTarget(targetPath) {
    return new Promise((resolve, reject) => {
        // 检查文件是否存在
        fs.access(targetPath, fs.constants.F_OK, (error) => {
            if (error) {
                console.error(`目标路径 ${targetPath} 不存在`);
                return resolve();
            }

            // 检查目标路径是否是文件夹
            fs.stat(targetPath, (error, stats) => {
                if (error) {
                    console.error(`获取文件状态时发生错误: ${error.message}`);
                    return reject(error);
                }

                if (stats.isDirectory()) {
                    // 目标是文件夹
                    fs.rm(targetPath, { recursive: true, force: true }, (error) => {
                        if (error) {
                            console.error(`清理文件夹时发生错误: ${error.message}`);
                            return reject(error);
                        }
                        console.log(`目标文件夹 ${targetPath} 已成功清理`);
                        resolve();
                    });
                } else if (stats.isFile()) {
                    // 目标是文件
                    fs.unlink(targetPath, (error) => {
                        if (error) {
                            console.error(`删除文件时发生错误: ${error.message}`);
                            return reject(error);
                        }
                        console.log(`目标文件 ${targetPath} 已成功删除`);
                        resolve();
                    });
                } else {
                    const msg = `目标路径 ${targetPath} 既不是文件也不是文件夹`;
                    console.error(msg);
                    reject(new Error(msg));
                }
            });
        });
    });
}


// 压缩文件
// zipTarget("dist","dist.zip")
async function zipTarget(source, target) {
    await execCommand(`${zipPath} a "${target}" "${source}"`)
}

/**
 * 执行命令并流式获取输出
 * @param {string} command - 要执行的命令
 * @param {Array<string>} args - 命令参数
 * @returns {Promise<string>} - 命令执行完成后的输出
 */
function execCommand(command, args = []) {
    console.log(`🚀执行命令: ${command} ${args.join(' ')}`);
    return new Promise((resolve, reject) => {
        // 使用 spawn 执行命令
        const cmd = spawn(command, args, { shell: true, encoding: 'utf8' });

        // 实时获取并输出标准输出
        cmd.stdout.on('data', (data) => {
            process.stdout.write(`标准输出: ${data}`);
        });

        // 实时获取并输出标准错误
        cmd.stderr.on('data', (data) => {
            process.stdout.write(`标准错误: ${data}`);
        });

        // 命令执行完成时处理
        cmd.on('close', (code) => {
            if (code === 0) {
                resolve(`命令成功执行，退出码: ${code}`);
            } else {
                reject(`命令失败，退出码: ${code}`);
            }
        });

        // 错误处理
        cmd.on('error', (error) => {
            reject(`执行命令时发生错误: ${error.message}`);
        });
    });
}

async function compileMavenProject() {

    const command = `cd "${process.env.PROJECT_MAVEN}" && "${mvnPath}" clean package`;
    await execCommand(command)

    const source = path.resolve(process.env.PROJECT_MAVEN, "moyin-admin/target/moyin-admin.jar")
    const destination = path.resolve(targetStore, 'moyin-admin.jar')
    fs.copyFile(source, destination, (error) => {
        if (error) {
            console.error(`复制文件时发生错误: ${error.message}`);
            return
        }
        console.log(`文件 ${source} 已成功复制到 ${destination}`);
    });
}

async function compileUserProject() {
    await cleanTarget(userProjectPath)
    await execCommand(`cd "${process.env.PROJECT_USER}" && yarn build-only`)
    await zipTarget(userProjectPath, path.resolve(targetStore, 'user.zip'))
}

async function compileAdminProject() {
    await cleanTarget(adminProjectPath)
    await execCommand(`cd "${process.env.PROJECT_ADMIN}" && npm run build:prod`)
    await zipTarget(adminProjectPath, path.resolve(targetStore, 'admin.zip'))
}

async function dumpMysqlScrit() {
    console.log("开始同步数据库...")
    const tartgetPath = path.resolve(targetStore, 'spring_moyin.sql')
    const command = `${mysqlDumpPath} -u ${mysqlUser} -p${mysqlPassword} --databases ${mysqlDatabase} > ${tartgetPath}`
    await execCommand(command).catch(err => {
        console.log(err)
    })
    console.log("数据库同步成功")
}

module.exports = {
    execCommand,
    zipTarget,
    compileMavenProject,
    compileUserProject,
    compileAdminProject,
    dumpMysqlScrit
}

// async function main() {
//     await compileMavenProject()
//     await compileUserProject();
//     await compileAdminProject();
//     await dumpMysqlScrit();
// }

// main();