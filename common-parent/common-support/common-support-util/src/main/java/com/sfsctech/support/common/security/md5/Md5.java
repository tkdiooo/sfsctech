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

    private static String mergeSalt(String info) {
        if (StringUtil.isBlank(salt)) return info;
        else return info + "{" + salt + "}";
    }
}
