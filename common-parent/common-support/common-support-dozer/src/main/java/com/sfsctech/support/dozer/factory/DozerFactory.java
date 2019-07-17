package com.sfsctech.support.dozer.factory;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sfsctech.core.base.domain.model.PagingInfo;
import com.sfsctech.support.common.util.AssertUtil;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Class DozerFactory
 *
 * @author 张麒 2019-7-11.
 * @version Description:
 */
public class DozerFactory {

    @Autowired
    protected Mapper dozerMapper;

    public <T, S> T convert(S s, Class<T> clz) {
        AssertUtil.notNull(s);
        return this.dozerMapper.map(s, clz);
    }

    public <T, S> PagingInfo<T> convert(PagingInfo<S> s, Class<T> clz) {
        AssertUtil.notNull(s);
        PagingInfo<T> resultSet = new PagingInfo<>();
        resultSet.setData(convert(s.getData(), clz));
        resultSet.setCondition(this.dozerMapper.map(s.getCondition(), clz));
        resultSet.setCurrentPage(s.getCurrentPage());
        resultSet.setStart(s.getStart());
        resultSet.setRecordsTotal(s.getRecordsTotal());
        resultSet.setPageSize(s.getPageSize());
        resultSet.setColumns(s.getColumns());
        resultSet.setDraw(s.getDraw());
        resultSet.setLength(s.getLength());
        resultSet.setOrder(s.getOrder());
        return resultSet;
    }

    public <T, S> PagingInfo<T> convert(PageInfo<S> s, PagingInfo<T> t, Class<T> clz) {
        AssertUtil.notNull(s);
        t.setData(convert(s.getList(), clz));
        t.setRecordsTotal(s.getTotal());
        return t;
    }

    public <T, S> List<T> convert(List<S> s, Class<T> clz) {
        AssertUtil.notNull(s);
        List<T> list = Lists.newArrayList();
        s.forEach(v -> list.add(this.dozerMapper.map(v, clz)));
        System.out.println(Lists.newArrayList(s.stream().map(v -> this.dozerMapper.map(v, clz)).iterator()));
        return list;
    }

    public <T, S> Set<T> convert(Set<S> s, Class<T> clz) {
        AssertUtil.notNull(s);
        Set<T> set = Sets.newHashSet();
        s.forEach(v -> set.add(this.dozerMapper.map(v, clz)));
        System.out.println(Sets.newHashSet(s.stream().map(v -> this.dozerMapper.map(v, clz)).iterator()));
        return set;
    }

    @SuppressWarnings("unchecked")
    public <T, S> T[] convert(S[] s, Class<T> clz) {
        AssertUtil.notNull(s);
        T[] arr = (T[]) Array.newInstance(clz, s.length);
        for (int i = 0; i < s.length; i++) {
            arr[i] = this.dozerMapper.map(s[i], clz);
        }
        System.out.println((T[]) Arrays.stream(s).map(v -> this.dozerMapper.map(v, clz)).toArray());
        System.out.println(arr);
        return arr;
    }
}
