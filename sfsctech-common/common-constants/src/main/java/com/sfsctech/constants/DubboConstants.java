package com.sfsctech.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class DubboConstants
 *
 * @author 张麒 2017/5/26.
 * @version Description:
 */
public class DubboConstants {

    /**
     * dubbo - kryo 需要序列化的类所在包路径，多个包名可以使用英文逗号分隔
     */
    public static Set<String> KRYO_SERIALIZE_PACKAGE = new HashSet<>();

    /**
     * dubbo - kryo 需要序列化的类所在包路径，多个包名可以使用英文逗号分隔
     */
    public static void addKryoSerializePackage(String... kryoSerializePackage) {
        KRYO_SERIALIZE_PACKAGE.addAll(Arrays.asList(kryoSerializePackage));
    }
}
