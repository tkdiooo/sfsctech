//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.ex;

import java.lang.reflect.Method;

public class ArgumentStyleNotAllowException extends RuntimeException {
    private static final long serialVersionUID = 7074834745109102519L;

    public ArgumentStyleNotAllowException(Class sourceInterfaceInfo, Method method) {
        super(sourceInterfaceInfo.getName() + "类的" + method.getName() + "方法含有非法的参数定义, 正确的参数定义应该是单独一个继承了BaseRequest类的对象");
    }
}
