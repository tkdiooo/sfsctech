package com.sfsctech.mybatis.dao.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.common.util.BeanUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.mybatis.dao.IBaseDao;
import com.sfsctech.mybatis.annotation.Namespace;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class MybatisBaseGenericDAOImpl
 *
 * @author 张麒 2017/6/27.
 * @version Description:
 */
public abstract class BaseDaoImpl<T, PK extends Serializable, Example> extends DynamicDaoImpl implements IBaseDao<T, PK, Example> {

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
    protected String getNamespace() {
        return namespace;
    }

    /**
     * 获取配置文件中的statementName
     *
     * @param name
     * @return
     */
    protected String getStatName(String name) {
        return String.format("%s.%s", getNamespace(), name);
    }

    /**
     * 生成主键值。
     * 默认情况下什么也不做；
     * 如果需要生成主键，需要由子类重写此方法根据需要的方式生成主键值。
     *
     * @param model 要持久化的对象
     */
    protected void generateId(T model) {
    }

    @Override
    public List<T> selectByExample(Example example) {
        return super.getSqlSession().selectList(getStatName("selectByExample"), example);
    }

    @Override
    public T selectByPrimaryKey(PK key) {
        return super.getSqlSession().selectOne(getStatName("selectByPrimaryKey"), key);
    }

    @Override
    public int deleteByPrimaryKey(PK key) {
        return super.getSqlSession().delete(getStatName("deleteByPrimaryKey"), key);
    }

    @Override
    public int deleteByExample(Example example) {
        return super.getSqlSession().delete(getStatName("deleteByExample"), example);
    }

    @Override
    public int insert(T model) {
        generateId(model);
        return super.getSqlSession().insert(getStatName("insert"), model);
    }

    @Override
    public int insertSelective(T model) {
        generateId(model);
        return super.getSqlSession().insert(getStatName("insertSelective"), model);
    }

    @Override
    public long countByExample(Example example) {
        return super.getSqlSession().selectOne(getStatName("countByExample"), example);
    }

    @Override
    public int updateByExampleSelective(T model, Example example) {
        Map<String, Object> params = new HashMap<>();
        params.put("record", model);
        params.put("example", example);
        return super.getSqlSession().update(getStatName("updateByExampleSelective"), params);
    }

    @Override
    public int updateByExample(T model, Example example) {
        Map<String, Object> params = new HashMap<>();
        params.put("record", model);
        params.put("example", example);
        return super.getSqlSession().update(getStatName("updateByExample"), params);
    }

    @Override
    public int updateByPrimaryKeySelective(T model) {
        return super.getSqlSession().update(getStatName("updateByPrimaryKeySelective"), model);
    }

    @Override
    public int updateByPrimaryKey(T model) {
        return super.getSqlSession().update(getStatName("updateByPrimaryKey"), model);
    }

    @Override
    public boolean checkUnique(Example example, String fieldName, Object condition) {
        List<T> list = selectByExample(example);
        return list.size() == 0 || (null != condition && BeanUtil.getPropertyValue(list.get(0), fieldName).equals(condition));
    }

    @Override
    public PageInfo<T> queryPagination(int pageNum, int pageSize, Example example) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(selectByExample(example));
    }

    @Override
    public PageInfo<T> queryPagination(PagingInfo<T> paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        return new PageInfo<>(queryForList("queryPagination", paging.getCondition()));
    }

    protected List<T> queryForList(String name, Object parameter) {
        return super.getSqlSession().selectList(getStatName(name), parameter);
    }

    protected <C> List<C> queryForList(String name, Object parameter, Class<C> cls) {
        return super.getSqlSession().selectList(getStatName(name), parameter);
    }

    protected T queryForObject(String name, Object parameter) {
        return super.getSqlSession().selectOne(getStatName(name), parameter);
    }

    protected <C> C queryForObject(String name, Object parameter, Class<C> cls) {
        return super.getSqlSession().selectOne(getStatName(name), parameter);
    }

}
