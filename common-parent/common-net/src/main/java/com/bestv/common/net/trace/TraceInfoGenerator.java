//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.trace;

import com.bestv.common.dto.TraceInfo;

public interface TraceInfoGenerator {
    void load(TraceInfo var1);

    TraceInfo generate(TraceInfo var1);

    TraceInfo generate();

    void unload(String var1);
}
