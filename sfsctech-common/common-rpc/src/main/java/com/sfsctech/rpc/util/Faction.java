package com.sfsctech.rpc.util;

import com.sfsctech.constants.inf.GsonEnum;

/**
 * Class Faction
 *
 * @author 张麒 2017-12-18.
 * @version Description:
 */
public enum Faction implements GsonEnum<Faction> {
    Successful,
    Failure(300, "操作失败"),
    Server_Error(500, "服务器错误"),
    Client_Error(600, "客户端错误"),
    Not_Found(404, "资源不存在"),
    Payload_Too_Large(413, "负荷太大");

    Faction() {
        this.code = 200;
        this.content = "操作成功";
    }

    Faction(Integer key, String value) {
        this.code = key;
        this.content = value;
    }

    private int code;
    private String content;

    //        @Override
    public Integer getCode() {
        return code;
    }

    //
//        @Override
    public String getContent() {
        return content;
    }

    @Override
    public Faction deserialize(int jsonEnum) {
        for (Faction faction : values()) {
            if (faction.code == jsonEnum) {
                return faction;
            }
        }
        return null;
    }

    @Override
    public String serialize() {
        return "{code:" + this.code + ",content:" + this.code + "}";
    }

//    @Override
//    public Faction deserialize(String jsonEnum) {
//        return Faction.parse(jsonEnum);
//    }
//
//    @Override
//    public String serialize() {
//        return this.getName();
//    }
}
