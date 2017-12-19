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
}
