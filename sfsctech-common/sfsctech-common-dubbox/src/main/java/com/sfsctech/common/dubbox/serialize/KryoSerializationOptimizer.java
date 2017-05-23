package com.sfsctech.common.dubbox.serialize;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.sfsctech.common.dubbox.properties.DubboConfig;
import com.sfsctech.common.tool.Assert;
import com.sfsctech.common.util.ClassUtil;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Class KryoSerializationOptimizer
 *
 * @author 张麒 2017/5/22.
 * @version Description:
 */
public class KryoSerializationOptimizer implements SerializationOptimizer {

    private List<Class> classes;

    public KryoSerializationOptimizer() {
        Assert.isNotBlank(DubboConfig.getKryoSerializePackage(), "dubbox kryo serialization package path can not be empty!");
        classes = new LinkedList<>(ClassUtil.getClasses(DubboConfig.getKryoSerializePackage()));
    }

    @Override
    public Collection<Class> getSerializableClasses() {
        return classes;
    }

}
