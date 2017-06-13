package com.sfsctech.dubbox.rmt.encoder.layout;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.dubbox.rmt.util.TraceNoUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Class TraceNoPatternLayout
 *
 * @author 张麒 2017/6/9.
 * @version Description:
 */
public class DubboxTraceNoPatternLayout extends PatternLayout {

    @Override
    public String doLayout(ILoggingEvent event) {
        String msg = super.doLayout(event);
        if (msg != null && TraceNoUtil.getTraceNo() != null) {
            // 如果是异常信息
            if (null != event.getThrowableProxy()) {
                // 有异常信息的，每行都打印跟踪号
                if (msg.endsWith(LabelConstants.NEW_LINE)) {
                    msg = StringUtils.replace(msg.substring(0, msg.length() - 1), LabelConstants.NEW_LINE, (LabelConstants.NEW_LINE + LabelConstants.OPEN_BRACKET + TraceNoUtil.getTraceNo() + LabelConstants.CLOSE_BRACKET + LabelConstants.TAB)) + LabelConstants.NEW_LINE;
                } else {
                    msg = StringUtils.replace(msg, LabelConstants.NEW_LINE, (LabelConstants.NEW_LINE + LabelConstants.OPEN_BRACKET + TraceNoUtil.getTraceNo() + LabelConstants.CLOSE_BRACKET + LabelConstants.TAB));
                }
            }
            //
            else {
                msg = StringUtils.replace(msg, "[traceNo]", (LabelConstants.OPEN_BRACKET + TraceNoUtil.getTraceNo() + LabelConstants.CLOSE_BRACKET));
            }
        }
        return msg;
    }
}
