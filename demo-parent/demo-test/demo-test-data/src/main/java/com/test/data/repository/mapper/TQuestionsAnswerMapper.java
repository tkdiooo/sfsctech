package com.test.data.repository.mapper;

import com.test.data.repository.model.domain.TQuestionsAnswer;
import com.test.data.repository.model.example.TQuestionsAnswerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TQuestionsAnswerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_questions_answer
     *
     * @mbg.generated
     */
    long countByExample(TQuestionsAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_questions_answer
     *
     * @mbg.generated
     */
    int deleteByExample(TQuestionsAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_questions_answer
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_questions_answer
     *
     * @mbg.generated
     */
    int insert(TQuestionsAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_questions_answer
     *
     * @mbg.generated
     */
    int insertSelective(TQuestionsAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_questions_answer
     *
     * @mbg.generated
     */
    List<TQuestionsAnswer> selectByExample(TQuestionsAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_questions_answer
     *
     * @mbg.generated
     */
    TQuestionsAnswer selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_questions_answer
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TQuestionsAnswer record, @Param("example") TQuestionsAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_questions_answer
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TQuestionsAnswer record, @Param("example") TQuestionsAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_questions_answer
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TQuestionsAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_questions_answer
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TQuestionsAnswer record);
}