package com.sfsctech.support.sftp.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Class SFTPProperties
 *
 * @author 张麒 2018-12-25.
 * @version Description:
 */
@Component
@ConfigurationProperties(
        prefix = "sftp.support"
)
public class SFTPProperties {

    // SFTP 登录用户名
    private String username;
    // SFTP 登录密码
    private String password;
    // 私钥
    private String privateKey;
    // SFTP 服务器地址IP地址
    private String ip;
    // SFTP 端口
    private int port;
    // 文件下载失败下次超时重试时间
    private int downloadSleep = 0;
    // 文件下载失败重试次数
    private int downloadRetry = 0;
    // 文件上传失败下次超时重试时间
    private int uploadSleep = 0;
    // 文件上传失败重试次数
    private int uploadRettry = 0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDownloadSleep() {
        return downloadSleep;
    }

    public void setDownloadSleep(int downloadSleep) {
        this.downloadSleep = downloadSleep;
    }

    public int getDownloadRetry() {
        return downloadRetry;
    }

    public void setDownloadRetry(int downloadRetry) {
        this.downloadRetry = downloadRetry;
    }

    public int getUploadSleep() {
        return uploadSleep;
    }

    public void setUploadSleep(int uploadSleep) {
        this.uploadSleep = uploadSleep;
    }

    public int getUploadRettry() {
        return uploadRettry;
    }

    public void setUploadRettry(int uploadRettry) {
        this.uploadRettry = uploadRettry;
    }
}
