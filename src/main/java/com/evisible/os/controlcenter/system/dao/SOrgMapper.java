package com.evisible.os.controlcenter.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.evisible.os.controlcenter.system.entity.SOrg;
import com.evisible.os.controlcenter.system.entity.SOrgExample;

public interface SOrgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_org
     *
     * @mbggenerated Tue Aug 16 10:18:44 CST 2016
     */
    int countByExample(SOrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_org
     *
     * @mbggenerated Tue Aug 16 10:18:44 CST 2016
     */
    int deleteByExample(SOrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_org
     *
     * @mbggenerated Tue Aug 16 10:18:44 CST 2016
     */
    int deleteByPrimaryKey(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_org
     *
     * @mbggenerated Tue Aug 16 10:18:44 CST 2016
     */
    int insert(SOrg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_org
     *
     * @mbggenerated Tue Aug 16 10:18:44 CST 2016
     */
    int insertSelective(SOrg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_org
     *
     * @mbggenerated Tue Aug 16 10:18:44 CST 2016
     */
    List<SOrg> selectByExample(SOrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_org
     *
     * @mbggenerated Tue Aug 16 10:18:44 CST 2016
     */
    SOrg selectByPrimaryKey(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_org
     *
     * @mbggenerated Tue Aug 16 10:18:44 CST 2016
     */
    int updateByExampleSelective(@Param("record") SOrg record, @Param("example") SOrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_org
     *
     * @mbggenerated Tue Aug 16 10:18:44 CST 2016
     */
    int updateByExample(@Param("record") SOrg record, @Param("example") SOrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_org
     *
     * @mbggenerated Tue Aug 16 10:18:44 CST 2016
     */
    int updateByPrimaryKeySelective(SOrg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_org
     *
     * @mbggenerated Tue Aug 16 10:18:44 CST 2016
     */
    int updateByPrimaryKey(SOrg record);
}