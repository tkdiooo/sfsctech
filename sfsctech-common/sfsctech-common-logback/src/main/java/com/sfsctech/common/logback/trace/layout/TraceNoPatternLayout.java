package com.sfsctech.common.logback.trace.layout;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.sfsctech.common.constants.LabelConstants;
import com.sfsctech.common.logback.trace.util.TraceNoUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Class TraceNoPatternLayout
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
public class TraceNoPatternLayout extends PatternLayout {

    @Override
    public String doLayout(ILoggingEvent event) {
        String msg = super.doLayout(event);
        System.out.println(TraceNoUtils.getTraceNo());
        if (msg != null && event.getThrowableProxy() != null && TraceNoUtils.getTraceNo() != null) {
            // 有异常信息的，每行都打印跟踪号
            if (msg.endsWith(LabelConstants.NEW_LINE)) {
                msg = StringUtils.replace(msg.substring(0, msg.length() - 1), LabelConstants.NEW_LINE, (LabelConstants.NEW_LINE + LabelConstants.OPEN_BRACKET + TraceNoUtils.getTraceNo() + LabelConstants.CLOSE_BRACKET + LabelConstants.TAB)) + LabelConstants.NEW_LINE;
            } else {
                msg = StringUtils.replace(msg, LabelConstants.NEW_LINE, (LabelConstants.NEW_LINE + LabelConstants.OPEN_BRACKET + TraceNoUtils.getTraceNo() + LabelConstants.CLOSE_BRACKET + LabelConstants.TAB));
            }
        }
        return msg;
    }
}
