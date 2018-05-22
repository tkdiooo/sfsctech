package com.sfsctech.support.common.security.base64;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.support.common.util.AssertUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Class Base64
 *
 * @author 张麒 2016/4/15.
 * @version Description:
 */
public class Base64 {

    /**
     * Base64 加密
     *
     * @param bytes information
     * @return cipher
     */
    public static String encrypt(byte[] bytes) {
        AssertUtil.notNull(bytes, "加密信息为空");
        String cipher = new BASE64Encoder().encode(bytes);
        return cipher.replaceAll(LabelConstants.BACK_SLASH + LabelConstants.PLUS, LabelConstants.UNDERLINE)
                .replaceAll(LabelConstants.FORWARD_SLASH, LabelConstants.MINUS)
                .replaceAll(LabelConstants.EQUAL, LabelConstants.PERIOD).replaceAll("\\s", "");
    }


    /**
     * Base64 解密
     *
     * @param info cipher
     * @return information
     */
    public static byte[] decrypt(String info) {
        AssertUtil.isNotBlank(info, "解密信息为空");
        try {
            info = info.replaceAll(LabelConstants.UNDERLINE, LabelConstants.BACK_SLASH + LabelConstants.PLUS)
                    .replaceAll(LabelConstants.MINUS, LabelConstants.FORWARD_SLASH)
                    .replaceAll(LabelConstants.BACK_SLASH + LabelConstants.PERIOD, LabelConstants.EQUAL);
            return new BASE64Decoder().decodeBuffer(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
