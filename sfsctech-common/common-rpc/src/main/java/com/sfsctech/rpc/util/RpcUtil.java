package com.sfsctech.rpc.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sfsctech.common.util.JsonUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.inf.GsonEnum;
import com.sfsctech.rpc.adapter.GsonEnumTypeAdapter;
import com.sfsctech.rpc.result.ActionResult;
import org.slf4j.Logger;

import java.lang.reflect.Type;

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

    public static <T, E> ActionResult<T> parseActionResult(String json, GsonEnum<E> gsonEnum, Type type) {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(gsonEnum.getClass(), new GsonEnumTypeAdapter<>(gsonEnum))
                .create();
        return gson.fromJson(json, type);
    }

    public static void main(String[] args) {
//        ActionResult result = new ActionResult();
//        String jsonText = JsonUtil.toJSONString(result);
//        System.out.println("将 person 转换成 json 字符串：\n" + jsonText);
//
//        System.out.println("-------------------");
//
//        String json = "{\"attachs\":{\"_csrf\":{\"parameterName\":\"KT__7jneScau7JFxmq5kjw\",\"token\":\"Dvo46H1b42XJddXVAXet7B\"}},\"dataSet\":[{\"content\":\"前端网站\",\"description\":\"\",\"guid\":\"BkQpsuqP77zR1N2piEXiaQ\",\"id\":20,\"number\":\"0001500001\",\"parent\":\"NVA29rgUnbVFkCqwSTd2U1\",\"sort\":0,\"status\":1},{\"content\":\"后台管理\",\"description\":\"\",\"guid\":\"BMqrsGueGfWwqoqjxVkyHF\",\"id\":21,\"number\":\"0001500002\",\"parent\":\"NVA29rgUnbVFkCqwSTd2U1\",\"sort\":0,\"status\":1},{\"content\":\"前端业务\",\"description\":\"\",\"guid\":\"BKHZekP87RjPpAiDygCwxa\",\"id\":22,\"number\":\"0001500003\",\"parent\":\"NVA29rgUnbVFkCqwSTd2U1\",\"sort\":0,\"status\":1},{\"content\":\"后台业务\",\"description\":\"\",\"guid\":\"8YPaR5HFzJUgd2oQMW6zZ3\",\"id\":23,\"number\":\"0001500004\",\"parent\":\"NVA29rgUnbVFkCqwSTd2U1\",\"sort\":0,\"status\":1}],\"messages\":[\"操作成功\"],\"result\":null,\"status\":{\"code\":300,\"content\":\"操作成功\"},\"success\":true}";
//
//        ActionResult<DictionaryDto> p2 = RpcUtil.parseActionResult(json, RpcConstants.Status.Successful, new TypeToken<ActionResult<DictionaryDto>>() {
//        }.getType());
//        System.out.println("根据 json 字符串生成 person 实例：\n" + p2);
//        new ArrayList<DictionaryDto>();
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
