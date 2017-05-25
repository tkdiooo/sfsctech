package com.sfsctech.mybatis.mapper;

import com.sfsctech.mybatis.domain.TSysAccount;
import com.sfsctech.mybatis.domain.TSysAccountExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TSysAccountMapper {
    long countByExample(TSysAccountExample example);

    int deleteByExample(TSysAccountExample example);

    int deleteByPrimaryKey(Long guid);

    int insert(TSysAccount record);

    int insertSelective(TSysAccount record);

    List<TSysAccount> selectByExample(TSysAccountExample example);

    TSysAccount selectByPrimaryKey(Long guid);

    int updateByExampleSelective(@Param("record") TSysAccount record, @Param("example") TSysAccountExample example);

    int updateByExample(@Param("record") TSysAccount record, @Param("example") TSysAccountExample example);

    int updateByPrimaryKeySelective(TSysAccount record);

    int updateByPrimaryKey(TSysAccount record);
}