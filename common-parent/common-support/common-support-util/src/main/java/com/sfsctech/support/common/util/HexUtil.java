package com.sfsctech.support.common.util;

import java.util.ArrayList;

/**
 * Class HexUtil
 *
 * @author 张麒 2016/5/27.
 * @version Description:
 */
public class HexUtil {


    private static char[] hexChar = new char[]{'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static byte[] toHex(byte[] digestByte) {
        byte[] rtChar = new byte[digestByte.length * 2];
        for (int i = 0; i < digestByte.length; i++) {
            byte b1 = (byte) (digestByte[i] >> 4 & 0x0f);
            byte b2 = (byte) (digestByte[i] & 0x0f);
            rtChar[i * 2] = (byte) (b1 < 10 ? b1 + 48 : b1 + 55);
            rtChar[i * 2 + 1] = (byte) (b2 < 10 ? b2 + 48 : b2 + 55);
        }
        return rtChar;
    }

    public static String toHexString(byte[] digestByte) {
        return new String(toHex(digestByte));
    }

    public static byte[] fromHex(byte[] sc) {
        byte[] res = new byte[sc.length / 2];
        for (int i = 0; i < sc.length; i++) {
            byte c1 = (byte) (sc[i] - 48 < 17 ? sc[i] - 48 : sc[i] - 55);
            i++;
            byte c2 = (byte) (sc[i] - 48 < 17 ? sc[i] - 48 : sc[i] - 55);
            res[i / 2] = (byte) (c1 * 16 + c2);
        }
        return res;
    }

    public static byte[] fromHexString(String hex) {
        return fromHex(hex.getBytes());
    }

    public static String encode(String in) {
        return new String(toHex(in.getBytes()));
    }

    public static byte[] strToByte(String inStr) {
        if (!StringUtil.isNumeric(inStr)) {
            return new byte[0];
        }
        ArrayList<Byte> byteList = new ArrayList<>();
        for (int i = 0; i < inStr.length(); i++) {
            byteList.add(Byte.parseByte(String.valueOf(inStr.charAt(i))));
        }
        byte[] byteArray = new byte[byteList.size()];
        for (int j = 0; j < byteArray.length; j++) {
            byteArray[j] = byteList.get(j);
        }
        return byteArray;
    }

    public static String decode(String in) {
        return new String(fromHex(in.getBytes()));
    }

    public static String getEncryptKey() {
        return RandomUtil.randomString(24);
    }


    public static int getHashCode() {
        return RandomUtil.randomString(10).hashCode();
    }

    public static void main(String args[]) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
            System.out.println(getHashCode());
        System.out.println(System.currentTimeMillis() - startTime);
    }

    /**
     * 16进制 To byte[]<br>
     * <font color='red'> fix byte[] en_pwd = new BigInteger(pwd, 16).toByteArray();bug</font>
     *
     * @param hexString
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }


    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
