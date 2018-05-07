//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.config.ex;

public class PropertyTypeConvertorNotExistsException extends RuntimeException {
    private static final long serialVersionUID = 724742002295474170L;

    public PropertyTypeConvertorNotExistsException(Class clazz) {
        super(buildMessage(clazz));
    }

    private static String buildMessage(Class clazz) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("找不到该类的转换器: ").append(clazz.getName());
        return stringBuilder.toString();
    }
}
