package com.sfsctech.support.common.security.md5;


import com.sfsctech.support.common.util.AssertUtil;
import com.sfsctech.support.common.util.NumberUtil;
import com.sfsctech.support.common.util.StringUtil;

import java.security.MessageDigest;

/**
 * Class Md5
 *
 * @author 张麒 2016/4/15.
 * @version Description:
 */
public class Md5 {

    // 盐值
    private static final String salt = "08ud7g974Gw5qW08247EH76z";

    private static final String[] hexDigIts = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String encode(String info) {
        return MD5(info, "UTF-8");
    }

    public static String MD5(String info, String encoding) {
        AssertUtil.isNotBlank(info, "加密信息为空");
        try {
            byte[] strTemp;

            if (encoding != null) strTemp = mergeSalt(info).getBytes(encoding);
            else strTemp = mergeSalt(info).getBytes();

            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            return NumberUtil.toHex(mdTemp.digest());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * MD5加密
     */
    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (StringUtil.isBlank(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception ignored) {
        }
        return resultString;
    }

    private static String mergeSalt(String info) {
        if (StringUtil.isBlank(salt)) return info;
        else return info + "{" + salt + "}";
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte value : b) {
            resultSb.append(byteToHexString(value));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }
}
