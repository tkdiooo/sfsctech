package com.sfsctech.support.bootstrap.tools;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.sfsctech.core.base.constants.StatusConstants;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class Bootstrap
 *
 * @author 张麒 2019-6-12.
 * @version Description:
 */
public class Bootstrap {

    public static class Grid {

        public enum Status {

            Danger(StatusConstants.Status.Delete.getCode(), "<span class='label label-danger'>" + StatusConstants.Status.Delete.getDescription() + "</span>"),

            Success(StatusConstants.Status.Valid.getCode(), "<span class='label label-success'>" + StatusConstants.Status.Valid.getDescription() + "</span>"),

            Default(StatusConstants.Status.Disable.getCode(), "<span class='label label-info'>" + StatusConstants.Status.Disable.getDescription() + "</span>"),

            Warning(StatusConstants.Status.Enabled.getCode(), "<span class='label label-warning'>" + StatusConstants.Status.Enabled.getDescription() + "</span>");

            Status(int key, String value) {
                this.key = key;
                this.value = value;
            }

            private int key;
            private String value;

            public Integer getCode() {
                return key;
            }

            public String getDescription() {
                return value;
            }

            private static Map<Integer, String> columns = Lists.newArrayList(values()).stream().collect(Collectors.toMap(Status::getCode, Status::getDescription));

            /**
             * Grid列
             *
             * @return
             */
            public static Map<Integer, String> Column() {
                return columns;
            }
        }
    }

    public static class Select {

        public enum Status {

            Delete(StatusConstants.Status.Delete.getCode(), StatusConstants.Status.Delete.getDescription(), "text-red"),

            Valid(StatusConstants.Status.Valid.getCode(), StatusConstants.Status.Valid.getDescription(), "text-green"),

            Disable(StatusConstants.Status.Disable.getCode(), StatusConstants.Status.Disable.getDescription(), "text-aqua"),

            Enabled(StatusConstants.Status.Enabled.getCode(), StatusConstants.Status.Enabled.getDescription(), "text-yellow");

            Status(int key, String value, String cls) {
                this.key = key;
                this.value = value;
                this.cls = cls;
            }

            private int key;
            private String value;
            private String cls;

            public Integer getCode() {
                return key;
            }

            public String getDescription() {
                return value;
            }

            public String getCls() {
                return cls;
            }

            private static List<Map<String, Object>> columns = (List) Stream.of(values()).map(status -> ImmutableMap.of("value", status.getCode(), "text", status.getDescription(), "class", status.getCls())).collect(Collectors.toList());

            /**
             * Grid列
             *
             * @return
             */
            public static List<Map<String, Object>> Options() {
                return columns;
            }

            /**
             * Grid列
             *
             * @return
             */
            public static List<Map<String, Object>> Options(Status... conditions) {
                return columns.stream().filter(status -> {
                    for (Status condition : conditions) {
                        if (status.get("value") == condition.getCode()) {
                            return true;
                        }
                    }
                    return false;
                }).collect(Collectors.toList());
            }
        }
    }

}
