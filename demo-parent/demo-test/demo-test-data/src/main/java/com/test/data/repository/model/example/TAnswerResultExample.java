package com.test.data.repository.model.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TAnswerResultExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    public TAnswerResultExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGuidIsNull() {
            addCriterion("Guid is null");
            return (Criteria) this;
        }

        public Criteria andGuidIsNotNull() {
            addCriterion("Guid is not null");
            return (Criteria) this;
        }

        public Criteria andGuidEqualTo(String value) {
            addCriterion("Guid =", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidNotEqualTo(String value) {
            addCriterion("Guid <>", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidGreaterThan(String value) {
            addCriterion("Guid >", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidGreaterThanOrEqualTo(String value) {
            addCriterion("Guid >=", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidLessThan(String value) {
            addCriterion("Guid <", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidLessThanOrEqualTo(String value) {
            addCriterion("Guid <=", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidLike(String value) {
            addCriterion("Guid like", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidNotLike(String value) {
            addCriterion("Guid not like", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidIn(List<String> values) {
            addCriterion("Guid in", values, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidNotIn(List<String> values) {
            addCriterion("Guid not in", values, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidBetween(String value1, String value2) {
            addCriterion("Guid between", value1, value2, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidNotBetween(String value1, String value2) {
            addCriterion("Guid not between", value1, value2, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerIsNull() {
            addCriterion("GuidExamAnswer is null");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerIsNotNull() {
            addCriterion("GuidExamAnswer is not null");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerEqualTo(String value) {
            addCriterion("GuidExamAnswer =", value, "guidexamanswer");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerNotEqualTo(String value) {
            addCriterion("GuidExamAnswer <>", value, "guidexamanswer");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerGreaterThan(String value) {
            addCriterion("GuidExamAnswer >", value, "guidexamanswer");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerGreaterThanOrEqualTo(String value) {
            addCriterion("GuidExamAnswer >=", value, "guidexamanswer");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerLessThan(String value) {
            addCriterion("GuidExamAnswer <", value, "guidexamanswer");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerLessThanOrEqualTo(String value) {
            addCriterion("GuidExamAnswer <=", value, "guidexamanswer");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerLike(String value) {
            addCriterion("GuidExamAnswer like", value, "guidexamanswer");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerNotLike(String value) {
            addCriterion("GuidExamAnswer not like", value, "guidexamanswer");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerIn(List<String> values) {
            addCriterion("GuidExamAnswer in", values, "guidexamanswer");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerNotIn(List<String> values) {
            addCriterion("GuidExamAnswer not in", values, "guidexamanswer");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerBetween(String value1, String value2) {
            addCriterion("GuidExamAnswer between", value1, value2, "guidexamanswer");
            return (Criteria) this;
        }

        public Criteria andGuidexamanswerNotBetween(String value1, String value2) {
            addCriterion("GuidExamAnswer not between", value1, value2, "guidexamanswer");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerIsNull() {
            addCriterion("GuidQuestionsAnswer is null");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerIsNotNull() {
            addCriterion("GuidQuestionsAnswer is not null");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerEqualTo(String value) {
            addCriterion("GuidQuestionsAnswer =", value, "guidquestionsanswer");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerNotEqualTo(String value) {
            addCriterion("GuidQuestionsAnswer <>", value, "guidquestionsanswer");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerGreaterThan(String value) {
            addCriterion("GuidQuestionsAnswer >", value, "guidquestionsanswer");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerGreaterThanOrEqualTo(String value) {
            addCriterion("GuidQuestionsAnswer >=", value, "guidquestionsanswer");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerLessThan(String value) {
            addCriterion("GuidQuestionsAnswer <", value, "guidquestionsanswer");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerLessThanOrEqualTo(String value) {
            addCriterion("GuidQuestionsAnswer <=", value, "guidquestionsanswer");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerLike(String value) {
            addCriterion("GuidQuestionsAnswer like", value, "guidquestionsanswer");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerNotLike(String value) {
            addCriterion("GuidQuestionsAnswer not like", value, "guidquestionsanswer");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerIn(List<String> values) {
            addCriterion("GuidQuestionsAnswer in", values, "guidquestionsanswer");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerNotIn(List<String> values) {
            addCriterion("GuidQuestionsAnswer not in", values, "guidquestionsanswer");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerBetween(String value1, String value2) {
            addCriterion("GuidQuestionsAnswer between", value1, value2, "guidquestionsanswer");
            return (Criteria) this;
        }

        public Criteria andGuidquestionsanswerNotBetween(String value1, String value2) {
            addCriterion("GuidQuestionsAnswer not between", value1, value2, "guidquestionsanswer");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("Create_Time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("Create_Time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("Create_Time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("Create_Time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("Create_Time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Create_Time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("Create_Time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("Create_Time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("Create_Time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("Create_Time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("Create_Time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("Create_Time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_answer_result
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_answer_result
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}