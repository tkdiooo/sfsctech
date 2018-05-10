package com.sfsctech.common.support.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Class ZipUtil
 *
 * @author 张麒 2016/4/14.
 * @version Description:
 */
public class ZipUtil {

    private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);

    private static final int BUFFER = 2048;

    /**
     * 获取Zip File对象，子目录内文件也压缩
     *
     * @param path 文件路径
     * @return File
     * @throws IOException
     */
    public static File getZipFile(String path) throws IOException {
        AssertUtil.isNotBlank(path, "文件路径为空");
        return getZipFile(new File(path));
    }

    /**
     * 获取Zip File对象，子目录内文件也压缩
     *
     * @param file 文件对象
     * @return File
     * @throws IOException
     */
    public static File getZipFile(File file) throws IOException {
        boolean flag;
        AssertUtil.notNull(file);
        // 创建缓冲输入流BufferedInputStream
        BufferedInputStream bis = null;
        // 压缩文件名称
        String zipName = FileUtil.getFileBaseName(file.getPath());
        // 创建ZipOutputStream对象，将向它传递希望写入文件的输出流
        File zipFile = new File(zipName + ".zip");
        FileOutputStream fos = null;
        ZipOutputStream out = null;
        // dirContents[]获取当前目录(myDir)所有文件对象（包括子目录名)
        File dirContents[] = file.listFiles();
        // 创建临时文件tempFile,使用后删除
        File tempFile;
        try {
            fos = new FileOutputStream(zipFile);
            out = new ZipOutputStream(new BufferedOutputStream(fos, BUFFER));
            // 处理当前目录所有文件对象，包括子目录
            AssertUtil.notNull(dirContents);
            for (File dir : dirContents) {
                // 使用递归方法将当前目录的子目录转成一个ZIP文件，并作为一个ENTRY加进"ORIGIN"
                if (dir.isDirectory()) {
                    tempFile = getZipFile(dir);
                    flag = true;
                }
                // 如果当前文件不是子目录
                else {
                    tempFile = dir;
                    // flag标记tempFile是否由子目录压缩成的ZIP文件
                    flag = false;
                }
                logger.info("Compress file: " + tempFile.getName());
                FileInputStream fis = new FileInputStream(tempFile);
                bis = new BufferedInputStream(fis, BUFFER);
                // 为被读取的文件创建压缩条目
                ZipEntry entry = new ZipEntry(tempFile.getName());
                byte data[] = new byte[BUFFER];
                int count;
                // 在向ZIP输出流写入数据之前，必须首先使用out.putNextEntry(entry); 方法安置压缩条目对象
                out.putNextEntry(entry);
                // 向ZIP 文件写入数据
                while ((count = bis.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                // 关闭输入流
                bis.close();
                // tempFile是临时生成的ZIP文件,删除它
                if (flag) {
                    flag = tempFile.delete();
                    logger.info("Delete file:" + tempFile.getName() + " " + flag);
                }
            }
        } finally {
            FileUtil.close(bis, out, fos);
        }
        return zipFile;
    }

    public static void createZipFile(String sourcePath, String zipPath) throws IOException {
        AssertUtil.isNotBlank("sourcePath", "数据文件路径为空");
        AssertUtil.isNotBlank("zipPath", "压缩文件路径为空");
        OutputStream os = null;
        BufferedOutputStream bos = null;
        ZipOutputStream zos = null;
        try {
            os = new FileOutputStream(zipPath);
            bos = new BufferedOutputStream(os);
            zos = new ZipOutputStream(bos);
            File file = new File(sourcePath);
            String basePath;
            if (file.isDirectory()) {
                basePath = file.getPath();
            } else {
                basePath = file.getParent();
            }
            zipFile(file, basePath, zos);
            zos.closeEntry();
        } finally {
            FileUtil.close(zos, bos, os);
        }
    }

    private static void zipFile(File source, String basePath, ZipOutputStream zos) throws IOException {
        File[] files;
        if (source.isDirectory()) {
            files = source.listFiles();
        } else {
            files = new File[1];
            files[0] = source;
        }

        String pathName;
        byte[] buf = new byte[1024];
        int length;
        InputStream is = null;
        try {
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        pathName = file.getPath().substring(basePath.length() + 1) + "/";
                        zos.putNextEntry(new ZipEntry(pathName));
                        zipFile(file, basePath, zos);
                    } else {
                        pathName = file.getPath().substring(basePath.length() + 1);
                        is = new FileInputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        zos.putNextEntry(new ZipEntry(pathName));
                        while ((length = bis.read(buf)) > 0) {
                            zos.write(buf, 0, length);
                        }
                    }
                }
            }
        } finally {
            FileUtil.close(is);
        }
    }
}
