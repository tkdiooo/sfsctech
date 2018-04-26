package com.sfsctech.rpc.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.sfsctech.constants.inf.GsonEnum;

import java.lang.reflect.Type;

/**
 * Class GsonEnumTypeAdapter
 *
 * @author 张麒 2017-12-18.
 * @version Description:
 */
public class GsonEnumTypeAdapter<E> implements JsonDeserializer<E> {

    private final GsonEnum<E> gsonEnum;

    public GsonEnumTypeAdapter(GsonEnum<E> gsonEnum) {
        this.gsonEnum = gsonEnum;
    }

    @Override
    public E deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (null != jsonElement) {
            return gsonEnum.deserialize(jsonElement.getAsJsonObject().get("code").getAsInt());
        }
        return null;
    }
}
