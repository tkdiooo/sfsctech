//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.log.factory;

import com.bestv.common.net.log.CommonNetLogger;
import com.bestv.common.net.log.LoggerTypeEnum;

public interface CommonNetLoggerFactory<T> {
    CommonNetLogger getLogger(T var1, LoggerTypeEnum var2);
}
