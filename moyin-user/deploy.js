// const { Client } = require('ssh2')
// const AdmZip = require('adm-zip')
// const fs = require('fs')
import { Client } from 'ssh2'
import AdmZip from 'adm-zip'
import fs from 'fs'

// 参数
const serverIp = '114.55.174.108'
const serverPort = 22
const userName = 'root'
const password = 'Bing1234.'
const localDistPath = './dist'
const remotePath = '/url/share/nginx/html/user/'

// 打包dist目录为dist.zip
const zip = new AdmZip()
zip.addLocalFolder(localDistPath)
zip.writeZip('dist.zip')

// 连接到服务器
const conn = new Client()
conn
  .on('ready', () => {
// console.log('Client :: ready')

    // 上传dist.zip到服务器
    const sftp = conn.sftp()
    sftp.put('dist.zip', `${remotePath}dist.zip`, (err) => {
      if (err) {
        throw err
      }
// console.log('File uploaded!')

      // 执行解压命令
      conn.exec(
        `unzip -o ${remotePath}dist.zip -d ${remotePath} && rm ${remotePath}dist.zip`,
        (err, stream) => {
          if (err) throw err
          stream
            .on('close', (code, signal) => {
// console.log(`Stream :: close :: code: ${code}, signal: ${signal}`)
              conn.end()
            })
            .on('data', (data) => {
// console.log(`STDOUT: ${data}`)
            })
            .stderr.on('data', (data) => {
              console.error(`STDERR: ${data}`)
            })
        },
      )
    })
  })
  .connect({
    host: serverIp,
    port: serverPort,
    username: userName,
    password: password,
  })

conn.on('error', (err) => {
  console.error('SSH Connection :: error: ' + err.message)
})

conn.on('end', () => {
// console.log('SSH Connection :: end')
  // 清理本地文件
  fs.unlinkSync('dist.zip')
})
