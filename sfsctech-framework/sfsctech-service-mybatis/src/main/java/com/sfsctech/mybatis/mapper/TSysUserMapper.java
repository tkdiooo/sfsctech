package com.sfsctech.mybatis.mapper;

import com.sfsctech.mybatis.domain.TSysUser;
import com.sfsctech.mybatis.domain.TSysUserExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TSysUserMapper {
    long countByExample(TSysUserExample example);

    int deleteByExample(TSysUserExample example);

    int deleteByPrimaryKey(Long guid);

    int insert(TSysUser record);

    int insertSelective(TSysUser record);

    List<TSysUser> selectByExample(TSysUserExample example);

    TSysUser selectByPrimaryKey(Long guid);

    int updateByExampleSelective(@Param("record") TSysUser record, @Param("example") TSysUserExample example);

    int updateByExample(@Param("record") TSysUser record, @Param("example") TSysUserExample example);

    int updateByPrimaryKeySelective(TSysUser record);

    int updateByPrimaryKey(TSysUser record);
}