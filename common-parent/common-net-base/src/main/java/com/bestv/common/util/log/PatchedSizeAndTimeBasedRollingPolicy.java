//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util.log;

import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import java.lang.reflect.Field;

public class PatchedSizeAndTimeBasedRollingPolicy<E> extends TimeBasedRollingPolicy<E> {
    private static final String FILE_NAME_AND_TRIGGER_POLICY_FIELD_NAME = "timeBasedFileNamingAndTriggeringPolicy";
    private FileSize maxFileSize;

    public PatchedSizeAndTimeBasedRollingPolicy() {
    }

    public void start() {
        PatchedLogFNATP sizeAndTimeBasedFNATP = new PatchedLogFNATP();
        if (this.maxFileSize == null) {
            this.addError("未配置最大文件大小参数");
        } else {
            this.addInfo("每个日志文件大小都会限制在 [" + this.maxFileSize + "] 内.");
            sizeAndTimeBasedFNATP.setMaxFileSize(this.maxFileSize);

            try {
                Field timeBasedFileNamingAndTriggeringPolicyField = TimeBasedRollingPolicy.class.getDeclaredField("timeBasedFileNamingAndTriggeringPolicy");
                timeBasedFileNamingAndTriggeringPolicyField.setAccessible(true);
                timeBasedFileNamingAndTriggeringPolicyField.set(this, sizeAndTimeBasedFNATP);
                timeBasedFileNamingAndTriggeringPolicyField.setAccessible(false);
                this.addInfo("已向策略中载入 文件名触发器策略 对象: " + sizeAndTimeBasedFNATP.toString());
            } catch (Exception var3) {
                this.addError("载入 文件名触发器策略 对象: " + sizeAndTimeBasedFNATP.toString() + "失败");
            }

            if (!this.isUnboundedTotalSizeCap() && this.totalSizeCap.getSize() < this.maxFileSize.getSize()) {
                this.addError("全日志文件大小限制: " + this.totalSizeCap.toString() + " 不能小于最大日志文件大小限制: " + this.maxFileSize.getSize());
            } else {
                super.start();
            }
        }
    }

    public void setMaxFileSize(FileSize aMaxFileSize) {
        this.maxFileSize = aMaxFileSize;
    }

    public String toString() {
        return "c.q.l.core.rolling.SizeAndTimeBasedRollingPolicy@" + this.hashCode();
    }
}
