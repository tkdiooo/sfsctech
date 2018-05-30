package com.sfsctech.core.rpc.result;


import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.domain.result.BaseResult;
import com.sfsctech.core.base.enums.StatusEnum;
import com.sfsctech.core.base.enums.TipsEnum;
import com.sfsctech.core.rpc.util.RpcUtil;
import com.sfsctech.core.spring.constants.I18NConstants;
import com.sfsctech.core.spring.util.ResourceUtil;

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

    private ActionResult(T result, StatusEnum<Integer, String> status, String... messages) {
        super(status, messages);
        this.result = result;
    }

    private ActionResult(StatusEnum<Integer, String> status, String... messages) {
        super(status, messages);
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static <T> ActionResult<T> forSuccess() {
        ActionResult<T> result = new ActionResult<>();
        result.addMessages(ResourceUtil.getMessage(I18NConstants.Tips.OperateSuccess.getCode(), RpcUtil.getRequest().getLocale()));
        return result;
    }

    public static <T> ActionResult<T> forSuccess(T t) {
        return new ActionResult<>(t, ResourceUtil.getMessage(I18NConstants.Tips.OperateSuccess.getCode(), RpcUtil.getRequest().getLocale()));
    }

    public static <T> ActionResult<T> forSuccessInfo(TipsEnum<String, String> tips, String... params) {
        ActionResult<T> result = new ActionResult<>();
        result.addMessages(ResourceUtil.getMessage(tips.getCode(), RpcUtil.getRequest().getLocale(), params));
        return result;
    }

    public static <T> ActionResult<T> forSuccessInfo(T t, TipsEnum<String, String> tips, String... params) {
        return new ActionResult<>(t, ResourceUtil.getMessage(tips.getCode(), RpcUtil.getRequest().getLocale(), params));
    }

    public static <T> ActionResult<T> forFailure() {
        return new ActionResult<>(RpcConstants.Status.Failure, ResourceUtil.getMessage(I18NConstants.Tips.OperateFailure.getCode(), RpcUtil.getRequest().getLocale()));
    }

    public static <T> ActionResult<T> forFailure(T t) {
        return new ActionResult<>(t, RpcConstants.Status.Failure, ResourceUtil.getMessage(I18NConstants.Tips.OperateFailure.getCode(), RpcUtil.getRequest().getLocale()));
    }

    public static <T> ActionResult<T> forFailureInfo(TipsEnum<String, String> tips, String... params) {
        return new ActionResult<>(RpcConstants.Status.Failure, ResourceUtil.getMessage(tips.getCode(), RpcUtil.getRequest().getLocale(), params));
    }

    public static <T> ActionResult<T> forFailureInfo(T t, TipsEnum<String, String> tips, String... params) {
        return new ActionResult<>(t, RpcConstants.Status.Failure, ResourceUtil.getMessage(tips.getCode(), RpcUtil.getRequest().getLocale(), params));
    }

    public static <T> ActionResult<T> forStatus(T t, StatusEnum<Integer, String> status, TipsEnum<String, String> tips, String... params) {
        return new ActionResult<>(t, status, ResourceUtil.getMessage(tips.getCode(), RpcUtil.getRequest().getLocale(), params));
    }

}