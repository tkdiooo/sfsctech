package com.sfsctech.core.base.constants;


import com.sfsctech.core.base.enums.BaseEnum;
import com.sfsctech.core.base.enums.StatusEnum;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.servlet.http.HttpServletResponse;

/**
 * Class RpcConstants
 *
 * @author 张麒 2016/9/1.
 * @version Description:
 */
public class RpcConstants {

    public enum Status implements StatusEnum<Integer, String> {

        Successful,
        Failure(300, "操作失败", false),
        // 400 - 499
        NotFound(HttpServletResponse.SC_NOT_FOUND, "资源不存在", false),
        Forbidden(HttpServletResponse.SC_FORBIDDEN, "请求的资源不允许访问", false),
        RequestEntityTooLarge(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, "请求提交的实体数据无法处理", false),
        // 500 - 599
        ServerError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器错误", false),
        // 自定义
        ServerNormal(700, "正常运行", true),
        ServerUpkeep(701, "维护更新", false),
        ServerShutdown(702, "下线停运", false),
        RpcError(801, "远程过程调用错误", false);

        Status() {
            this.code = 200;
            this.description = "操作成功";
            this.successful = true;
        }

        Status(Integer key, String value, boolean successful) {
            this.code = key;
            this.description = value;
            this.successful = successful;
        }

        private int code;
        private String description;
        private boolean successful;

        @Override
        public Integer getCode() {
            return code;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public boolean getSuccessful() {
            return successful;
        }

        @Override
        public String toString() {
            return (new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE)).setExcludeFieldNames("name", "ordinal").toString();
        }

        public static Status getEnum(Integer code) {
            return (Status) StatusEnum.getByCode(values(), code);
        }

        public static String getValueByKey(Integer key) {
            return BaseEnum.findValue(key, values());
        }

        public static Integer getKeyByValue(String value) {
            return BaseEnum.findKey(value, values());
        }

    }
}
