package com.sfsctech.dubbox.serialize;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.sfsctech.base.exception.BizException;
import com.sfsctech.base.exception.VerifyException;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.common.tools.Assert;
import com.sfsctech.common.util.ClassUtil;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.dubbox.properties.DubboConfig;

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
        DubboConfig.addKryoSerializePackage("com.sfsctech.base.result");
        String serializePackage = DubboConfig.getKryoSerializePackagePath();
        classes = new LinkedList<>(ClassUtil.getClasses(serializePackage));
        classes.add(PagingInfo.class);
        classes.add(VerifyException.class);
        classes.add(BizException.class);
        classes.add(RpcConstants.class);
        classes.add(RpcConstants.Status.class);
    }

    @Override
    public Collection<Class> getSerializableClasses() {
        return classes;
    }

}
