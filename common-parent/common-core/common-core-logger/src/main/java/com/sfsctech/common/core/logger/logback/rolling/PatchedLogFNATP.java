package com.sfsctech.common.core.logger.logback.rolling;

import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;
import ch.qos.logback.core.rolling.helper.ArchiveRemover;
import ch.qos.logback.core.rolling.helper.FileNamePattern;

/**
 * Class PatchedLogFNATP
 *
 * @author 张麒 2018-5-4.
 * @version Description:
 */
public class PatchedLogFNATP extends SizeAndTimeBasedFNATP {
    PatchedLogFNATP() {
    }

    protected ArchiveRemover createArchiveRemover() {
        return new PatchedLogArchiveRemover(new FileNamePattern(this.tbrp.getFileNamePattern(), this.context), this.rc);
    }
}
