//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util.log;

import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;
import ch.qos.logback.core.rolling.helper.ArchiveRemover;
import ch.qos.logback.core.rolling.helper.FileNamePattern;

class PatchedLogFNATP extends SizeAndTimeBasedFNATP {
    PatchedLogFNATP() {
    }

    protected ArchiveRemover createArchiveRemover() {
        return new PatchedLogArchiveRemover(new FileNamePattern(this.tbrp.getFileNamePattern(), this.context), this.rc);
    }
}
