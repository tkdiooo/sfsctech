package com.sfsctech.support.common.util;

import com.sfsctech.core.base.constants.LabelConstants;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * Class FileUtil
 *
 * @author 张麒 2016/4/7.
 * @version Description:
 */
public class FileUtil extends FileUtils {

    /**
     * 路径转换
     *
     * @param path File Address
     */
    public static String convertPath(String path) {
        AssertUtil.isNotBlank(path, "路径为空");
        return path.replaceAll("\\\\", LabelConstants.FORWARD_SLASH).replaceAll("//", LabelConstants.FORWARD_SLASH);
    }

    /**
     * 物理创建文件
     *
     * @param path File Address
     * @return Boolean
     */
    public static File createFile(String path) {
        AssertUtil.isNotBlank(path, "路径为空");
        String filePath = convertPath(path);
        if (createFolder(filePath)) {
            File file = new File(filePath);
            try {
                file.createNewFile();
                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 物理创建文件夹
     *
     * @param path Folder Address
     * @return Boolean
     */
    public static boolean createFolder(String path) {
        AssertUtil.isNotBlank(path, "路径为空");
        String filePath = convertPath(path);
        int n = filePath.lastIndexOf(LabelConstants.FORWARD_SLASH);
        int m = filePath.lastIndexOf(LabelConstants.PERIOD);
        if (m > n) {
            filePath = filePath.substring(0, n);
        }
        File file = new File(filePath);
        boolean bool = true;
        if (!file.exists())
            bool = file.mkdirs();
        return bool;
    }


    /**
     * 把流对象内容写入文件
     *
     * @param path File Address
     * @param is   InputStream
     */
    public static void writeInputStreamToPath(String path, InputStream is) throws IOException {
        if (null != createFile(path)) {
            BufferedInputStream bis = null;
            FileOutputStream fos = null;
            try {
                byte[] buf = new byte[1024];
                int size;
                // 获取网络输入流
                bis = new BufferedInputStream(is);
                // 建立文件
                fos = new FileOutputStream(path);
                // 保存文件
                while ((size = bis.read(buf)) != -1)
                    fos.write(buf, 0, size);

            } finally {
                close(bis, fos);
            }
        }
    }

    /**
     * 读取文件内容
     *
     * @param path File Address
     * @return File Content
     * @throws IOException
     */
    public static String readFileToString(String path) throws IOException {
        AssertUtil.isNotBlank(path, "文件路径为空");
        return readFileToString(new File(path), getFileEncoding(path));
    }

    /**
     * 读取文件内容
     *
     * @param path File Address
     * @return File Content
     * @throws IOException
     */
    public static List<String> readFileToLines(String path) throws IOException {
        AssertUtil.isNotBlank(path, "文件路径为空");
        return readLines(new File(path), getFileEncoding(path));
    }

    /**
     * 获取当前Class物理路径
     *
     * @return File Address
     */
    public static String getClassRoot() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource("");
        String path = "";
        try {
            if (null != url) path = URLDecoder.decode(url.getFile(), "utf-8").substring(1);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    /**
     * 获取文件编码
     *
     * @param path File Address
     * @return Encoding
     * @throws IOException
     */
    public static String getFileEncoding(String path) throws IOException {
        String code = "GBK";
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(path));
            int p = (bis.read() << 8) + bis.read();
            //其中的 0xefbb、0xfffe、0xfeff、0x5c75这些都是这个文件的前面两个字节的16进制数
            switch (p) {
                case 0xefbb:
                    code = "UTF-8";
                    break;
                case 0xfffe:
                    code = "Unicode";
                    break;
                case 0xfeff:
                    code = "UTF-16BE";
                    break;
                case 0x5c75:
                    code = "ANSI|ASCII";
                    break;
            }
        } finally {
            close(bis);
        }
        return code;
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName File Name
     * @return Suffix
     */
    public static String getFileSuffixName(String fileName) {
        int pos = fileName.lastIndexOf(LabelConstants.PERIOD);
        if (pos == -1) return "NO_EXT_NAME";
        return fileName.substring(pos + 1);
    }


    /**
     * 获取文件名
     *
     * @param path File Address
     * @return File Name
     */
    public static String getFileName(String path) {
        String filePath = convertPath(path);
        return filePath.substring(filePath.lastIndexOf(LabelConstants.FORWARD_SLASH) + 1);
    }


    /**
     * 获取文件名，不包含后缀名
     *
     * @param fileName File Name
     * @return File Name
     */
    public static String getFileBaseName(String fileName) {
        String filePath = convertPath(fileName);
        String name = filePath.substring(filePath.lastIndexOf(LabelConstants.FORWARD_SLASH) + 1);
        int pos = name.lastIndexOf(LabelConstants.PERIOD);
        if (pos == -1) return name;
        else return name.substring(0, pos);
    }

    /**
     * 获取文件夹下所有文件集合
     *
     * @param path folder Address
     * @return List&lt;File&gt;
     */
    public static List<File> getFileFromFolders(String path) {
        String folderPath = convertPath(path);
        File folder = new File(folderPath);
        if (!folder.exists()) {
            throw new RuntimeException("(目录不存在。)folder [" + folder + "]not exist。");
        }
        File[] files = folder.listFiles(File::isDirectory);
        return Arrays.asList(files);
    }

    /**
     * 获取文件夹下所有相同后缀名的文件集合
     *
     * @param path   folder Address
     * @param suffix 后缀名
     * @return List&lt;File&gt;
     */
    public static List<File> getFileFromFolders(String path, final String suffix) {
        String folderPath = convertPath(path);
        File folder = new File(folderPath);
        if (!folder.exists()) {
            throw new RuntimeException("(目录不存在。)folder [" + folder + "]not exist。");
        }
        File[] files = folder.listFiles(pathname -> pathname.getName().toLowerCase().lastIndexOf("." + suffix.toLowerCase()) != -1);
        return Arrays.asList(files);
    }

    /**
     * 文件对象重命名
     *
     * @param file    File
     * @param newName new File Name
     * @return boolean
     */
    public static boolean fileRename(File file, String newName) {
        // 获取文件夹路径
        String path = file.getParent();
        // 获取文件名称
        String name = file.getName();
        // 获取文件后缀名
        String suffix = getFileSuffixName(name);
        // 如果新名称包含后缀
        if (newName.contains(LabelConstants.PERIOD))
            newName = newName.substring(0, newName.indexOf(LabelConstants.PERIOD));
        boolean bool = false;
        // 如果文件存在，执行改名
        if (file.exists()) {
            bool = file.renameTo(new File(path + LabelConstants.FORWARD_SLASH + newName + LabelConstants.PERIOD + suffix));
        }
        return bool;
    }

    /**
     * 文件重命名
     *
     * @param path    File Address
     * @param newName new File Name
     * @return boolean
     */
    public static boolean fileRename(String path, String newName) {
        String filePath = convertPath(path);
        return fileRename(new File(filePath), newName);
    }

    /**
     * 文件是否存在
     *
     * @param path File Address
     * @return boolean
     */
    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 关闭流对象
     *
     * @param closeable interface Closeable
     * @throws IOException
     */
    public static void close(Closeable... closeable) throws IOException {
        for (Closeable c : closeable) {
            if (null != c) {
                c.close();
            }
        }
    }

    /**
     * 图片压缩
     *
     * @param origPath
     * @param newPath
     * @param width
     * @param height
     * @throws IOException
     */
    public static void imageResize(String origPath, String newPath, int width, int height) throws IOException {
        File origFile = new File(origPath);
        AssertUtil.isFile(origFile, "原文件不是标准文件");
        // 后缀
        String suffix = getFileSuffixName(origPath);
        // 创建图片路径
        createFolder(newPath);

        BufferedImage bi = ImageIO.read(origFile);

        // 等比例压缩 :以输入的较小值为准计算压缩比例
        double ratio;

        if (height < width) ratio = (double) height / bi.getHeight();
        else ratio = (double) width / bi.getWidth();

        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
        Image image = op.filter(bi, null);
        // 缩略图文件
        File newFile = new File(newPath);
        ImageIO.write((BufferedImage) image, suffix, newFile);
    }

    /**
     * 获取文件MD5信息
     *
     * @param file
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getFileMD5(File file) throws IOException, NoSuchAlgorithmException {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in = null;
        byte buffer[] = new byte[8192];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } finally {
            close(in);
        }
    }
}
