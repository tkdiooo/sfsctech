package com.sfsctech.common.dubbox.serialize;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.sfsctech.common.base.exception.BizException;
import com.sfsctech.common.base.exception.VerifyException;
import com.sfsctech.common.base.model.PagingInfo;
import com.sfsctech.common.dubbox.properties.DubboConfig;
import com.sfsctech.common.tools.Assert;
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
        Assert.notEmpty(DubboConfig.getKryoSerializePackage(), "dubbox kryo serialization package path can not be empty!");
        DubboConfig.addKryoSerializePackage("com.sfsctech.common.base.result");
        String serializePackage = DubboConfig.getKryoSerializePackagePath();
        classes = new LinkedList<>(ClassUtil.getClasses(serializePackage));
        classes.add(PagingInfo.class);
        classes.add(VerifyException.class);
        classes.add(BizException.class);
    }

    @Override
    public Collection<Class> getSerializableClasses() {
        return classes;
    }

}
