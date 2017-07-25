package com.sfsctech.mybatis.dao.monitor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.cache.inf.ICacheFactory;
import com.sfsctech.cache.inf.ICacheService;
import com.sfsctech.common.util.BeanUtil;
import com.sfsctech.mybatis.dao.IBaseDao;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class SqlMapClientDaoSupportMonitor
 *
 * @author 张麒 2017/6/29.
 * @version Description:
 */
public abstract class MyBatisDaoSupportMonitor<T, PK extends Serializable, Example> extends SqlSessionDaoSupport implements IBaseDao<T, PK, Example> {

    private MybatisCacheMonitor<Example> cacheMonitor = new MybatisCacheMonitor<>();

    protected final void setCacheClient(ICacheFactory<String, Object> cacheClient) {
        cacheMonitor.setCacheClient(cacheClient);
    }

    protected final ICacheService<String, Object> getCacheClient() {
        return cacheMonitor.getCacheClient();
    }

    @Override
    public List<T> selectByExample(Example example) {
        return getSqlSession().selectList(getStatName("selectByExample"), example);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T selectByPrimaryKey(PK key) {
        Object model = getCacheClient().get(cacheMonitor.getCacheKey(getNamespace(), String.valueOf(key)));
        if (null != model) return (T) model;
        else {
            T t = getSqlSession().selectOne(getStatName("selectByPrimaryKey"), key);
            cacheMonitor.putTimeOut(getNamespace(), t, 60 * 10);
            return t;
        }
    }

    @Override
    public int deleteByPrimaryKey(PK key) {
        getCacheClient().remove(cacheMonitor.getCacheKey(getNamespace(), String.valueOf(key)));
        return getSqlSession().delete(getStatName("deleteByPrimaryKey"), key);
    }

    @Override
    public int deleteByExample(Example example) {
        getCacheClient().remove(cacheMonitor.getCacheKey(getNamespace(), example));
        return getSqlSession().delete(getStatName("deleteByExample"), example);
    }

    @Override
    public int insert(T model) {
        generateId(model);
        Integer key = getSqlSession().insert(getStatName("insert"), model);
        cacheMonitor.putTimeOut(getNamespace(), model, 60 * 10);
        return key;
    }

    @Override
    public int insertSelective(T model) {
        generateId(model);
        Integer key = getSqlSession().insert(getStatName("insertSelective"), model);
        cacheMonitor.putTimeOut(getNamespace(), model, 60 * 10);
        return key;
    }

    @Override
    public long countByExample(Example example) {
        return getSqlSession().selectOne(getStatName("countByExample"), example);
    }

    @Override
    public int updateByExampleSelective(T model, Example example) {
        Map<String, Object> params = new HashMap<>();
        params.put("record", model);
        params.put("example", example);
        Integer key = getSqlSession().update(getStatName("updateByExampleSelective"), params);
        cacheMonitor.putTimeOut(getNamespace(), model, 60 * 10);
        return key;
    }

    @Override
    public int updateByExample(T model, Example example) {
        Map<String, Object> params = new HashMap<>();
        params.put("record", model);
        params.put("example", example);
        Integer key = getSqlSession().update(getStatName("updateByExample"), params);
        cacheMonitor.putTimeOut(getNamespace(), model, 60 * 10);
        return key;
    }

    @Override
    public int updateByPrimaryKeySelective(T model) {
        Integer key = getSqlSession().update(getStatName("updateByPrimaryKeySelective"), model);
        cacheMonitor.putTimeOut(getNamespace(), model, 60 * 10);
        return key;
    }

    @Override
    public int updateByPrimaryKey(T model) {
        Integer key = getSqlSession().update(getStatName("updateByPrimaryKey"), model);
        cacheMonitor.putTimeOut(getNamespace(), model, 60 * 10);
        return key;
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
        return getSqlSession().selectList(getStatName(name), parameter);
    }

    protected <C> List<C> queryForList(String name, Object parameter, Class<C> cls) {
        return getSqlSession().selectList(getStatName(name), parameter);
    }

    protected T queryForObject(String name, Object parameter) {
        return getSqlSession().selectOne(getStatName(name), parameter);
    }

    protected <C> C queryForObject(String name, Object parameter, Class<C> cls) {
        return getSqlSession().selectOne(getStatName(name), parameter);
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

    protected abstract void generateId(T model);

    protected abstract String getNamespace();

}
