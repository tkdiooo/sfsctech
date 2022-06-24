package com.test.data.repository.mapper;

import com.test.data.repository.model.domain.TExamNotice;
import com.test.data.repository.model.example.TExamNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TExamNoticeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_exam_notice
     *
     * @mbg.generated
     */
    long countByExample(TExamNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_exam_notice
     *
     * @mbg.generated
     */
    int deleteByExample(TExamNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_exam_notice
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_exam_notice
     *
     * @mbg.generated
     */
    int insert(TExamNotice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_exam_notice
     *
     * @mbg.generated
     */
    int insertSelective(TExamNotice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_exam_notice
     *
     * @mbg.generated
     */
    List<TExamNotice> selectByExample(TExamNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_exam_notice
     *
     * @mbg.generated
     */
    TExamNotice selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_exam_notice
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TExamNotice record, @Param("example") TExamNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_exam_notice
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TExamNotice record, @Param("example") TExamNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_exam_notice
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TExamNotice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_exam_notice
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TExamNotice record);
}