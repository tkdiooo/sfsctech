package com.sfsctech.dubbox.serialize;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.sfsctech.base.annotation.KryoSerializePackage;
import com.sfsctech.base.exception.BizException;
import com.sfsctech.base.exception.VerifyException;
import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.base.model.Column;
import com.sfsctech.base.model.Order;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.base.session.UserAuthData;
import com.sfsctech.common.util.ClassUtil;
import com.sfsctech.constants.DubboConstants;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.rpc.result.ActionResult;

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
