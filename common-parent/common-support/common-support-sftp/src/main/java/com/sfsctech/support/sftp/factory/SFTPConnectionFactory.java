package com.sfsctech.support.sftp.factory;

import com.jcraft.jsch.*;
import com.sfsctech.support.common.util.FileUtil;
import com.sfsctech.support.common.util.StringUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import com.sfsctech.support.sftp.properties.SFTPProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * Class SFTPConnectionFactory
 *
 * @author 张麒 2018-12-25.
 * @version Description:
 */
public class SFTPConnectionFactory {

    private final Logger logger = LoggerFactory.getLogger(SFTPConnectionFactory.class);

    private ChannelSftp client;
    private Session session;

    private SFTPProperties properties;

    public SFTPConnectionFactory(SFTPProperties properties) {
        this.properties = properties;
    }

    public ChannelSftp getConnection() {
        if (client == null || session == null || !client.isConnected() || !session.isConnected()) {
            try {
                JSch jsch = new JSch();
                // 设置私钥
                if (StringUtil.isNotBlank(properties.getPrivateKey())) {
                    jsch.addIdentity(properties.getPrivateKey());
                }
                if (StringUtil.isNotBlank(properties.getUsername()) && 0 != properties.getPort()) {
                    session = jsch.getSession(properties.getUsername(), properties.getIp(), properties.getPort());
                } else if (StringUtil.isNotBlank(properties.getUsername())) {
                    session = jsch.getSession(properties.getUsername(), properties.getIp());
                } else {
                    session = jsch.getSession(properties.getIp());
                }
                if (StringUtil.isNotBlank(properties.getPassword())) {
                    session.setPassword(properties.getPassword());
                }
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect(300000);
                Channel channel = session.openChannel("sftp");
                channel.connect();
                client = (ChannelSftp) channel;
                logger.info("sftp服务器连接成功");
            } catch (JSchException e) {
                logger.error("sftp登录失败，检测登录ip，端口号，用户名密码是否正确，错误信息:{}", ThrowableUtil.getRootMessage(e));
            }
        }

        return client;
    }

