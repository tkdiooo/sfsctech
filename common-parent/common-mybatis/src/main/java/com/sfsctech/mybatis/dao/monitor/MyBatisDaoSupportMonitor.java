package com.sfsctech.mybatis.dao.monitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.cache.inf.ICacheFactory;
import com.sfsctech.cache.inf.ICacheService;
import com.sfsctech.common.util.BeanUtil;
import com.sfsctech.constants.LabelConstants;
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

    private ICacheFactory<ICacheService<String, Object>> cacheClient;

    protected void setCacheFactory(ICacheFactory<ICacheService<String, Object>> cacheClient) {
        this.cacheClient = cacheClient;
    }

    protected ICacheService<String, Object> getCacheClient() {
        return this.cacheClient.getCacheClient();
    }

    private String getCacheKey(String namespace, String key) {
        return namespace + LabelConstants.UNDERLINE + key;
    }

    private String getCacheKey(String namespace, Example example) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(example);
        JSONArray jsonArray = jsonObject.getJSONArray("oredCriteria").getJSONObject(0).getJSONArray("allCriteria");
        for (Object json : jsonArray) {
            JSONObject jo = (JSONObject) json;
            if (jo.getString("condition").startsWith("Guid")) {
                return getCacheKey(namespace, jo.getString("value"));
            }
        }
        return "";
    }

    private void putTimeOut(String namespace, String cacheKey, Object value, int timeOut) {
        try {
            getCacheClient().putTimeOut(getCacheKey(namespace, cacheKey), value, timeOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> selectByExample(Example example) {
        return getSqlSession().selectList(getStatName("selectByExample"), example);
    }

    @Override
    public T selectByPrimaryKey(PK key) {
        return getSqlSession().selectOne(getStatName("selectByPrimaryKey"), key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T selectByPrimaryKeyForCache(PK key) {
        Object model = getCacheClient().get(getCacheKey(getNamespace(), String.valueOf(key)));
        if (null != model) return (T) model;
        else {
            T t = this.selectByPrimaryKey(key);
            putTimeOut(getNamespace(), String.valueOf(key), t, 60 * 10);
            return t;
        }
    }

    @Override
    public int deleteByPrimaryKey(PK key) {
        return getSqlSession().delete(getStatName("deleteByPrimaryKey"), key);
    }

    @Override
    public int deleteByPrimaryKeyForCache(PK key) {
        getCacheClient().remove(getCacheKey(getNamespace(), String.valueOf(key)));
        return this.deleteByPrimaryKey(key);
    }

    @Override
    public int deleteByExample(Example example) {
        return getSqlSession().delete(getStatName("deleteByExample"), example);
    }

    @Override
    public int deleteByExampleForCache(Example example) {
        getCacheClient().remove(getCacheKey(getNamespace(), example));
        return this.deleteByExample(example);
    }

    @Override
    public int insert(T model) {
        generateId(model);
        return getSqlSession().insert(getStatName("insert"), model);
    }

    @Override
    public int insertForCache(T model) {
        Integer key = this.insert(model);
        putTimeOut(getNamespace(), String.valueOf(key), model, 60 * 10);
        return key;
    }

    @Override
    public int insertSelective(T model) {
        generateId(model);
        return getSqlSession().insert(getStatName("insertSelective"), model);
    }

    @Override
    public int insertSelectiveForCache(T model) {
        Integer key = this.insertSelective(model);
        putTimeOut(getNamespace(), String.valueOf(key), model, 60 * 10);
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
        return getSqlSession().update(getStatName("updateByExampleSelective"), params);
    }

    @Override
    public int updateByExampleSelectiveForCache(T model, Example example) {
        Integer key = this.updateByExampleSelective(model, example);
        putTimeOut(getNamespace(), String.valueOf(key), model, 60 * 10);
        return key;
    }

    @Override
    public int updateByExample(T model, Example example) {
        Map<String, Object> params = new HashMap<>();
        params.put("record", model);
        params.put("example", example);
        return getSqlSession().update(getStatName("updateByExample"), params);
    }

    @Override
    public int updateByExampleForCache(T model, Example example) {
        Integer key = this.updateByExample(model, example);
        putTimeOut(getNamespace(), String.valueOf(key), model, 60 * 10);
        return key;
    }

    @Override
    public int updateByPrimaryKeySelective(T model) {
        return getSqlSession().update(getStatName("updateByPrimaryKeySelective"), model);
    }

    @Override
    public int updateByPrimaryKeySelectiveForCache(T model) {
        Integer key = this.updateByPrimaryKeySelective(model);
        putTimeOut(getNamespace(), String.valueOf(key), model, 60 * 10);
        return key;
    }

    @Override
    public int updateByPrimaryKey(T model) {
        return getSqlSession().update(getStatName("updateByPrimaryKey"), model);
    }

    @Override
    public int updateByPrimaryKeyForCache(T model) {
        Integer key = this.updateByPrimaryKey(model);
        putTimeOut(getNamespace(), String.valueOf(key), model, 60 * 10);
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

    protected int update(String name, Object parameter) {
        return getSqlSession().update(getStatName(name), parameter);
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
