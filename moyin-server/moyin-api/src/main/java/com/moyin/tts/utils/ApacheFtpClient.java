package com.moyin.tts.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.time.Duration;

/**
 * @author 31734
 */
@Slf4j
public class ApacheFtpClient {
    private final FTPClient ftpClient;

    /**
     * 实例化
     *
     * @param hostName FTP服务器地址
     * @param port     FTP服务器端口
     * @param userName FTP登录账户
     * @param password FTP登录密码
     * @throws IOException
     */
    public ApacheFtpClient(String hostName, int port, String userName, String password) throws IOException {
        ftpClient = new FTPClient();
        //设置传输命令的超时 毫秒
        ftpClient.setDefaultTimeout(20000);
        //设置两个服务连接超时时间 毫秒
        ftpClient.setConnectTimeout(10000);
        //被动模式下设置数据传输的超时时间 毫秒
        ftpClient.setDataTimeout(Duration.ofMillis(15000));
        //连接FTP
        ftpClient.connect(hostName, port);
        //更加账户密码登录服务
        ftpClient.login(userName, password);
        //被动模式（需要设置在连接之后，尤其linux环境）
        ftpClient.enterLocalPassiveMode();
    }

    /**
     * FTP上传文件
     *
     * @param remoteUploadDirectory   要上传的目录（FTP服务器上的目录）
     * @param localUploadFilePathName 本地上传文件的完整路径（本地路径）
     * @return
     */
    public Pair<Boolean, String> uploadFile(String remoteUploadDirectory, String localUploadFilePathName) {
        FileInputStream fis = null;
        try {
            // 如果不能进入dir下，说明此目录不存在！
            System.out.println("FTP响应码：" + ftpClient.getReplyCode());
            System.out.println("FTP响应信息：" + ftpClient.getReplyString());
            if (!ftpClient.changeWorkingDirectory(remoteUploadDirectory)) {
                log.info("没有目录:{}", remoteUploadDirectory);
                if (!ftpClient.makeDirectory(remoteUploadDirectory)) {
                    log.info("创建文件目录【{}】 失败！", remoteUploadDirectory);
                    return Pair.of(false, "创建文件目录【" + remoteUploadDirectory + "】 失败");
                }
            }
            //进入文件目录
            ftpClient.changeWorkingDirectory(remoteUploadDirectory);
            //创建文件流
            fis = new FileInputStream(new File(localUploadFilePathName));
            //设置上传目录
            ftpClient.setBufferSize(1024);
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //FTP服务器上最终的名字
            String uploadFileName = localUploadFilePathName.substring(localUploadFilePathName.lastIndexOf(File.separator) + 1);
            //文件上传
            boolean b = ftpClient.storeFile(uploadFileName, fis);
            int replyCode = ftpClient.getReplyCode();
            log.info("上传文件响应码：{}", replyCode);
            log.info("上传文件响应信息：{}", ftpClient.getReplyString());
            return Pair.of(b, b ? "上传成功" : "上传失败");
        } catch (Exception e) {
            log.error("FTP上传文件异常！:", e.getMessage());
            return Pair.of(false, "上传文件异常");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                log.error("关闭流发生异常!", e);
            }
        }
    }

    /**
     * FTP文件下载
     *
     * @param remoteDownloadDirectory 要下载的目录（FTP服务器目录）
     * @param localDirectory          本地下载文件路径
     * @param downloadFileName        下载的文件名
     * @return
     */
    public Pair<Boolean, String> downloadFile(String remoteDownloadDirectory, String localDirectory, String downloadFileName) {
        OutputStream out = null;
        try {
            if (StringUtils.isBlank(downloadFileName)) {
                return Pair.of(false, "要下载的文件不能为空");
            }
            //工作目录切换到下载文件的目录下
            if (!ftpClient.changeWorkingDirectory(remoteDownloadDirectory)) {
                log.info("目录不存在：{}", remoteDownloadDirectory);
                return Pair.of(false, "目录不存在");
            }
            //获取目录下所有文件
            FTPFile[] files = ftpClient.listFiles();
            if (files.length < 1) {
                return Pair.of(false, "目录为空");
            }
            boolean fileExist = false;
            boolean downloadFlag = false;
            //遍历文件列表
            for (FTPFile ftpFile : files) {
                String localFile = localDirectory + File.separator + downloadFileName;
                //是否存在要下载的文件
                if (downloadFileName.equals(ftpFile.getName())) {
                    fileExist = true;
                    out = new FileOutputStream(localFile);
                    //下载
                    downloadFlag = ftpClient.retrieveFile(downloadFileName, out);
                    int replyCode = ftpClient.getReplyCode();
                    log.info("下载文件响应码：{}", replyCode);
                    break;
                }
            }
            if (!fileExist) {
                return Pair.of(false, "FTP服务器上文件不存在");
            }
            return Pair.of(downloadFlag, downloadFlag ? "下载成功" : "下载失败");
        } catch (Exception e) {
            log.error("FTP下载文件异常！:", e);
            return Pair.of(false, "下载文件异常");
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
