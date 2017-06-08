package com.sfsctech.common.tools;

import com.sfsctech.common.util.NumberUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class Token
 *
 * @author 张麒 2016/4/14.
 * @version Description:
 */
public class Token {

    private long previous;

    private static Token token = null;

    private Token() {

    }

    public static Token getInstance() {
        if (null == token)
            synchronized (Token.class) {
                if (null == token) token = new Token();
            }
        return token;
    }

    public synchronized String getToken(String id) {
        try {
            long current = System.currentTimeMillis();
            if (current == previous) current++;

            previous = current;

            byte[] now = Long.toString(current).getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(id.getBytes());
            md.update(now);

            return NumberUtil.toHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
