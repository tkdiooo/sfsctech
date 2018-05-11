package com.sfsctech.common.core.security.tools;

import com.sfsctech.common.core.security.annotation.Encrypt;
import com.sfsctech.common.support.security.EncrypterTool;
import com.sfsctech.common.support.util.BeanUtil;
import com.sfsctech.common.support.util.ObjectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Class SecurityUtil
 *
 * @author 张麒 2017/7/31.
 * @version Description:
 */
public class SecurityUtil {

    public static void Encrypt(Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            Encrypt encrypt = field.getAnnotation(Encrypt.class);
            if (null != encrypt) {
                Object value = BeanUtil.getProperty(obj, field.getName());
                if (field.getType().equals(String.class) && !ObjectUtil.isEmpty(value)) {
                    BeanUtil.setFieldValue(obj, field.getName(), EncrypterTool.encrypt(encrypt.value(), String.valueOf(value)));
                }
            }
        }
    }

    public static void Decrypt(Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            Encrypt encrypt = field.getAnnotation(Encrypt.class);
            if (null != encrypt) {
                Object value = BeanUtil.getProperty(obj, field.getName());
                if (field.getType().equals(String.class) && !ObjectUtil.isEmpty(value)) {
                    BeanUtil.setFieldValue(obj, field.getName(), EncrypterTool.decrypt(encrypt.value(), String.valueOf(value)));
                }
            }
        }
    }
}
