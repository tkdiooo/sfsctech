//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.result;

import com.bestv.common.dto.EnvContext;
import com.bestv.common.lang.ex.ErrorContext;
import java.io.Serializable;

public class BaseResult implements Serializable {
    private static final long serialVersionUID = 86423604971101898L;
    private boolean success = false;
    private EnvContext envContext;
    private ErrorContext errorContext;

    public BaseResult() {
    }

    public EnvContext getEnvContext() {
        return this.envContext;
    }

    public void setEnvContext(EnvContext envContext) {
        this.envContext = envContext;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ErrorContext getErrorContext() {
        return this.errorContext;
    }

    public void setErrorContext(ErrorContext errorContext) {
        this.errorContext = errorContext;
    }
}
