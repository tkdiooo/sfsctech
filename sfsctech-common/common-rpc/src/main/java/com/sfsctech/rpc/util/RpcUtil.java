package com.sfsctech.rpc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.google.gson.*;
import com.sfsctech.base.result.BaseResult;
import com.sfsctech.common.util.JsonUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.constants.StatusConstants;
import com.sfsctech.constants.inf.GsonEnum;
import com.sfsctech.rpc.result.ActionResult;
import org.slf4j.Logger;

import java.lang.reflect.Type;
import java.time.LocalDate;

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

    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .serializeNulls()
//                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(Faction.class, new GsonEnumTypeAdapter<>(Faction.Successful))
//                .registerTypeAdapter(RpcConstants.Status.class, new GsonEnumTypeAdapter<RpcConstants.Status>() {
//
//                    @Override
//                    public JsonElement serialize(RpcConstants.Status status, Type type, JsonSerializationContext jsonSerializationContext) {
//                        return new JsonPrimitive(status.ordinal());
//                    }
//
//                    @Override
//                    public RpcConstants.Status deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//                        System.out.println(1);
//                        if (null != jsonElement) {
//                            System.out.println(type);
//                            return RpcConstants.Status.values()[jsonElement.getAsInt()];
//                        }
//                        return null;
//                    }
//                })
//                .registerTypeAdapter(Faction.class, new GsonEnumTypeAdapter<>(RpcConstants.Status.Successful))
                .create();
        BaseResult result = new BaseResult();
        Person p1 = new Person("雷卡", Faction.Successful);
        System.out.println("调用 toString 方法：\n" + p1);
        String jsonText = gson.toJson(p1);
        System.out.println("将 person 转换成 json 字符串：\n" + jsonText);

        System.out.println("-------------------");

        Person p2 = gson.fromJson(jsonText, Person.class);
        System.out.println("根据 json 字符串生成 person 实例：\n" + p2);
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
