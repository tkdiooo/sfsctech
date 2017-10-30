package com.sfsctech.constants;

import com.sfsctech.constants.inf.IEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class UIConstants
 *
 * @author 张麒 2017/10/24.
 * @version Description:
 */
public class UIConstants {

    public enum Operation implements IEnum<String, String> {

        Added("operation", "新增"),
        Editor("operation", "编辑");

        Operation(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private String key;
        private String value;

        @Override
        public String getCode() {
            return key;
        }

        @Override
        public String getContent() {
            return value;
        }
    }

    private static String[] OPTIONS_CLASS = {"btn btn-danger", "btn btn-success", "btn btn-info", "btn btn-warning"};

    public static <K, V> List<Map<String, Object>> matchOptions(IEnum<K, V>... enums) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (IEnum<K, V> e : enums) {
            Map<String, Object> options = new HashMap<>();
            options.put("value", e.getCode());
            options.put("text", e.getContent());
            options.put("class", OPTIONS_CLASS[Integer.valueOf(e.getCode().toString())]);
            list.add(options);
        }
        return list;
    }

}