    public void close() {
        if (client != null) {
            if (client.isConnected()) {
                client.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    private boolean isDirExist(String sftpDirectory) throws SftpException {
        Vector<?> vector = getConnection().ls(sftpDirectory);
        return null != vector;
    }

    /**
     * 创建服务器上文件目录
     *
     * @param sftpDirectory 服务器的文件目录
     * @return true：成功、false：失败
     */
    private boolean createDirectory(String sftpDirectory) {
        boolean bool = true;
        try {
            if (!isDirExist(sftpDirectory)) {
                ChannelSftp sftp = getConnection();
                String dirs[] = sftpDirectory.split("/");
                StringBuilder filePath = new StringBuilder("/");
                for (String dir : dirs) {
                    if (StringUtil.isBlank(dir)) {
                        continue;
                    }
                    filePath.append(dir).append("/");
                    if (isDirExist(filePath.toString())) {
                        sftp.cd(filePath.toString());
                    } else {
                        // 建立目录
                        sftp.mkdir(filePath.toString());
                        // 进入并设置为当前目录
                        sftp.cd(filePath.toString());
                    }
                }
                logger.info("服务器文件目录创建成功:", sftpDirectory);
            } else {
                logger.info("服务器文件目录存在:", sftpDirectory);
            }
        } catch (SftpException e) {
            logger.error("sftp文件上传，目录创建失败，错误信息:{}", ThrowableUtil.getRootMessage(e), e);
            bool = false;
        }
        return bool;
    }

    /**
     * 批量上传文件
     *
     * @param sftpDirectory 服务器的文件目录
     * @param files         文件集合
     * @return true：成功、false：失败
     */
    public synchronized boolean bacthUpload(String sftpDirectory, List<File> files) {
        boolean bool = createDirectory(sftpDirectory);
        if (bool) {
            ChannelSftp sftp = getConnection();
            try {
                for (File file : files) {
                    sftp.put(new FileInputStream(file), sftpDirectory + file.getName());
                    logger.info("上传sftp文件成功，ftp路径:{},文件名称:{}", sftpDirectory, file.getName());
                }
            } catch (Exception e) {
                logger.error("sftp文件上传失败，错误信息:{}", ThrowableUtil.getRootMessage(e), e);
            } finally {
                close();
            }
        }
        return bool;
    }

    /**
     * 批量上传文件（文件夹下所有的文件）
     *
     * @param sftpDirectory 服务器的文件目录
     * @param filePath      文件夹路径
     * @return true：成功、false：失败
     */
    public synchronized boolean bacthUpload(String sftpDirectory, String filePath) {
        List<File> files = FileUtil.getFileFromFolders(filePath);
        return bacthUpload(sftpDirectory, files);
    }

    /**
     * 文件上传
     * 将文件对象上传到sftp作为文件。
     *
     * @param sftpDirectory 服务器的文件目录
     * @param filePath      文件路径
     * @return true：成功、false：失败
     */
    public synchronized boolean upload(String sftpDirectory, String filePath) {
        return upload(sftpDirectory, new File(filePath));
    }

    /**
     * 文件上传
     * 将文件对象上传到sftp作为文件。
     *
     * @param sftpDirectory 服务器的文件目录
     * @param file          文件对象
     * @return true：成功、false：失败
     */
    public synchronized boolean upload(String sftpDirectory, File file) {
        boolean bool = createDirectory(sftpDirectory);
        int i = 0;
        ChannelSftp sftp = getConnection();
        try {
            while (bool) {
                try {
                    sftp.put(new FileInputStream(file), sftpDirectory + file.getName());
                    logger.info("{}sftp文件成功，ftp路径:{},文件名称:{}", (i > 0 ? "重试上传" : "上传"), sftpDirectory, file.getName());
                    bool = false;
                } catch (Exception e) {
                    i++;
                    logger.error("sftp文件上传失败，重试中。。。第" + i + "次，错误信息:{}", ThrowableUtil.getRootMessage(e), e);
                    if (i > properties.getUploadRettry()) {
                        logger.error("sftp文件上传失败，超过重试次数结束重试，错误信息:{}", ThrowableUtil.getRootMessage(e), e);
                        return false;
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(properties.getUploadSleep());
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } finally {
            close();
        }
        return true;
    }

    /**
     * 下载文件。
     *
     * @param sftpDirectory 服务器的文件目录
     * @param fileName      下载文件名
     * @return InputStream
     */
    public synchronized InputStream download(String sftpDirectory, String fileName) {
        boolean bool = createDirectory(sftpDirectory);
        int i = 0;
        ChannelSftp sftp = getConnection();
        InputStream inputStream = null;
        try {
            while (bool) {
                try {
                    inputStream = sftp.get(sftpDirectory + fileName);
                    logger.info("{}sftp文件成功，ftp路径:{}", (i > 0 ? "重试下载" : "下载"), sftpDirectory);
                    bool = false;
                } catch (Exception e) {
                    i++;
                    logger.error("sftp文件下载失败，重试中。。。第" + i + "次，错误信息:{}", ThrowableUtil.getRootMessage(e), e);
                    if (i > properties.getDownloadRetry()) {
                        logger.error("ftp文件下载失败，超过重试次数结束重试，错误信息:{}", ThrowableUtil.getRootMessage(e), e);
                        return inputStream;
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(properties.getDownloadSleep());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } finally {
            close();
        }
        return inputStream;
    }

    /**
     * 下载文件。
     *
     * @param sftpDirectory 服务器的文件目录
     * @param fileName      下载文件名
     * @param localFile     本地保存文件
     * @return true：成功、false：失败
     */
    public synchronized boolean download(String sftpDirectory, String fileName, File localFile) {
        boolean bool = createDirectory(sftpDirectory);
        int i = 0;
        ChannelSftp sftp = getConnection();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(localFile);
            while (bool) {
                try {
                    sftp.get(sftpDirectory + fileName, fileOutputStream);
                    logger.info("{}sftp文件成功，ftp路径:{},文件名称:{}", (i > 0 ? "重试下载" : "下载"), sftpDirectory, localFile.getName());
                    bool = false;
                } catch (Exception e) {
                    i++;
                    logger.error("sftp文件下载失败，重试中。。。第" + i + "次，错误信息:{}", ThrowableUtil.getRootMessage(e), e);
                    if (i > properties.getDownloadRetry()) {
                        logger.error("ftp文件下载失败，超过重试次数结束重试，错误信息:{}", ThrowableUtil.getRootMessage(e), e);
                        return false;
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(properties.getDownloadSleep());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
            if (null != fileOutputStream) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 下载文件。
     *
     * @param sftpDirectory 服务器的文件目录
     * @param fileName      下载文件名
     * @param localFilePath 本地保存文件路径
     * @return true：成功、false：失败
     */
    public synchronized boolean download(String sftpDirectory, String fileName, String localFilePath) {
        return download(sftpDirectory, fileName, new File(localFilePath));
    }


    /**
     * 删除文件
     *
     * @param sftpFilePath 服务器的文件路径
     */
    @SuppressWarnings("unchecked")
    public synchronized boolean delete(String sftpFilePath) {
        ChannelSftp sftp = getConnection();
        try {
            Vector<ChannelSftp.LsEntry> vector = sftp.ls(sftpFilePath);
            if (null == vector) {
                logger.error("ftp文件删除失败，服务器不存在文件路径:{}", sftpFilePath);
                return false;
            }
            // 文件，直接删除
            if (vector.size() == 1) {
                sftp.rm(sftpFilePath);
            }
            // 空文件夹，直接删除
            else if (vector.size() == 2) {
                sftp.rmdir(sftpFilePath);
            }
            // 批量删除文件夹下所有文件
            else {
                vector.forEach(v -> {
                    System.out.println(v.getLongname());
                    if (!(".".equals(v.getFilename()) || "..".equals(v.getFilename()))) {
                        delete(sftpFilePath + "/" + v.getFilename());
                    }
                });
                // 删除文件夹
                sftp.rmdir(sftpFilePath);
            }
        } catch (SftpException e) {
            logger.error("ftp文件删除错误，异常信息:{}", ThrowableUtil.getRootMessage(e), e);
        }
        return true;
    }
}
