package com.sfsctech.mybatis.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TSysAccountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TSysAccountExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andGuidIsNull() {
            addCriterion("Guid is null");
            return (Criteria) this;
        }

        public Criteria andGuidIsNotNull() {
            addCriterion("Guid is not null");
            return (Criteria) this;
        }

        public Criteria andGuidEqualTo(Long value) {
            addCriterion("Guid =", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidNotEqualTo(Long value) {
            addCriterion("Guid <>", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidGreaterThan(Long value) {
            addCriterion("Guid >", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidGreaterThanOrEqualTo(Long value) {
            addCriterion("Guid >=", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidLessThan(Long value) {
            addCriterion("Guid <", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidLessThanOrEqualTo(Long value) {
            addCriterion("Guid <=", value, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidIn(List<Long> values) {
            addCriterion("Guid in", values, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidNotIn(List<Long> values) {
            addCriterion("Guid not in", values, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidBetween(Long value1, Long value2) {
            addCriterion("Guid between", value1, value2, "guid");
            return (Criteria) this;
        }

        public Criteria andGuidNotBetween(Long value1, Long value2) {
            addCriterion("Guid not between", value1, value2, "guid");
            return (Criteria) this;
        }

        public Criteria andUserguidIsNull() {
            addCriterion("UserGuid is null");
            return (Criteria) this;
        }

        public Criteria andUserguidIsNotNull() {
            addCriterion("UserGuid is not null");
            return (Criteria) this;
        }

        public Criteria andUserguidEqualTo(Long value) {
            addCriterion("UserGuid =", value, "userguid");
            return (Criteria) this;
        }

        public Criteria andUserguidNotEqualTo(Long value) {
            addCriterion("UserGuid <>", value, "userguid");
            return (Criteria) this;
        }

        public Criteria andUserguidGreaterThan(Long value) {
            addCriterion("UserGuid >", value, "userguid");
            return (Criteria) this;
        }

        public Criteria andUserguidGreaterThanOrEqualTo(Long value) {
            addCriterion("UserGuid >=", value, "userguid");
            return (Criteria) this;
        }

        public Criteria andUserguidLessThan(Long value) {
            addCriterion("UserGuid <", value, "userguid");
            return (Criteria) this;
        }

        public Criteria andUserguidLessThanOrEqualTo(Long value) {
            addCriterion("UserGuid <=", value, "userguid");
            return (Criteria) this;
        }

        public Criteria andUserguidIn(List<Long> values) {
            addCriterion("UserGuid in", values, "userguid");
            return (Criteria) this;
        }

        public Criteria andUserguidNotIn(List<Long> values) {
            addCriterion("UserGuid not in", values, "userguid");
            return (Criteria) this;
        }

        public Criteria andUserguidBetween(Long value1, Long value2) {
            addCriterion("UserGuid between", value1, value2, "userguid");
            return (Criteria) this;
        }

        public Criteria andUserguidNotBetween(Long value1, Long value2) {
            addCriterion("UserGuid not between", value1, value2, "userguid");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("Account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("Account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(String value) {
            addCriterion("Account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(String value) {
            addCriterion("Account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(String value) {
            addCriterion("Account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(String value) {
            addCriterion("Account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(String value) {
            addCriterion("Account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(String value) {
            addCriterion("Account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLike(String value) {
            addCriterion("Account like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotLike(String value) {
            addCriterion("Account not like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<String> values) {
            addCriterion("Account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<String> values) {
            addCriterion("Account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(String value1, String value2) {
            addCriterion("Account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(String value1, String value2) {
            addCriterion("Account not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("Password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("Password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("Password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("Password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("Password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("Password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("Password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("Password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("Password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("Password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("Password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("Password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("Password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("Password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andInitpasswordIsNull() {
            addCriterion("InitPassword is null");
            return (Criteria) this;
        }

        public Criteria andInitpasswordIsNotNull() {
            addCriterion("InitPassword is not null");
            return (Criteria) this;
        }

        public Criteria andInitpasswordEqualTo(String value) {
            addCriterion("InitPassword =", value, "initpassword");
            return (Criteria) this;
        }

        public Criteria andInitpasswordNotEqualTo(String value) {
            addCriterion("InitPassword <>", value, "initpassword");
            return (Criteria) this;
        }

        public Criteria andInitpasswordGreaterThan(String value) {
            addCriterion("InitPassword >", value, "initpassword");
            return (Criteria) this;
        }

        public Criteria andInitpasswordGreaterThanOrEqualTo(String value) {
            addCriterion("InitPassword >=", value, "initpassword");
            return (Criteria) this;
        }

        public Criteria andInitpasswordLessThan(String value) {
            addCriterion("InitPassword <", value, "initpassword");
            return (Criteria) this;
        }

        public Criteria andInitpasswordLessThanOrEqualTo(String value) {
            addCriterion("InitPassword <=", value, "initpassword");
            return (Criteria) this;
        }

        public Criteria andInitpasswordLike(String value) {
            addCriterion("InitPassword like", value, "initpassword");
            return (Criteria) this;
        }

        public Criteria andInitpasswordNotLike(String value) {
            addCriterion("InitPassword not like", value, "initpassword");
            return (Criteria) this;
        }

        public Criteria andInitpasswordIn(List<String> values) {
            addCriterion("InitPassword in", values, "initpassword");
            return (Criteria) this;
        }

        public Criteria andInitpasswordNotIn(List<String> values) {
            addCriterion("InitPassword not in", values, "initpassword");
            return (Criteria) this;
        }

        public Criteria andInitpasswordBetween(String value1, String value2) {
            addCriterion("InitPassword between", value1, value2, "initpassword");
            return (Criteria) this;
        }

        public Criteria andInitpasswordNotBetween(String value1, String value2) {
            addCriterion("InitPassword not between", value1, value2, "initpassword");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("Sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("Sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Short value) {
            addCriterion("Sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Short value) {
            addCriterion("Sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Short value) {
            addCriterion("Sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Short value) {
            addCriterion("Sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Short value) {
            addCriterion("Sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Short value) {
            addCriterion("Sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Short> values) {
            addCriterion("Sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Short> values) {
            addCriterion("Sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Short value1, Short value2) {
            addCriterion("Sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Short value1, Short value2) {
            addCriterion("Sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNull() {
            addCriterion("Enabled is null");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNotNull() {
            addCriterion("Enabled is not null");
            return (Criteria) this;
        }

        public Criteria andEnabledEqualTo(Short value) {
            addCriterion("Enabled =", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotEqualTo(Short value) {
            addCriterion("Enabled <>", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThan(Short value) {
            addCriterion("Enabled >", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThanOrEqualTo(Short value) {
            addCriterion("Enabled >=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThan(Short value) {
            addCriterion("Enabled <", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThanOrEqualTo(Short value) {
            addCriterion("Enabled <=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledIn(List<Short> values) {
            addCriterion("Enabled in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotIn(List<Short> values) {
            addCriterion("Enabled not in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledBetween(Short value1, Short value2) {
            addCriterion("Enabled between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotBetween(Short value1, Short value2) {
            addCriterion("Enabled not between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andLockedIsNull() {
            addCriterion("Locked is null");
            return (Criteria) this;
        }

        public Criteria andLockedIsNotNull() {
            addCriterion("Locked is not null");
            return (Criteria) this;
        }

        public Criteria andLockedEqualTo(Short value) {
            addCriterion("Locked =", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedNotEqualTo(Short value) {
            addCriterion("Locked <>", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedGreaterThan(Short value) {
            addCriterion("Locked >", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedGreaterThanOrEqualTo(Short value) {
            addCriterion("Locked >=", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedLessThan(Short value) {
            addCriterion("Locked <", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedLessThanOrEqualTo(Short value) {
            addCriterion("Locked <=", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedIn(List<Short> values) {
            addCriterion("Locked in", values, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedNotIn(List<Short> values) {
            addCriterion("Locked not in", values, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedBetween(Short value1, Short value2) {
            addCriterion("Locked between", value1, value2, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedNotBetween(Short value1, Short value2) {
            addCriterion("Locked not between", value1, value2, "locked");
            return (Criteria) this;
        }

        public Criteria andLocktimeIsNull() {
            addCriterion("LockTime is null");
            return (Criteria) this;
        }

        public Criteria andLocktimeIsNotNull() {
            addCriterion("LockTime is not null");
            return (Criteria) this;
        }

        public Criteria andLocktimeEqualTo(Date value) {
            addCriterion("LockTime =", value, "locktime");
            return (Criteria) this;
        }

        public Criteria andLocktimeNotEqualTo(Date value) {
            addCriterion("LockTime <>", value, "locktime");
            return (Criteria) this;
        }

        public Criteria andLocktimeGreaterThan(Date value) {
            addCriterion("LockTime >", value, "locktime");
            return (Criteria) this;
        }

        public Criteria andLocktimeGreaterThanOrEqualTo(Date value) {
            addCriterion("LockTime >=", value, "locktime");
            return (Criteria) this;
        }

        public Criteria andLocktimeLessThan(Date value) {
            addCriterion("LockTime <", value, "locktime");
            return (Criteria) this;
        }

        public Criteria andLocktimeLessThanOrEqualTo(Date value) {
            addCriterion("LockTime <=", value, "locktime");
            return (Criteria) this;
        }

        public Criteria andLocktimeIn(List<Date> values) {
            addCriterion("LockTime in", values, "locktime");
            return (Criteria) this;
        }

        public Criteria andLocktimeNotIn(List<Date> values) {
            addCriterion("LockTime not in", values, "locktime");
            return (Criteria) this;
        }

        public Criteria andLocktimeBetween(Date value1, Date value2) {
            addCriterion("LockTime between", value1, value2, "locktime");
            return (Criteria) this;
        }

        public Criteria andLocktimeNotBetween(Date value1, Date value2) {
            addCriterion("LockTime not between", value1, value2, "locktime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("Status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("Status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("Status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("Status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("Status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("Status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("Status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("Status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("Status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("Status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("Status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("Status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("Creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("Creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(Long value) {
            addCriterion("Creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(Long value) {
            addCriterion("Creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(Long value) {
            addCriterion("Creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(Long value) {
            addCriterion("Creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(Long value) {
            addCriterion("Creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(Long value) {
            addCriterion("Creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<Long> values) {
            addCriterion("Creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<Long> values) {
            addCriterion("Creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(Long value1, Long value2) {
            addCriterion("Creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(Long value1, Long value2) {
            addCriterion("Creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("CreateTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("CreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("CreateTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("CreateTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("CreateTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CreateTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("CreateTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("CreateTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("CreateTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("CreateTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("CreateTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("CreateTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNull() {
            addCriterion("Updater is null");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNotNull() {
            addCriterion("Updater is not null");
            return (Criteria) this;
        }

        public Criteria andUpdaterEqualTo(Long value) {
            addCriterion("Updater =", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotEqualTo(Long value) {
            addCriterion("Updater <>", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThan(Long value) {
            addCriterion("Updater >", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThanOrEqualTo(Long value) {
            addCriterion("Updater >=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThan(Long value) {
            addCriterion("Updater <", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThanOrEqualTo(Long value) {
            addCriterion("Updater <=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterIn(List<Long> values) {
            addCriterion("Updater in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotIn(List<Long> values) {
            addCriterion("Updater not in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterBetween(Long value1, Long value2) {
            addCriterion("Updater between", value1, value2, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotBetween(Long value1, Long value2) {
            addCriterion("Updater not between", value1, value2, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("UpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("UpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("UpdateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("UpdateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("UpdateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UpdateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("UpdateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("UpdateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("UpdateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("UpdateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("UpdateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("UpdateTime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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