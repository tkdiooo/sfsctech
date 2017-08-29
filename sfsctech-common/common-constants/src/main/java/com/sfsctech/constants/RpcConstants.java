package com.sfsctech.constants;


import com.sfsctech.constants.inf.IEnum;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class RpcConstants
 *
 * @author 张麒 2016/9/1.
 * @version Description:
 */
public class RpcConstants {

    public enum Status implements IEnum<Integer, String> {

        Successful(200, "操作成功"),
        Failure(300, "操作失败"),
        Server_Error(500, "服务器错误"),
        Client_Error(600, "客户端错误");

        Status(Integer key, String value) {
            this.code = key;
            this.content = value;
        }

        private int code;
        private String content;

        @Override
        public Integer getKey() {
            return code;
        }

        @Override
        public String getValue() {
            return content;
        }

        public static String getValueByKey(Integer key) {
            return IEnum.findValue(values(), key);
        }

        public static Integer getKeyByValue(String value) {
            return IEnum.findKey(values(), value);
        }

        @Override
        public String toString() {
            return new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE).setExcludeFieldNames("name", "ordinal").toString();
        }
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
        public Integer getKey() {
            return key;
        }

        @Override
        public String getValue() {
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
