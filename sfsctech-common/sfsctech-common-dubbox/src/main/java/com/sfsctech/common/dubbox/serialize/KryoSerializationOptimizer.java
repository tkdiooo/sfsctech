package com.sfsctech.common.dubbox.serialize;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.sfsctech.common.dubbox.properties.DubboConfig;
import com.sfsctech.common.util.ClassUtil;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Class KryoSerializationOptimizer
 *
 * @author 张麒 2017/5/22.
 * @version Description:
 */
public class KryoSerializationOptimizer implements SerializationOptimizer {

    @Override
    public Collection<Class> getSerializableClasses() {
        System.out.println(DubboConfig.getKryoSerializePackage());
        Set<Class<?>> set = ClassUtil.getClasses(DubboConfig.getKryoSerializePackage());
        System.out.println(set);
        return new LinkedList<>(set);
    }

}
