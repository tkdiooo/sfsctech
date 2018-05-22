package com.sfsctech.dubbo.base.serialize;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.domain.model.Column;
import com.sfsctech.core.base.domain.model.Order;
import com.sfsctech.core.base.domain.model.PagingInfo;
import com.sfsctech.core.base.jwt.JwtToken;
import com.sfsctech.core.base.session.UserAuthData;
import com.sfsctech.core.rpc.result.ActionResult;
import com.sfsctech.core.exception.ex.BizException;
import com.sfsctech.core.exception.ex.VerifyException;
import com.sfsctech.support.common.util.ClassUtil;
import com.sfsctech.dubbo.base.annotation.KryoSerializePackage;
import com.sfsctech.dubbo.base.constants.DubboConstants;

import java.util.*;


/**
 * Class KryoSerializationOptimizer
 *
 * @author 张麒 2017/5/22.
 * @version Description:
 */
public class KryoSerializationOptimizer implements SerializationOptimizer {

    private List<Class> classes = new LinkedList<>();
    private LinkedHashMap<String, Set<Class<?>>> map = new LinkedHashMap<>();

    private void initSerializablePackage() {
        map.put(DubboConstants.BASE_KRYO_SERIALIZE_PATH, ClassUtil.getClasses(DubboConstants.BASE_KRYO_SERIALIZE_PATH));
        // 获取当前需要加载的配置路径
        Set<Class<?>> set = ClassUtil.getClasses(DubboConstants.KRYO_SERIALIZE_PATH);
        for (Class<?> cls : set) {
            if (cls.isAnnotationPresent(KryoSerializePackage.class)) {
                KryoSerializePackage ksp = cls.getAnnotation(KryoSerializePackage.class);
                if (!map.containsKey(ksp.value())) {
                    map.put(ksp.value(), ClassUtil.getClasses(ksp.value()));
                }
            }
        }
    }

    public KryoSerializationOptimizer() {
        initSerializablePackage();
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
        map.forEach((key, value) -> classes.addAll(value));
    }

    @Override
    public Collection<Class> getSerializableClasses() {
        return classes;
    }

}
