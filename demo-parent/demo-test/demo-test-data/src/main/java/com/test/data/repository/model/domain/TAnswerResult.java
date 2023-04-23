package com.test.data.repository.model.domain;

import java.util.Date;

public class TAnswerResult {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_answer_result.ID
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_answer_result.Guid
     *
     * @mbg.generated
     */
    private String guid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_answer_result.GuidExamAnswer
     *
     * @mbg.generated
     */
    private String guidexamanswer;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_answer_result.GuidQuestionsAnswer
     *
     * @mbg.generated
     */
    private String guidquestionsanswer;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_answer_result.Create_Time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_answer_result.ID
     *
     * @return the value of t_answer_result.ID
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_answer_result.ID
     *
     * @param id the value for t_answer_result.ID
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_answer_result.Guid
     *
     * @return the value of t_answer_result.Guid
     *
     * @mbg.generated
     */
    public String getGuid() {
        return guid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_answer_result.Guid
     *
     * @param guid the value for t_answer_result.Guid
     *
     * @mbg.generated
     */
    public void setGuid(String guid) {
        this.guid = guid == null ? null : guid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_answer_result.GuidExamAnswer
     *
     * @return the value of t_answer_result.GuidExamAnswer
     *
     * @mbg.generated
     */
    public String getGuidexamanswer() {
        return guidexamanswer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_answer_result.GuidExamAnswer
     *
     * @param guidexamanswer the value for t_answer_result.GuidExamAnswer
     *
     * @mbg.generated
     */
    public void setGuidexamanswer(String guidexamanswer) {
        this.guidexamanswer = guidexamanswer == null ? null : guidexamanswer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_answer_result.GuidQuestionsAnswer
     *
     * @return the value of t_answer_result.GuidQuestionsAnswer
     *
     * @mbg.generated
     */
    public String getGuidquestionsanswer() {
        return guidquestionsanswer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_answer_result.GuidQuestionsAnswer
     *
     * @param guidquestionsanswer the value for t_answer_result.GuidQuestionsAnswer
     *
     * @mbg.generated
     */
    public void setGuidquestionsanswer(String guidquestionsanswer) {
        this.guidquestionsanswer = guidquestionsanswer == null ? null : guidquestionsanswer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_answer_result.Create_Time
     *
     * @return the value of t_answer_result.Create_Time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_answer_result.Create_Time
     *
     * @param createTime the value for t_answer_result.Create_Time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}