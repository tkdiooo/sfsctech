//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.log;

import com.bestv.common.lang.request.BaseRequest;
import com.bestv.common.lang.result.BaseResult;

public interface CommonNetLogger {
    void logRequest(BaseRequest var1);

    void logResult(BaseResult var1);

    void info(String var1);

    void info(String var1, Object... var2);

    void warn(String var1);

    void warn(String var1, Object... var2);

    void error(String var1);

    void error(String var1, Object... var2);

    void error(String var1, Throwable var2);
}
