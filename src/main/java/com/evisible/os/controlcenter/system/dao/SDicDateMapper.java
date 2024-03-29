package com.evisible.os.controlcenter.system.dao;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.evisible.os.controlcenter.system.entity.SDicDate;
import com.evisible.os.controlcenter.system.entity.SDicDateExample;

public interface SDicDateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_dic_date
     *
     * @mbggenerated Mon Apr 25 16:38:45 CST 2016
     */
    int countByExample(SDicDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_dic_date
     *
     * @mbggenerated Mon Apr 25 16:38:45 CST 2016
     */
    int deleteByExample(SDicDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_dic_date
     *
     * @mbggenerated Mon Apr 25 16:38:45 CST 2016
     */
    int deleteByPrimaryKey(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_dic_date
     *
     * @mbggenerated Mon Apr 25 16:38:45 CST 2016
     */
    int insert(SDicDate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_dic_date
     *
     * @mbggenerated Mon Apr 25 16:38:45 CST 2016
     */
    int insertSelective(SDicDate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_dic_date
     *
     * @mbggenerated Mon Apr 25 16:38:45 CST 2016
     */
    List<SDicDate> selectByExample(SDicDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_dic_date
     *
     * @mbggenerated Mon Apr 25 16:38:45 CST 2016
     */
    SDicDate selectByPrimaryKey(String uuid);
    
    
    /**
     * 扩展方法
     * @param map
     * @return
     */
    List<SDicDate>  selectByExampleTypeCodeOrName(Map<String,Object> map);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_dic_date
     *
     * @mbggenerated Mon Apr 25 16:38:45 CST 2016
     */
    int updateByExampleSelective(@Param("record") SDicDate record, @Param("example") SDicDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_dic_date
     *
     * @mbggenerated Mon Apr 25 16:38:45 CST 2016
     */
    int updateByExample(@Param("record") SDicDate record, @Param("example") SDicDateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_dic_date
     *
     * @mbggenerated Mon Apr 25 16:38:45 CST 2016
     */
    int updateByPrimaryKeySelective(SDicDate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_dic_date
     *
     * @mbggenerated Mon Apr 25 16:38:45 CST 2016
     */
    int updateByPrimaryKey(SDicDate record);
}