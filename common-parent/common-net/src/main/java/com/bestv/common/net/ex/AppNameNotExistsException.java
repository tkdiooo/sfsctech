//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.ex;

public class AppNameNotExistsException extends RuntimeException {
    private static final long serialVersionUID = -6470366905349548468L;

    public AppNameNotExistsException(Class sourceInterfaceInfo) {
        super("接口" + sourceInterfaceInfo.getName() + "的appName不存在, 请在该接口上打上CommonNet注解并配置appName");
    }
}
