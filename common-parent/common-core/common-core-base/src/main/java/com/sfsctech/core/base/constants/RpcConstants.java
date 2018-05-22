package com.sfsctech.core.base.constants;


import com.sfsctech.core.base.enums.BaseEnum;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class RpcConstants
 *
 * @author 张麒 2016/9/1.
 * @version Description:
 */
public class RpcConstants {

    public enum Status implements BaseEnum<Integer, String> {

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
            this.description = "操作成功";
        }

        Status(Integer key, String value) {
            this.code = key;
            this.description = value;
        }

        private int code;
        private String description;

        @Override
        public Integer getCode() {
            return code;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return (new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE)).setExcludeFieldNames("name", "ordinal").toString();
        }

        public static Status getEnum(Integer code) {
            return (Status) BaseEnum.getByCode(values(), code);
        }

        public static String getValueByKey(Integer key) {
            return BaseEnum.findValue(values(), key);
        }

        public static Integer getKeyByValue(String value) {
            return BaseEnum.findKey(values(), value);
        }
    }
}
