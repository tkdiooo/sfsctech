//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.service.template;

import com.bestv.common.lang.result.BaseResult;

public interface ServiceExecuteTemplate {
    <T extends BaseResult> T executeService(T var1, ServiceCallBack var2);
}
