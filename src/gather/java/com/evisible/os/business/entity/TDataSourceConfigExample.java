package com.evisible.os.business.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TDataSourceConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TDataSourceConfigExample() {
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

        public Criteria andUuidIsNull() {
            addCriterion("uuid is null");
            return (Criteria) this;
        }

        public Criteria andUuidIsNotNull() {
            addCriterion("uuid is not null");
            return (Criteria) this;
        }

        public Criteria andUuidEqualTo(String value) {
            addCriterion("uuid =", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotEqualTo(String value) {
            addCriterion("uuid <>", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThan(String value) {
            addCriterion("uuid >", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThanOrEqualTo(String value) {
            addCriterion("uuid >=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThan(String value) {
            addCriterion("uuid <", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThanOrEqualTo(String value) {
            addCriterion("uuid <=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLike(String value) {
            addCriterion("uuid like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotLike(String value) {
            addCriterion("uuid not like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidIn(List<String> values) {
            addCriterion("uuid in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotIn(List<String> values) {
            addCriterion("uuid not in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidBetween(String value1, String value2) {
            addCriterion("uuid between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotBetween(String value1, String value2) {
            addCriterion("uuid not between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andDsIdIsNull() {
            addCriterion("ds_id is null");
            return (Criteria) this;
        }

        public Criteria andDsIdIsNotNull() {
            addCriterion("ds_id is not null");
            return (Criteria) this;
        }

        public Criteria andDsIdEqualTo(String value) {
            addCriterion("ds_id =", value, "dsId");
            return (Criteria) this;
        }

        public Criteria andDsIdNotEqualTo(String value) {
            addCriterion("ds_id <>", value, "dsId");
            return (Criteria) this;
        }

        public Criteria andDsIdGreaterThan(String value) {
            addCriterion("ds_id >", value, "dsId");
            return (Criteria) this;
        }

        public Criteria andDsIdGreaterThanOrEqualTo(String value) {
            addCriterion("ds_id >=", value, "dsId");
            return (Criteria) this;
        }

        public Criteria andDsIdLessThan(String value) {
            addCriterion("ds_id <", value, "dsId");
            return (Criteria) this;
        }

        public Criteria andDsIdLessThanOrEqualTo(String value) {
            addCriterion("ds_id <=", value, "dsId");
            return (Criteria) this;
        }

        public Criteria andDsIdLike(String value) {
            addCriterion("ds_id like", value, "dsId");
            return (Criteria) this;
        }

        public Criteria andDsIdNotLike(String value) {
            addCriterion("ds_id not like", value, "dsId");
            return (Criteria) this;
        }

        public Criteria andDsIdIn(List<String> values) {
            addCriterion("ds_id in", values, "dsId");
            return (Criteria) this;
        }

        public Criteria andDsIdNotIn(List<String> values) {
            addCriterion("ds_id not in", values, "dsId");
            return (Criteria) this;
        }

        public Criteria andDsIdBetween(String value1, String value2) {
            addCriterion("ds_id between", value1, value2, "dsId");
            return (Criteria) this;
        }

        public Criteria andDsIdNotBetween(String value1, String value2) {
            addCriterion("ds_id not between", value1, value2, "dsId");
            return (Criteria) this;
        }

        public Criteria andDsNameIsNull() {
            addCriterion("ds_name is null");
            return (Criteria) this;
        }

        public Criteria andDsNameIsNotNull() {
            addCriterion("ds_name is not null");
            return (Criteria) this;
        }

        public Criteria andDsNameEqualTo(String value) {
            addCriterion("ds_name =", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameNotEqualTo(String value) {
            addCriterion("ds_name <>", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameGreaterThan(String value) {
            addCriterion("ds_name >", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameGreaterThanOrEqualTo(String value) {
            addCriterion("ds_name >=", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameLessThan(String value) {
            addCriterion("ds_name <", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameLessThanOrEqualTo(String value) {
            addCriterion("ds_name <=", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameLike(String value) {
            addCriterion("ds_name like", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameNotLike(String value) {
            addCriterion("ds_name not like", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameIn(List<String> values) {
            addCriterion("ds_name in", values, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameNotIn(List<String> values) {
            addCriterion("ds_name not in", values, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameBetween(String value1, String value2) {
            addCriterion("ds_name between", value1, value2, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameNotBetween(String value1, String value2) {
            addCriterion("ds_name not between", value1, value2, "dsName");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andDriverIsNull() {
            addCriterion("driver is null");
            return (Criteria) this;
        }

        public Criteria andDriverIsNotNull() {
            addCriterion("driver is not null");
            return (Criteria) this;
        }

        public Criteria andDriverEqualTo(String value) {
            addCriterion("driver =", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverNotEqualTo(String value) {
            addCriterion("driver <>", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverGreaterThan(String value) {
            addCriterion("driver >", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverGreaterThanOrEqualTo(String value) {
            addCriterion("driver >=", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverLessThan(String value) {
            addCriterion("driver <", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverLessThanOrEqualTo(String value) {
            addCriterion("driver <=", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverLike(String value) {
            addCriterion("driver like", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverNotLike(String value) {
            addCriterion("driver not like", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverIn(List<String> values) {
            addCriterion("driver in", values, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverNotIn(List<String> values) {
            addCriterion("driver not in", values, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverBetween(String value1, String value2) {
            addCriterion("driver between", value1, value2, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverNotBetween(String value1, String value2) {
            addCriterion("driver not between", value1, value2, "driver");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andDsUsernameIsNull() {
            addCriterion("ds_username is null");
            return (Criteria) this;
        }

        public Criteria andDsUsernameIsNotNull() {
            addCriterion("ds_username is not null");
            return (Criteria) this;
        }

        public Criteria andDsUsernameEqualTo(String value) {
            addCriterion("ds_username =", value, "dsUsername");
            return (Criteria) this;
        }

        public Criteria andDsUsernameNotEqualTo(String value) {
            addCriterion("ds_username <>", value, "dsUsername");
            return (Criteria) this;
        }

        public Criteria andDsUsernameGreaterThan(String value) {
            addCriterion("ds_username >", value, "dsUsername");
            return (Criteria) this;
        }

        public Criteria andDsUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("ds_username >=", value, "dsUsername");
            return (Criteria) this;
        }

        public Criteria andDsUsernameLessThan(String value) {
            addCriterion("ds_username <", value, "dsUsername");
            return (Criteria) this;
        }

        public Criteria andDsUsernameLessThanOrEqualTo(String value) {
            addCriterion("ds_username <=", value, "dsUsername");
            return (Criteria) this;
        }

        public Criteria andDsUsernameLike(String value) {
            addCriterion("ds_username like", value, "dsUsername");
            return (Criteria) this;
        }

        public Criteria andDsUsernameNotLike(String value) {
            addCriterion("ds_username not like", value, "dsUsername");
            return (Criteria) this;
        }

        public Criteria andDsUsernameIn(List<String> values) {
            addCriterion("ds_username in", values, "dsUsername");
            return (Criteria) this;
        }

        public Criteria andDsUsernameNotIn(List<String> values) {
            addCriterion("ds_username not in", values, "dsUsername");
            return (Criteria) this;
        }

        public Criteria andDsUsernameBetween(String value1, String value2) {
            addCriterion("ds_username between", value1, value2, "dsUsername");
            return (Criteria) this;
        }

        public Criteria andDsUsernameNotBetween(String value1, String value2) {
            addCriterion("ds_username not between", value1, value2, "dsUsername");
            return (Criteria) this;
        }

        public Criteria andDsPasswordIsNull() {
            addCriterion("ds_password is null");
            return (Criteria) this;
        }

        public Criteria andDsPasswordIsNotNull() {
            addCriterion("ds_password is not null");
            return (Criteria) this;
        }

        public Criteria andDsPasswordEqualTo(String value) {
            addCriterion("ds_password =", value, "dsPassword");
            return (Criteria) this;
        }

        public Criteria andDsPasswordNotEqualTo(String value) {
            addCriterion("ds_password <>", value, "dsPassword");
            return (Criteria) this;
        }

        public Criteria andDsPasswordGreaterThan(String value) {
            addCriterion("ds_password >", value, "dsPassword");
            return (Criteria) this;
        }

        public Criteria andDsPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("ds_password >=", value, "dsPassword");
            return (Criteria) this;
        }

        public Criteria andDsPasswordLessThan(String value) {
            addCriterion("ds_password <", value, "dsPassword");
            return (Criteria) this;
        }

        public Criteria andDsPasswordLessThanOrEqualTo(String value) {
            addCriterion("ds_password <=", value, "dsPassword");
            return (Criteria) this;
        }

        public Criteria andDsPasswordLike(String value) {
            addCriterion("ds_password like", value, "dsPassword");
            return (Criteria) this;
        }

        public Criteria andDsPasswordNotLike(String value) {
            addCriterion("ds_password not like", value, "dsPassword");
            return (Criteria) this;
        }

        public Criteria andDsPasswordIn(List<String> values) {
            addCriterion("ds_password in", values, "dsPassword");
            return (Criteria) this;
        }

        public Criteria andDsPasswordNotIn(List<String> values) {
            addCriterion("ds_password not in", values, "dsPassword");
            return (Criteria) this;
        }

        public Criteria andDsPasswordBetween(String value1, String value2) {
            addCriterion("ds_password between", value1, value2, "dsPassword");
            return (Criteria) this;
        }

        public Criteria andDsPasswordNotBetween(String value1, String value2) {
            addCriterion("ds_password not between", value1, value2, "dsPassword");
            return (Criteria) this;
        }

        public Criteria andServerAddIsNull() {
            addCriterion("server_add is null");
            return (Criteria) this;
        }

        public Criteria andServerAddIsNotNull() {
            addCriterion("server_add is not null");
            return (Criteria) this;
        }

        public Criteria andServerAddEqualTo(String value) {
            addCriterion("server_add =", value, "serverAdd");
            return (Criteria) this;
        }

        public Criteria andServerAddNotEqualTo(String value) {
            addCriterion("server_add <>", value, "serverAdd");
            return (Criteria) this;
        }

        public Criteria andServerAddGreaterThan(String value) {
            addCriterion("server_add >", value, "serverAdd");
            return (Criteria) this;
        }

        public Criteria andServerAddGreaterThanOrEqualTo(String value) {
            addCriterion("server_add >=", value, "serverAdd");
            return (Criteria) this;
        }

        public Criteria andServerAddLessThan(String value) {
            addCriterion("server_add <", value, "serverAdd");
            return (Criteria) this;
        }

        public Criteria andServerAddLessThanOrEqualTo(String value) {
            addCriterion("server_add <=", value, "serverAdd");
            return (Criteria) this;
        }

        public Criteria andServerAddLike(String value) {
            addCriterion("server_add like", value, "serverAdd");
            return (Criteria) this;
        }

        public Criteria andServerAddNotLike(String value) {
            addCriterion("server_add not like", value, "serverAdd");
            return (Criteria) this;
        }

        public Criteria andServerAddIn(List<String> values) {
            addCriterion("server_add in", values, "serverAdd");
            return (Criteria) this;
        }

        public Criteria andServerAddNotIn(List<String> values) {
            addCriterion("server_add not in", values, "serverAdd");
            return (Criteria) this;
        }

        public Criteria andServerAddBetween(String value1, String value2) {
            addCriterion("server_add between", value1, value2, "serverAdd");
            return (Criteria) this;
        }

        public Criteria andServerAddNotBetween(String value1, String value2) {
            addCriterion("server_add not between", value1, value2, "serverAdd");
            return (Criteria) this;
        }

        public Criteria andPortIsNull() {
            addCriterion("port is null");
            return (Criteria) this;
        }

        public Criteria andPortIsNotNull() {
            addCriterion("port is not null");
            return (Criteria) this;
        }

        public Criteria andPortEqualTo(Integer value) {
            addCriterion("port =", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotEqualTo(Integer value) {
            addCriterion("port <>", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThan(Integer value) {
            addCriterion("port >", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThanOrEqualTo(Integer value) {
            addCriterion("port >=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThan(Integer value) {
            addCriterion("port <", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThanOrEqualTo(Integer value) {
            addCriterion("port <=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortIn(List<Integer> values) {
            addCriterion("port in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotIn(List<Integer> values) {
            addCriterion("port not in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortBetween(Integer value1, Integer value2) {
            addCriterion("port between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotBetween(Integer value1, Integer value2) {
            addCriterion("port not between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNull() {
            addCriterion("creator_id is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNotNull() {
            addCriterion("creator_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdEqualTo(String value) {
            addCriterion("creator_id =", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotEqualTo(String value) {
            addCriterion("creator_id <>", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThan(String value) {
            addCriterion("creator_id >", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThanOrEqualTo(String value) {
            addCriterion("creator_id >=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThan(String value) {
            addCriterion("creator_id <", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThanOrEqualTo(String value) {
            addCriterion("creator_id <=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLike(String value) {
            addCriterion("creator_id like", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotLike(String value) {
            addCriterion("creator_id not like", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIn(List<String> values) {
            addCriterion("creator_id in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotIn(List<String> values) {
            addCriterion("creator_id not in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdBetween(String value1, String value2) {
            addCriterion("creator_id between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotBetween(String value1, String value2) {
            addCriterion("creator_id not between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNull() {
            addCriterion("createdate is null");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNotNull() {
            addCriterion("createdate is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedateEqualTo(Date value) {
            addCriterion("createdate =", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotEqualTo(Date value) {
            addCriterion("createdate <>", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThan(Date value) {
            addCriterion("createdate >", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("createdate >=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThan(Date value) {
            addCriterion("createdate <", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThanOrEqualTo(Date value) {
            addCriterion("createdate <=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIn(List<Date> values) {
            addCriterion("createdate in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotIn(List<Date> values) {
            addCriterion("createdate not in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateBetween(Date value1, Date value2) {
            addCriterion("createdate between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotBetween(Date value1, Date value2) {
            addCriterion("createdate not between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andUpdatorIsNull() {
            addCriterion("updator is null");
            return (Criteria) this;
        }

        public Criteria andUpdatorIsNotNull() {
            addCriterion("updator is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatorEqualTo(String value) {
            addCriterion("updator =", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotEqualTo(String value) {
            addCriterion("updator <>", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorGreaterThan(String value) {
            addCriterion("updator >", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorGreaterThanOrEqualTo(String value) {
            addCriterion("updator >=", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLessThan(String value) {
            addCriterion("updator <", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLessThanOrEqualTo(String value) {
            addCriterion("updator <=", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLike(String value) {
            addCriterion("updator like", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotLike(String value) {
            addCriterion("updator not like", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorIn(List<String> values) {
            addCriterion("updator in", values, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotIn(List<String> values) {
            addCriterion("updator not in", values, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorBetween(String value1, String value2) {
            addCriterion("updator between", value1, value2, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotBetween(String value1, String value2) {
            addCriterion("updator not between", value1, value2, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatedateIsNull() {
            addCriterion("updatedate is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedateIsNotNull() {
            addCriterion("updatedate is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedateEqualTo(Date value) {
            addCriterion("updatedate =", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateNotEqualTo(Date value) {
            addCriterion("updatedate <>", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateGreaterThan(Date value) {
            addCriterion("updatedate >", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("updatedate >=", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateLessThan(Date value) {
            addCriterion("updatedate <", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateLessThanOrEqualTo(Date value) {
            addCriterion("updatedate <=", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateIn(List<Date> values) {
            addCriterion("updatedate in", values, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateNotIn(List<Date> values) {
            addCriterion("updatedate not in", values, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateBetween(Date value1, Date value2) {
            addCriterion("updatedate between", value1, value2, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateNotBetween(Date value1, Date value2) {
            addCriterion("updatedate not between", value1, value2, "updatedate");
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