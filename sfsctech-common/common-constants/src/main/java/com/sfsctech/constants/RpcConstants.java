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

    public enum Status implements IEnum<Integer, String>, GsonEnum<Status> {

        Successful,
        Failure(300, "操作失败"),
        ServerError(500, "服务器错误"),
        ServerNormal(501, "正常运行"),
        ServerUpkeep(502, "维护更新"),
        ServerShutdown(503, "下线停运"),
        ClientError(600, "客户端错误"),
        NotFound(404, "资源不存在"),
        PayloadTooLarge(413, "负荷太大");

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

        @Override
        public Integer getCode() {
            return code;
        }

        @Override
        public String getContent() {
            return content;
        }

        @Override
        public Status deserialize(int code) {
            for (Status status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }

        public static String getValueByKey(Integer key) {
            return IEnum.findValue(values(), key);
        }

        public static Integer getKeyByValue(String value) {
            return IEnum.findKey(values(), value);
        }
    }
}
