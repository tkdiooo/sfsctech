//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sfsctech.common.cloud.net.ex;

import java.lang.reflect.Method;

public class ParameterStyleException extends RuntimeException {
    private static final long serialVersionUID = 7075442745147862519L;

    public ParameterStyleException(Class interfaceClass, Method method) {
        super(interfaceClass.getName() + "类的" + method.getName() + "方法含有非法的参数定义, 正确的参数定义是一个继承了BaseRequest类的对象");
    }
}
