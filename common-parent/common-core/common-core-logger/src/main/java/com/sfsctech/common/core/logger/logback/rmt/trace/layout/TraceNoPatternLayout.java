package com.sfsctech.common.core.logger.logback.rmt.trace.layout;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.sfsctech.common.core.base.constants.LabelConstants;
import com.sfsctech.common.core.logger.logback.core.pattern.LevelConverter;
import com.sfsctech.common.core.logger.logback.core.pattern.TraceConverter;
import com.sfsctech.common.core.logger.logback.rmt.util.TraceNoUtil;
import com.sfsctech.common.support.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Class TraceNoPatternLayout
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
public class TraceNoPatternLayout extends PatternLayout {

    static {
        defaultConverterMap.put("level", LevelConverter.class.getName());
        defaultConverterMap.put("trace", TraceConverter.class.getName());
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        String msg = super.doLayout(event);
        try {
            System.out.println(new String(msg.getBytes(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (msg != null && event.getThrowableProxy() != null && TraceNoUtil.getTraceNo() != null) {
            // 有异常信息的，每行都打印跟踪号
            if (msg.endsWith(LabelConstants.NEW_LINE)) {
                msg = StringUtils.replace(msg.substring(0, msg.length() - 1), LabelConstants.NEW_LINE, (LabelConstants.NEW_LINE + LabelConstants.OPEN_BRACKET + TraceNoUtil.getTraceNo() + LabelConstants.CLOSE_BRACKET + LabelConstants.TAB)) + LabelConstants.NEW_LINE;
            } else {
                msg = StringUtils.replace(msg, LabelConstants.NEW_LINE, (LabelConstants.NEW_LINE + LabelConstants.OPEN_BRACKET + TraceNoUtil.getTraceNo() + LabelConstants.CLOSE_BRACKET + LabelConstants.TAB));
            }
        }
        return msg;
    }
}
