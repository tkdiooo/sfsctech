package com.sfsctech.dubbox.serialize;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.sfsctech.base.exception.BizException;
import com.sfsctech.base.exception.VerifyException;
import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.base.model.Column;
import com.sfsctech.base.model.Order;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.base.session.UserAuthData;
import com.sfsctech.common.util.ClassUtil;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.constants.DubboConstants;
import com.sfsctech.base.annotation.KryoSerializePackage;
import com.sfsctech.dubbox.config.DubboConfig;
import com.sfsctech.rpc.result.ActionResult;

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

    private List<Class> classes = new LinkedList<>();

    private void initSerializablePackage() {
        DubboConstants.addKryoSerializePackage("com.sfsctech.base.result");
        Set<Class<?>> set = ClassUtil.getClasses("com.sfsctech.serialize");
        for (Class<?> cls : set) {
            if (cls.isAnnotationPresent(KryoSerializePackage.class)) {
                KryoSerializePackage ksp = cls.getAnnotation(KryoSerializePackage.class);
                DubboConstants.addKryoSerializePackage(ksp.value());
            }
        }
    }

    public KryoSerializationOptimizer() {
        this.initSerializablePackage();
        classes.add(PagingInfo.class);
        classes.add(Column.class);
        classes.add(Order.class);
        classes.add(VerifyException.class);
        classes.add(BizException.class);
        classes.add(RpcConstants.class);
        classes.add(RpcConstants.Status.class);
        classes.add(UserAuthData.class);
        classes.add(ActionResult.class);
        classes.add(JwtToken.class);
        classes.addAll(ClassUtil.getClasses(DubboConfig.getKryoSerializePackagePath()));
    }

    @Override
    public Collection<Class> getSerializableClasses() {
        return classes;
    }

}
