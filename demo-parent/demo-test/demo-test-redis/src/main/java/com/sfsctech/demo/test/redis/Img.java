package com.sfsctech.demo.test.redis;

import com.sfsctech.support.common.util.FileUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

/**
 * Class Img
 *
 * @author 张麒 2021-5-10.
 * @version Description:
 */
public class Img {

    /**
     * 获取文件扩展名
     *
     * @param fileName File Name
     * @return Suffix
     */
    public static String getFileSuffixName(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos == -1) return "NO_EXT_NAME";
        return fileName.substring(pos + 1);
    }

    private static BufferedImage readImage(String url) throws Exception {
        File file = new File(url);
        FileInputStream fis = new FileInputStream(file);
        return ImageIO.read(fis);
    }

    public static void conversionImg(String url) throws Exception {
        BufferedImage image = readImage(url);
        int pixe = image.getColorModel().getPixelSize();
        // 如果位深度等于32转换为24
        if (pixe == 32) {
            int width = image.getWidth();
            int height = image.getHeight();
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D gr = bufferedImage.createGraphics();
            //重绘
            gr.drawImage(image.getScaledInstance(width, height, Image.SCALE_DEFAULT), 0, 0, width, height, null);
            gr.dispose();
            // 获取文件后缀
            String suffix = getFileSuffixName(url);
            File file = new File(url + ".24." + suffix);
            ImageIO.write(bufferedImage, suffix, file);
        }
    }


    public static void main(String[] args) throws Exception {
//        conversionImg("D:\\微信图片_20210510092901.png");

        // 读取文件内容以字符串返回
        System.out.println(FileUtil.readFileToString("D:\\40916_史莎莎.txt"));
        System.out.println("-----原始数据");
        System.out.println(new String(FileUtil.readFileToString("D:\\40916_史莎莎.txt").getBytes(),"GBK"));
        System.out.println("-----GBK");
        System.out.println(FileUtil.getFileEncoding("D:\\40916_史莎莎.txt"));
        System.out.println("-----原始数据编码");
    }
}
