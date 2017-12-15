package com.sfsctech.rpc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.sfsctech.common.util.JsonUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.constants.StatusConstants;
import com.sfsctech.rpc.result.ActionResult;
import org.slf4j.Logger;

/**
 * Class RpcUtil
 *
 * @author 张麒 2016/9/1.
 * @version Description:
 */
public class RpcUtil {

    public static <T> boolean logPrint(ActionResult<T> result, Logger logger) {
        if (!result.isSuccess()) {
            logger.error(JsonUtil.toJSONString(result.getStatus()));
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
        }
        return result.isSuccess();
    }

    @SuppressWarnings({"unchecked"})
    public static <T> ActionResult<T> parseActionResult(String json) {
        ParserConfig config = ParserConfig.getGlobalInstance();
        config.setAutoTypeSupport(true);
        config.putDeserializer(RpcConstants.Status.class, new StatusDeserializer());
        return (ActionResult<T>) JSON.parseObject(json, ActionResult.class, config);
    }

//    public static <T> ActionResult<T> sendRpcResult(boolean success, RpcConstants.ResponseCode responseCode, String... messages) {
//        ActionResult<T> ar = new ActionResult<>();
//        ar.setSuccess(success);
//        ar.setResponseCode(responseCode);
//        ar.setMessages(ListUtil.toList(messages));
//        return ar;
//    }

//    public static <T> ActionResult<T> getRpcResult(ActionResult<T> ar) {
//        // 接口返回错误，处理错误信息
//        if (ar.isSuccess()) {
//            System.out.println(ar.getMessages());
//        }
//        return ar;
//    }

//    @SuppressWarnings("unchecked")
//    public static <T> List<T> getList(String paramKey, RpcResult<T> ar) {
//        // 接口异常捕获
////        catchException(ar);
//        List<T> result = null;
//        // 如果返回结果不为空
//        if (null != ar.getAttachs().get(paramKey)) {
//            result = (List<T>) ar.getAttachs().get(paramKey);
//        } else {
//            result = new ArrayList<>();
//        }
//        return result;
//    }
//
//    @SuppressWarnings("unchecked")
//    public static <T> T getObject(Class<T> cls, String paramKey, RpcResult ar) {
//        // 接口异常捕获
////        catchException(ar);
//        T result = null;
//        // 如果返回结果不为空
//        if (null != ar.getAttachs().get(paramKey)) {
//            result = (T) ar.getAttachs().get(paramKey);
//        }
//        return result;
//    }
//
//    public static void catchException(RpcResult ar) {
//        // 对象等于null、或者返回结果是false，抛出异常
////        if (!(null != ar && ar.getSuccess())) {
////            if (null != ar.getMessages() && ar.getMessages().size() > 0) {
////                throw new BizException(ar.getMessages().get(0));
////            } else {
////                throw new BizException("系统异常");
////            }
////        }
//    }
}
