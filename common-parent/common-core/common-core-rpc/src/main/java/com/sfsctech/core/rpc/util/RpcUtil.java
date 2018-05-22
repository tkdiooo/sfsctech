package com.sfsctech.core.rpc.util;

import com.sfsctech.core.rpc.result.ActionResult;
import org.slf4j.Logger;

/**
 * Class RpcUtil
 *
 * @author 张麒 2016/9/1.
 * @version Description:
 */
// TODO
public class RpcUtil {

    public static <T> boolean logPrint(ActionResult<T> result, Logger logger) {
        if (!result.isSuccess()) {
//            logger.error(JsonUtil.toJSONString(result.getStatus()));
//            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
        }
        return result.isSuccess();
    }

//    public static <T, E> ActionResult<T> parseActionResult(String json, GsonEnum<E> gsonEnum, Type type) {
//        Gson gson = new GsonBuilder()
//                .serializeNulls()
//                .registerTypeAdapter(gsonEnum.getClass(), new GsonEnumTypeAdapter<>(gsonEnum))
//                .create();
//        return gson.fromJson(json, type);
//    }
}
