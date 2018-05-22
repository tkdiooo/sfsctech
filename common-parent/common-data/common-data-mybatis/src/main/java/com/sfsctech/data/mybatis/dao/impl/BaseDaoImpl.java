package com.sfsctech.data.mybatis.dao.impl;


import com.sfsctech.data.mybatis.annotation.Namespace;
import com.sfsctech.support.common.util.StringUtil;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * Class MybatisBaseGenericDAOImpl
 *
 * @author 张麒 2017/6/27.
 * @version Description:
 */
public abstract class BaseDaoImpl<T, PK extends Serializable, Example> extends DynamicDaoImpl<T, PK, Example> {

    private String namespace;

    @PostConstruct
    private void initClient() {
        if (StringUtil.isEmpty(namespace)) {
            Namespace annotation = getClass().getAnnotation(Namespace.class);
            if (annotation != null) {
                namespace = annotation.value();
            }
        }
    }

    /**
     * 获取Namespace，默认为Annotation名称
     *
     * @return
     */
    @Override
    protected String getNamespace() {
        return namespace;
    }

    /**
     * 生成主键值。
     * 默认情况下什么也不做；
     * 如果需要生成主键，需要由子类重写此方法根据需要的方式生成主键值。
     *
     * @param model 要持久化的对象
     */
    @Override
    protected void generateId(T model) {
    }
}
