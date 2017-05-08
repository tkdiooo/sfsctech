package com.sfsctech.common.constants;


import com.sfsctech.common.inf.IEnum;

/**
 * Class RpcConstants
 *
 * @author 张麒 2016/9/1.
 * @version Description:
 */
public class RpcConstants {

    public static final boolean SUCCESSFUL = true;

    public static final boolean FAILURE = false;

    public enum ResponseCode implements IEnum<Integer, String> {

        SC_OK(200, "OK"),
        SC_ROUTE_CHANGE(300, "Route change"),
        SC_CLIENT_ERROR(400, "Client Error"),
        SC_SERVER_ERROR(500, "Server Error");

        ResponseCode(Integer key, String value) {
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
