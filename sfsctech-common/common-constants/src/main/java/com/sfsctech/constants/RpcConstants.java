package com.sfsctech.constants;


import com.sfsctech.constants.inf.GsonEnum;
import com.sfsctech.constants.inf.IEnum;

/**
 * Class RpcConstants
 *
 * @author 张麒 2016/9/1.
 * @version Description:
 */
public class RpcConstants {

    public enum Status implements /*IEnum<Integer, String>,*/ GsonEnum<Status> {

        Successful,
        Failure(300, "操作失败"),
        Server_Error(500, "服务器错误"),
        Client_Error(600, "客户端错误"),
        Not_Found(404, "资源不存在"),
        Payload_Too_Large(413, "负荷太大");

        Status() {
            this.code = 200;
            this.content = "操作成功";
        }

        Status(Integer key, String value) {
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
        public Status deserialize(int jsonEnum) {
            System.out.println(jsonEnum);
            return Status.Failure;
        }

        @Override
        public String serialize() {
            return "{" + this.code + ",+\"" + this.content + "\"}";
        }

//        public static String getValueByKey(Integer key) {
//            return IEnum.findValue(values(), key);
//        }
//
//        public static Integer getKeyByValue(String value) {
//            return IEnum.findKey(values(), value);
//        }
    }

    public enum ServiceStatus implements IEnum<Integer, String> {

        Normal(0, "正常运行"),
        Upkeep(1, "维护更新"),
        Shutdown(2, "下线停运");

        ServiceStatus(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        private Integer key;
        private String value;

        @Override
        public Integer getCode() {
            return key;
        }

        @Override
        public String getContent() {
            return value;
        }

        public static String getValueByKey(Integer key) {
            return IEnum.findValue(values(), key);
        }

        public static Integer getKeyByValue(String value) {
            return IEnum.findKey(values(), value);
        }
    }
}
