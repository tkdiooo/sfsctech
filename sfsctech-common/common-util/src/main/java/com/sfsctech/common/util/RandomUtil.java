package com.sfsctech.common.util;

import org.apache.commons.lang3.RandomUtils;

import java.util.Random;
import java.util.UUID;

/**
 * Class RandomUtil
 *
 * @author 张麒 2016/4/13.
 * @version Description:
 */
public class RandomUtil extends RandomUtils {

    public static Random random = new Random();

    public enum Strategy {
        Numeric, Char, Full
    }

    /**
     * 生成随机文件名 以UUID的策略生成一个长度为32的字符串，在同一时空中具有唯一性。
     *
     * @return UUID
     */
    public static String getUUID() {
        String id = UUID.randomUUID().toString();
        return id.replace("-", "");
    }

    /**
     * 获取随机值
     *
     * @param strategy Enum Strategy
     * @param length   字符长度
     * @return Random Value
     */
    public static String getRandom(Strategy strategy, int length) {
        if (Strategy.Numeric.equals(strategy)) return getNumber(length);
        else if (Strategy.Char.equals(strategy)) return getCharacter(length);
        else return getCharacterAndNumber(length);
    }

    private static String getNumber(int length) {
        String sRand = "";
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        return sRand;
    }

    private static String getCharacter(int length) {
        String sRand = "";
        for (int i = 0; i < length; i++) {
            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
            sRand += (char) (choice + random.nextInt(26));
        }
        return sRand;
    }

    /**
     * 获取字符和数字组合而成的随机码
     */
    private static String getCharacterAndNumber(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {// 字符串
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                sb.append((char) (choice + random.nextInt(26)));
            } else if ("num".equalsIgnoreCase(charOrNum)) {// 数字
                sb.append(random.nextInt(10));
            }
        }
        return sb.toString();
    }

    /**
     * 获取随机字符串
     *
     * @param length
     * @return
     */
    public static final String randomString(int length) {
        if (length < 1) {
            return "";
        }
        Random randGen = new Random();
        char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
                .toCharArray();
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(61)];
        }
        return new String(randBuffer);
    }

}
