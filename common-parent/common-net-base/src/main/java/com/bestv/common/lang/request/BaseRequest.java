//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.request;

import com.bestv.common.dto.EnvContext;
import java.io.Serializable;

public class BaseRequest implements Serializable {
    private static final long serialVersionUID = -8265459294122777021L;
    private EnvContext envContext;

    public BaseRequest() {
    }

    public EnvContext getEnvContext() {
        return this.envContext;
    }

    public void setEnvContext(EnvContext envContext) {
        this.envContext = envContext;
    }
}
