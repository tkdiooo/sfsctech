package com.sfsctech.rpc.util;

import com.google.gson.*;
import com.sfsctech.constants.inf.GsonEnum;

import java.lang.reflect.Type;

/**
 * Class GsonEnumTypeAdapter
 *
 * @author 张麒 2017-12-18.
 * @version Description:
 */
public class GsonEnumTypeAdapter<E> implements JsonSerializer<E>, JsonDeserializer<E> {

    private final GsonEnum<E> gsonEnum;

    public GsonEnumTypeAdapter(GsonEnum<E> gsonEnum) {
        this.gsonEnum = gsonEnum;
    }

    @Override
    public E deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        System.out.println(1);
        if (null != jsonElement) {
            System.out.println(type);
            return gsonEnum.deserialize(jsonElement.getAsString());
        }
        return null;
    }

    @Override
    public JsonElement serialize(E e, Type type, JsonSerializationContext jsonSerializationContext) {
        if (null != e && e instanceof GsonEnum) {
            return new JsonPrimitive(((GsonEnum) e).serialize());
        }
        return null;
    }
}
