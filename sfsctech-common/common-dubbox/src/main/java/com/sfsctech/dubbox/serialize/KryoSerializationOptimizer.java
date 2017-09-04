package com.sfsctech.dubbox.serialize;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.sfsctech.auth.session.UserAuthData;
import com.sfsctech.base.exception.BizException;
import com.sfsctech.base.exception.VerifyException;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.common.tools.Assert;
import com.sfsctech.common.util.ClassUtil;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.constants.DubboConstants;
import com.sfsctech.dubbox.properties.DubboConfig;
import com.sfsctech.rpc.result.ActionResult;

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
        Assert.notEmpty(DubboConstants.KRYO_SERIALIZE_PACKAGE, "dubbox kryo serialization package path can not be empty!");
        DubboConstants.addKryoSerializePackage("com.sfsctech.base.result");
        classes = new LinkedList<>(ClassUtil.getClasses(DubboConfig.getKryoSerializePackagePath()));
        classes.add(PagingInfo.class);
        classes.add(VerifyException.class);
        classes.add(BizException.class);
        classes.add(RpcConstants.class);
        classes.add(RpcConstants.Status.class);
        classes.add(UserAuthData.class);
        classes.add(ActionResult.class);
    }

    @Override
    public Collection<Class> getSerializableClasses() {
        return classes;
    }

}
