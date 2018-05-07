//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util;

import com.bestv.common.config.CommonConfig;
import com.bestv.common.config.ConfigKeyEnum;
import com.bestv.common.constant.Constants;
import com.bestv.common.lang.enums.CommonScenario;
import com.bestv.common.lang.ex.CommonErrorCode;
import com.bestv.common.lang.ex.CommonException;
import com.bestv.common.lang.ex.ErrorContext;
import com.bestv.common.lang.ex.ErrorInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;

public final class ErrorContextBuilder {
    private static final String ERROR_CODE_PREFIX = getErrorCodePrefix();
    private static final String ERROR_CODE_VERSION = getErrorCodeVersion();
    private static final Integer ERROR_BUILDER_STACK_DEPTH = getStackDepth();
    private static final Integer ERROR_BUILDER_STACK_LENGTH = getStackLength();

    public ErrorContextBuilder() {
    }

    public static String buildErrorCode(CommonScenario commonScenario, CommonErrorCode commonErrorCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ERROR_CODE_PREFIX).append(ERROR_CODE_VERSION).append(commonErrorCode.getInfoLevel().getCode()).append(commonErrorCode.getInfoType().getCode()).append(commonScenario.getAppCode()).append(commonScenario.getCode()).append(commonErrorCode.getCode());
        return stringBuilder.toString();
    }

    public static ErrorContext buildErrorContext(CommonScenario commonScenario, CommonException e) {
        ErrorContext errorContext = new ErrorContext();
        CommonErrorCode commonErrorCode = e.getErrorCode();
        String errorCode = buildErrorCode(commonScenario, commonErrorCode);
        errorContext.setErrorMessage(e.getMessage());
        errorContext.setErrorCode(errorCode);
        List<ErrorInfo> errorInfos = errorContext.getErrorInfos();
        if (CollectionUtils.isEmpty((Collection)errorInfos)) {
            errorInfos = new ArrayList();
            errorContext.setErrorInfos((List)errorInfos);
        }

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorCode(errorCode);
        errorInfo.setStackTraceElements(getStackTraceElementsWithDepth(e, ERROR_BUILDER_STACK_DEPTH, ERROR_BUILDER_STACK_LENGTH));
        ((List)errorInfos).add(errorInfo);
        return errorContext;
    }

    private static StackTraceElement[] getStackTraceElementsWithDepth(CommonException e, int depth, int length) {
        if (length <= 0) {
            return null;
        } else {
            StackTraceElement[] sourceStackTraceElements = e.getStackTrace();
            int leaseDepth = sourceStackTraceElements.length - depth;
            if (leaseDepth < 0) {
                leaseDepth = 0;
            }

            int targetLength = leaseDepth > length ? length : leaseDepth;
            if (leaseDepth <= 0) {
                return null;
            } else {
                StackTraceElement[] targetStackTraceElements = new StackTraceElement[targetLength];

                for(int targetDepth = 0; targetDepth < targetLength; ++targetDepth) {
                    int sourceDepth = targetDepth + depth;
                    targetStackTraceElements[targetDepth] = sourceStackTraceElements[sourceDepth];
                }

                return targetStackTraceElements;
            }
        }
    }

    private static String getErrorCodePrefix() {
        String errorCodePrefix = (String)CommonConfig.getProperty(ConfigKeyEnum.BIZ_ERROR_BUILDER_ERROR_CODE_PREFIX);
        return errorCodePrefix == null ? "CP" : errorCodePrefix;
    }

    private static String getErrorCodeVersion() {
        String errorCodeVersion = (String)CommonConfig.getProperty(ConfigKeyEnum.BIZ_ERROR_BUILDER_ERROR_CODE_VERSION);
        return errorCodeVersion == null ? "1" : errorCodeVersion;
    }

    private static Integer getStackDepth() {
        Integer errorBuilderStackDepth = (Integer)CommonConfig.getProperty(ConfigKeyEnum.BIZ_ERROR_BUILDER_STACK_DEPTH);
        return errorBuilderStackDepth == null ? Constants.DEFAULT_ERROR_BUILDER_STACK_DEPTH : errorBuilderStackDepth;
    }

    private static Integer getStackLength() {
        Integer errorBuilderStackLength = (Integer)CommonConfig.getProperty(ConfigKeyEnum.BIZ_ERROR_BUILDER_STACK_LENGTH);
        return errorBuilderStackLength == null ? Constants.DEFAULT_ERROR_BUILDER_STACK_LENGTH : errorBuilderStackLength;
    }
}
