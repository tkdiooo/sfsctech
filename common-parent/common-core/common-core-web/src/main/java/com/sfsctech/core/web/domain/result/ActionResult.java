package com.sfsctech.core.web.domain.result;


import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.domain.result.BaseResult;
import com.sfsctech.core.base.enums.TipsEnum;
import com.sfsctech.core.spring.constants.I18NConstants;
import com.sfsctech.core.spring.util.ResourceUtil;
import com.sfsctech.core.web.tools.action.ActionHolder;

import java.util.Locale;

/**
 * Controller服务通信Ajax响应对象
 *
 * @author 张麒 2016/4/11.
 * @version Description:
 */
public class ActionResult<T> extends BaseResult {

    private static final long serialVersionUID = -2581960680481338269L;

    private T result;

    private ActionResult() {
        super();
    }

    private ActionResult(T result, String... messages) {
        super();
        this.result = result;
        super.addMessages(messages);
    }

    private ActionResult(T result, RpcConstants.Status status, String... messages) {
        super(status, messages);
        this.result = result;
    }

    private ActionResult(RpcConstants.Status status, String... messages) {
        super(status, messages);
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setMessage(TipsEnum<String, String> tips, String... params) {
        super.getMessages().clear();
        super.addMessages(ResourceUtil.getMessage(tips.getCode(), ActionHolder.getRequest().getLocale(), params));
    }

    public void setMessage(String message) {
        super.getMessages().clear();
        super.addMessages(message);
    }

    public static <T> ActionResult<T> forSuccess() {
        ActionResult<T> result = new ActionResult<>();
        result.addMessages(ResourceUtil.getMessage(I18NConstants.Tips.OperateSuccess.getCode(), ActionHolder.getRequest().getLocale()));
        return result;
    }

    public static <T> ActionResult<T> forSuccess(Locale locale) {
        ActionResult<T> result = new ActionResult<>();
        result.addMessages(ResourceUtil.getMessage(I18NConstants.Tips.OperateSuccess.getCode(), locale));
        return result;
    }

    public static <T> ActionResult<T> forSuccess(T t) {
        return new ActionResult<>(t, ResourceUtil.getMessage(I18NConstants.Tips.OperateSuccess.getCode(), ActionHolder.getRequest().getLocale()));
    }

    public static <T> ActionResult<T> forSuccessInfo(TipsEnum<String, String> tips, String... params) {
        ActionResult<T> result = new ActionResult<>();
        result.addMessages(ResourceUtil.getMessage(tips.getCode(), ActionHolder.getRequest().getLocale(), params));
        return result;
    }

    public static <T> ActionResult<T> forSuccessInfo(T t, TipsEnum<String, String> tips, String... params) {
        return new ActionResult<>(t, ResourceUtil.getMessage(tips.getCode(), ActionHolder.getRequest().getLocale(), params));
    }

    public static <T> ActionResult<T> forFailure() {
        return new ActionResult<>(RpcConstants.Status.Failure, ResourceUtil.getMessage(I18NConstants.Tips.OperateFailure.getCode(), ActionHolder.getRequest().getLocale()));
    }

    public static <T> ActionResult<T> forFailure(Locale locale) {
        return new ActionResult<>(RpcConstants.Status.Failure, ResourceUtil.getMessage(I18NConstants.Tips.OperateFailure.getCode(), locale));
    }

    public static <T> ActionResult<T> forFailure(T t) {
        return new ActionResult<>(t, RpcConstants.Status.Failure, ResourceUtil.getMessage(I18NConstants.Tips.OperateFailure.getCode(), ActionHolder.getRequest().getLocale()));
    }

    public static <T> ActionResult<T> forFailureInfo(TipsEnum<String, String> tips, String... params) {
        return new ActionResult<>(RpcConstants.Status.Failure, ResourceUtil.getMessage(tips.getCode(), ActionHolder.getRequest().getLocale(), params));
    }

    public static <T> ActionResult<T> forFailureInfo(T t, TipsEnum<String, String> tips, String... params) {
        return new ActionResult<>(t, RpcConstants.Status.Failure, ResourceUtil.getMessage(tips.getCode(), ActionHolder.getRequest().getLocale(), params));
    }

    public static <T> ActionResult<T> forStatus(T t, RpcConstants.Status status, TipsEnum<String, String> tips, String... params) {
        return new ActionResult<>(t, status, ResourceUtil.getMessage(tips.getCode(), ActionHolder.getRequest().getLocale(), params));
    }

}
