package com.evisible.os.controlcenter.system.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserExample() {
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

        public Criteria andSDicDateIdIsNull() {
            addCriterion("s_dic_date_id is null");
            return (Criteria) this;
        }

        public Criteria andSDicDateIdIsNotNull() {
            addCriterion("s_dic_date_id is not null");
            return (Criteria) this;
        }

        public Criteria andSDicDateIdEqualTo(String value) {
            addCriterion("s_dic_date_id =", value, "sDicDateId");
            return (Criteria) this;
        }

        public Criteria andSDicDateIdNotEqualTo(String value) {
            addCriterion("s_dic_date_id <>", value, "sDicDateId");
            return (Criteria) this;
        }

        public Criteria andSDicDateIdGreaterThan(String value) {
            addCriterion("s_dic_date_id >", value, "sDicDateId");
            return (Criteria) this;
        }

        public Criteria andSDicDateIdGreaterThanOrEqualTo(String value) {
            addCriterion("s_dic_date_id >=", value, "sDicDateId");
            return (Criteria) this;
        }

        public Criteria andSDicDateIdLessThan(String value) {
            addCriterion("s_dic_date_id <", value, "sDicDateId");
            return (Criteria) this;
        }

        public Criteria andSDicDateIdLessThanOrEqualTo(String value) {
            addCriterion("s_dic_date_id <=", value, "sDicDateId");
            return (Criteria) this;
        }

        public Criteria andSDicDateIdLike(String value) {
            addCriterion("s_dic_date_id like", value, "sDicDateId");
            return (Criteria) this;
        }

        public Criteria andSDicDateIdNotLike(String value) {
            addCriterion("s_dic_date_id not like", value, "sDicDateId");
            return (Criteria) this;
        }

        public Criteria andSDicDateIdIn(List<String> values) {
            addCriterion("s_dic_date_id in", values, "sDicDateId");
            return (Criteria) this;
        }

        public Criteria andSDicDateIdNotIn(List<String> values) {
            addCriterion("s_dic_date_id not in", values, "sDicDateId");
            return (Criteria) this;
        }

        public Criteria andSDicDateIdBetween(String value1, String value2) {
            addCriterion("s_dic_date_id between", value1, value2, "sDicDateId");
            return (Criteria) this;
        }

        public Criteria andSDicDateIdNotBetween(String value1, String value2) {
            addCriterion("s_dic_date_id not between", value1, value2, "sDicDateId");
            return (Criteria) this;
        }

        public Criteria andUNameIsNull() {
            addCriterion("u_name is null");
            return (Criteria) this;
        }

        public Criteria andUNameIsNotNull() {
            addCriterion("u_name is not null");
            return (Criteria) this;
        }

        public Criteria andUNameEqualTo(String value) {
            addCriterion("u_name =", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameNotEqualTo(String value) {
            addCriterion("u_name <>", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameGreaterThan(String value) {
            addCriterion("u_name >", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameGreaterThanOrEqualTo(String value) {
            addCriterion("u_name >=", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameLessThan(String value) {
            addCriterion("u_name <", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameLessThanOrEqualTo(String value) {
            addCriterion("u_name <=", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameLike(String value) {
            addCriterion("u_name like", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameNotLike(String value) {
            addCriterion("u_name not like", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameIn(List<String> values) {
            addCriterion("u_name in", values, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameNotIn(List<String> values) {
            addCriterion("u_name not in", values, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameBetween(String value1, String value2) {
            addCriterion("u_name between", value1, value2, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameNotBetween(String value1, String value2) {
            addCriterion("u_name not between", value1, value2, "uName");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(String value) {
            addCriterion("account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(String value) {
            addCriterion("account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(String value) {
            addCriterion("account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(String value) {
            addCriterion("account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(String value) {
            addCriterion("account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(String value) {
            addCriterion("account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLike(String value) {
            addCriterion("account like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotLike(String value) {
            addCriterion("account not like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<String> values) {
            addCriterion("account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<String> values) {
            addCriterion("account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(String value1, String value2) {
            addCriterion("account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(String value1, String value2) {
            addCriterion("account not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andPasIsNull() {
            addCriterion("pas is null");
            return (Criteria) this;
        }

        public Criteria andPasIsNotNull() {
            addCriterion("pas is not null");
            return (Criteria) this;
        }

        public Criteria andPasEqualTo(String value) {
            addCriterion("pas =", value, "pas");
            return (Criteria) this;
        }

        public Criteria andPasNotEqualTo(String value) {
            addCriterion("pas <>", value, "pas");
            return (Criteria) this;
        }

        public Criteria andPasGreaterThan(String value) {
            addCriterion("pas >", value, "pas");
            return (Criteria) this;
        }

        public Criteria andPasGreaterThanOrEqualTo(String value) {
            addCriterion("pas >=", value, "pas");
            return (Criteria) this;
        }

        public Criteria andPasLessThan(String value) {
            addCriterion("pas <", value, "pas");
            return (Criteria) this;
        }

        public Criteria andPasLessThanOrEqualTo(String value) {
            addCriterion("pas <=", value, "pas");
            return (Criteria) this;
        }

        public Criteria andPasLike(String value) {
            addCriterion("pas like", value, "pas");
            return (Criteria) this;
        }

        public Criteria andPasNotLike(String value) {
            addCriterion("pas not like", value, "pas");
            return (Criteria) this;
        }

        public Criteria andPasIn(List<String> values) {
            addCriterion("pas in", values, "pas");
            return (Criteria) this;
        }

        public Criteria andPasNotIn(List<String> values) {
            addCriterion("pas not in", values, "pas");
            return (Criteria) this;
        }

        public Criteria andPasBetween(String value1, String value2) {
            addCriterion("pas between", value1, value2, "pas");
            return (Criteria) this;
        }

        public Criteria andPasNotBetween(String value1, String value2) {
            addCriterion("pas not between", value1, value2, "pas");
            return (Criteria) this;
        }

        public Criteria andSaltIsNull() {
            addCriterion("salt is null");
            return (Criteria) this;
        }

        public Criteria andSaltIsNotNull() {
            addCriterion("salt is not null");
            return (Criteria) this;
        }

        public Criteria andSaltEqualTo(String value) {
            addCriterion("salt =", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotEqualTo(String value) {
            addCriterion("salt <>", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltGreaterThan(String value) {
            addCriterion("salt >", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltGreaterThanOrEqualTo(String value) {
            addCriterion("salt >=", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLessThan(String value) {
            addCriterion("salt <", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLessThanOrEqualTo(String value) {
            addCriterion("salt <=", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLike(String value) {
            addCriterion("salt like", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotLike(String value) {
            addCriterion("salt not like", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltIn(List<String> values) {
            addCriterion("salt in", values, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotIn(List<String> values) {
            addCriterion("salt not in", values, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltBetween(String value1, String value2) {
            addCriterion("salt between", value1, value2, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotBetween(String value1, String value2) {
            addCriterion("salt not between", value1, value2, "salt");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andWecharNoIsNull() {
            addCriterion("wechar_no is null");
            return (Criteria) this;
        }

        public Criteria andWecharNoIsNotNull() {
            addCriterion("wechar_no is not null");
            return (Criteria) this;
        }

        public Criteria andWecharNoEqualTo(String value) {
            addCriterion("wechar_no =", value, "wecharNo");
            return (Criteria) this;
        }

        public Criteria andWecharNoNotEqualTo(String value) {
            addCriterion("wechar_no <>", value, "wecharNo");
            return (Criteria) this;
        }

        public Criteria andWecharNoGreaterThan(String value) {
            addCriterion("wechar_no >", value, "wecharNo");
            return (Criteria) this;
        }

        public Criteria andWecharNoGreaterThanOrEqualTo(String value) {
            addCriterion("wechar_no >=", value, "wecharNo");
            return (Criteria) this;
        }

        public Criteria andWecharNoLessThan(String value) {
            addCriterion("wechar_no <", value, "wecharNo");
            return (Criteria) this;
        }

        public Criteria andWecharNoLessThanOrEqualTo(String value) {
            addCriterion("wechar_no <=", value, "wecharNo");
            return (Criteria) this;
        }

        public Criteria andWecharNoLike(String value) {
            addCriterion("wechar_no like", value, "wecharNo");
            return (Criteria) this;
        }

        public Criteria andWecharNoNotLike(String value) {
            addCriterion("wechar_no not like", value, "wecharNo");
            return (Criteria) this;
        }

        public Criteria andWecharNoIn(List<String> values) {
            addCriterion("wechar_no in", values, "wecharNo");
            return (Criteria) this;
        }

        public Criteria andWecharNoNotIn(List<String> values) {
            addCriterion("wechar_no not in", values, "wecharNo");
            return (Criteria) this;
        }

        public Criteria andWecharNoBetween(String value1, String value2) {
            addCriterion("wechar_no between", value1, value2, "wecharNo");
            return (Criteria) this;
        }

        public Criteria andWecharNoNotBetween(String value1, String value2) {
            addCriterion("wechar_no not between", value1, value2, "wecharNo");
            return (Criteria) this;
        }

        public Criteria andQqNoIsNull() {
            addCriterion("qq_no is null");
            return (Criteria) this;
        }

        public Criteria andQqNoIsNotNull() {
            addCriterion("qq_no is not null");
            return (Criteria) this;
        }

        public Criteria andQqNoEqualTo(String value) {
            addCriterion("qq_no =", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoNotEqualTo(String value) {
            addCriterion("qq_no <>", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoGreaterThan(String value) {
            addCriterion("qq_no >", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoGreaterThanOrEqualTo(String value) {
            addCriterion("qq_no >=", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoLessThan(String value) {
            addCriterion("qq_no <", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoLessThanOrEqualTo(String value) {
            addCriterion("qq_no <=", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoLike(String value) {
            addCriterion("qq_no like", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoNotLike(String value) {
            addCriterion("qq_no not like", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoIn(List<String> values) {
            addCriterion("qq_no in", values, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoNotIn(List<String> values) {
            addCriterion("qq_no not in", values, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoBetween(String value1, String value2) {
            addCriterion("qq_no between", value1, value2, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoNotBetween(String value1, String value2) {
            addCriterion("qq_no not between", value1, value2, "qqNo");
            return (Criteria) this;
        }

        public Criteria andSignIsNull() {
            addCriterion("sign is null");
            return (Criteria) this;
        }

        public Criteria andSignIsNotNull() {
            addCriterion("sign is not null");
            return (Criteria) this;
        }

        public Criteria andSignEqualTo(Integer value) {
            addCriterion("sign =", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotEqualTo(Integer value) {
            addCriterion("sign <>", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThan(Integer value) {
            addCriterion("sign >", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThanOrEqualTo(Integer value) {
            addCriterion("sign >=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThan(Integer value) {
            addCriterion("sign <", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThanOrEqualTo(Integer value) {
            addCriterion("sign <=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignIn(List<Integer> values) {
            addCriterion("sign in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotIn(List<Integer> values) {
            addCriterion("sign not in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignBetween(Integer value1, Integer value2) {
            addCriterion("sign between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotBetween(Integer value1, Integer value2) {
            addCriterion("sign not between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andIntegralIsNull() {
            addCriterion("integral is null");
            return (Criteria) this;
        }

        public Criteria andIntegralIsNotNull() {
            addCriterion("integral is not null");
            return (Criteria) this;
        }

        public Criteria andIntegralEqualTo(Float value) {
            addCriterion("integral =", value, "integral");
            return (Criteria) this;
        }

        public Criteria andIntegralNotEqualTo(Float value) {
            addCriterion("integral <>", value, "integral");
            return (Criteria) this;
        }

        public Criteria andIntegralGreaterThan(Float value) {
            addCriterion("integral >", value, "integral");
            return (Criteria) this;
        }

        public Criteria andIntegralGreaterThanOrEqualTo(Float value) {
            addCriterion("integral >=", value, "integral");
            return (Criteria) this;
        }

        public Criteria andIntegralLessThan(Float value) {
            addCriterion("integral <", value, "integral");
            return (Criteria) this;
        }

        public Criteria andIntegralLessThanOrEqualTo(Float value) {
            addCriterion("integral <=", value, "integral");
            return (Criteria) this;
        }

        public Criteria andIntegralIn(List<Float> values) {
            addCriterion("integral in", values, "integral");
            return (Criteria) this;
        }

        public Criteria andIntegralNotIn(List<Float> values) {
            addCriterion("integral not in", values, "integral");
            return (Criteria) this;
        }

        public Criteria andIntegralBetween(Float value1, Float value2) {
            addCriterion("integral between", value1, value2, "integral");
            return (Criteria) this;
        }

        public Criteria andIntegralNotBetween(Float value1, Float value2) {
            addCriterion("integral not between", value1, value2, "integral");
            return (Criteria) this;
        }

        public Criteria andIntegralSIsNull() {
            addCriterion("integral_s is null");
            return (Criteria) this;
        }

        public Criteria andIntegralSIsNotNull() {
            addCriterion("integral_s is not null");
            return (Criteria) this;
        }

        public Criteria andIntegralSEqualTo(Integer value) {
            addCriterion("integral_s =", value, "integralS");
            return (Criteria) this;
        }

        public Criteria andIntegralSNotEqualTo(Integer value) {
            addCriterion("integral_s <>", value, "integralS");
            return (Criteria) this;
        }

        public Criteria andIntegralSGreaterThan(Integer value) {
            addCriterion("integral_s >", value, "integralS");
            return (Criteria) this;
        }

        public Criteria andIntegralSGreaterThanOrEqualTo(Integer value) {
            addCriterion("integral_s >=", value, "integralS");
            return (Criteria) this;
        }

        public Criteria andIntegralSLessThan(Integer value) {
            addCriterion("integral_s <", value, "integralS");
            return (Criteria) this;
        }

        public Criteria andIntegralSLessThanOrEqualTo(Integer value) {
            addCriterion("integral_s <=", value, "integralS");
            return (Criteria) this;
        }

        public Criteria andIntegralSIn(List<Integer> values) {
            addCriterion("integral_s in", values, "integralS");
            return (Criteria) this;
        }

        public Criteria andIntegralSNotIn(List<Integer> values) {
            addCriterion("integral_s not in", values, "integralS");
            return (Criteria) this;
        }

        public Criteria andIntegralSBetween(Integer value1, Integer value2) {
            addCriterion("integral_s between", value1, value2, "integralS");
            return (Criteria) this;
        }

        public Criteria andIntegralSNotBetween(Integer value1, Integer value2) {
            addCriterion("integral_s not between", value1, value2, "integralS");
            return (Criteria) this;
        }

        public Criteria andIntegralEIsNull() {
            addCriterion("integral_e is null");
            return (Criteria) this;
        }

        public Criteria andIntegralEIsNotNull() {
            addCriterion("integral_e is not null");
            return (Criteria) this;
        }

        public Criteria andIntegralEEqualTo(Integer value) {
            addCriterion("integral_e =", value, "integralE");
            return (Criteria) this;
        }

        public Criteria andIntegralENotEqualTo(Integer value) {
            addCriterion("integral_e <>", value, "integralE");
            return (Criteria) this;
        }

        public Criteria andIntegralEGreaterThan(Integer value) {
            addCriterion("integral_e >", value, "integralE");
            return (Criteria) this;
        }

        public Criteria andIntegralEGreaterThanOrEqualTo(Integer value) {
            addCriterion("integral_e >=", value, "integralE");
            return (Criteria) this;
        }

        public Criteria andIntegralELessThan(Integer value) {
            addCriterion("integral_e <", value, "integralE");
            return (Criteria) this;
        }

        public Criteria andIntegralELessThanOrEqualTo(Integer value) {
            addCriterion("integral_e <=", value, "integralE");
            return (Criteria) this;
        }

        public Criteria andIntegralEIn(List<Integer> values) {
            addCriterion("integral_e in", values, "integralE");
            return (Criteria) this;
        }

        public Criteria andIntegralENotIn(List<Integer> values) {
            addCriterion("integral_e not in", values, "integralE");
            return (Criteria) this;
        }

        public Criteria andIntegralEBetween(Integer value1, Integer value2) {
            addCriterion("integral_e between", value1, value2, "integralE");
            return (Criteria) this;
        }

        public Criteria andIntegralENotBetween(Integer value1, Integer value2) {
            addCriterion("integral_e not between", value1, value2, "integralE");
            return (Criteria) this;
        }

        public Criteria andLockedIsNull() {
            addCriterion("locked is null");
            return (Criteria) this;
        }

        public Criteria andLockedIsNotNull() {
            addCriterion("locked is not null");
            return (Criteria) this;
        }

        public Criteria andLockedEqualTo(Integer value) {
            addCriterion("locked =", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedNotEqualTo(Integer value) {
            addCriterion("locked <>", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedGreaterThan(Integer value) {
            addCriterion("locked >", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedGreaterThanOrEqualTo(Integer value) {
            addCriterion("locked >=", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedLessThan(Integer value) {
            addCriterion("locked <", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedLessThanOrEqualTo(Integer value) {
            addCriterion("locked <=", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedIn(List<Integer> values) {
            addCriterion("locked in", values, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedNotIn(List<Integer> values) {
            addCriterion("locked not in", values, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedBetween(Integer value1, Integer value2) {
            addCriterion("locked between", value1, value2, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedNotBetween(Integer value1, Integer value2) {
            addCriterion("locked not between", value1, value2, "locked");
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

        public Criteria andMedalNumberIsNull() {
            addCriterion("medal_number is null");
            return (Criteria) this;
        }

        public Criteria andMedalNumberIsNotNull() {
            addCriterion("medal_number is not null");
            return (Criteria) this;
        }

        public Criteria andMedalNumberEqualTo(Integer value) {
            addCriterion("medal_number =", value, "medalNumber");
            return (Criteria) this;
        }

        public Criteria andMedalNumberNotEqualTo(Integer value) {
            addCriterion("medal_number <>", value, "medalNumber");
            return (Criteria) this;
        }

        public Criteria andMedalNumberGreaterThan(Integer value) {
            addCriterion("medal_number >", value, "medalNumber");
            return (Criteria) this;
        }

        public Criteria andMedalNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("medal_number >=", value, "medalNumber");
            return (Criteria) this;
        }

        public Criteria andMedalNumberLessThan(Integer value) {
            addCriterion("medal_number <", value, "medalNumber");
            return (Criteria) this;
        }

        public Criteria andMedalNumberLessThanOrEqualTo(Integer value) {
            addCriterion("medal_number <=", value, "medalNumber");
            return (Criteria) this;
        }

        public Criteria andMedalNumberIn(List<Integer> values) {
            addCriterion("medal_number in", values, "medalNumber");
            return (Criteria) this;
        }

        public Criteria andMedalNumberNotIn(List<Integer> values) {
            addCriterion("medal_number not in", values, "medalNumber");
            return (Criteria) this;
        }

        public Criteria andMedalNumberBetween(Integer value1, Integer value2) {
            addCriterion("medal_number between", value1, value2, "medalNumber");
            return (Criteria) this;
        }

        public Criteria andMedalNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("medal_number not between", value1, value2, "medalNumber");
            return (Criteria) this;
        }

        public Criteria andUDescribeIsNull() {
            addCriterion("u_describe is null");
            return (Criteria) this;
        }

        public Criteria andUDescribeIsNotNull() {
            addCriterion("u_describe is not null");
            return (Criteria) this;
        }

        public Criteria andUDescribeEqualTo(String value) {
            addCriterion("u_describe =", value, "uDescribe");
            return (Criteria) this;
        }

        public Criteria andUDescribeNotEqualTo(String value) {
            addCriterion("u_describe <>", value, "uDescribe");
            return (Criteria) this;
        }

        public Criteria andUDescribeGreaterThan(String value) {
            addCriterion("u_describe >", value, "uDescribe");
            return (Criteria) this;
        }

        public Criteria andUDescribeGreaterThanOrEqualTo(String value) {
            addCriterion("u_describe >=", value, "uDescribe");
            return (Criteria) this;
        }

        public Criteria andUDescribeLessThan(String value) {
            addCriterion("u_describe <", value, "uDescribe");
            return (Criteria) this;
        }

        public Criteria andUDescribeLessThanOrEqualTo(String value) {
            addCriterion("u_describe <=", value, "uDescribe");
            return (Criteria) this;
        }

        public Criteria andUDescribeLike(String value) {
            addCriterion("u_describe like", value, "uDescribe");
            return (Criteria) this;
        }

        public Criteria andUDescribeNotLike(String value) {
            addCriterion("u_describe not like", value, "uDescribe");
            return (Criteria) this;
        }

        public Criteria andUDescribeIn(List<String> values) {
            addCriterion("u_describe in", values, "uDescribe");
            return (Criteria) this;
        }

        public Criteria andUDescribeNotIn(List<String> values) {
            addCriterion("u_describe not in", values, "uDescribe");
            return (Criteria) this;
        }

        public Criteria andUDescribeBetween(String value1, String value2) {
            addCriterion("u_describe between", value1, value2, "uDescribe");
            return (Criteria) this;
        }

        public Criteria andUDescribeNotBetween(String value1, String value2) {
            addCriterion("u_describe not between", value1, value2, "uDescribe");
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