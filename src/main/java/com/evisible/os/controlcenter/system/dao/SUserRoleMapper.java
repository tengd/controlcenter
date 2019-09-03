package com.evisible.os.controlcenter.system.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.evisible.os.controlcenter.system.entity.SUserRole;
import com.evisible.os.controlcenter.system.entity.SUserRoleExample;

public interface SUserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_role
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    int countByExample(SUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_role
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    int deleteByExample(SUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_role
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    int deleteByPrimaryKey(String uuid);
    
    
    /**
     * 扩展批量删除
     * This method was generated by MyBatis TengDong.
     * This method corresponds to the database table s_user_role
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    int deleteByPrimaryKeyKZ(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_role
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    int insert(SUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_role
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    int insertSelective(SUserRole record);
    
    
    /**
     * This is batch insert
     * @author TengDong
     * @param userRoleList
     * @return insert number
     */
    int addUserRoles(List<SUserRole>  userRoleList);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_role
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    List<SUserRole> selectByExample(SUserRoleExample example);
    
  
    /**
     * This method was generated by TengDong
     * This method corresponds to the database table s_user_role
     *
     *  @return 查询用户与角色关系
     */
    List<SUserRole> selectByExampleKZ(SUserRoleExample example);
    
    /**
     * @param example 统计
     * @return int
     */
    int countSelectByExampleKZ(SUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_role
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    SUserRole selectByPrimaryKey(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_role
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    int updateByExampleSelective(@Param("record") SUserRole record, @Param("example") SUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_role
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    int updateByExample(@Param("record") SUserRole record, @Param("example") SUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_role
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    int updateByPrimaryKeySelective(SUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_role
     *
     * @mbggenerated Tue Mar 29 14:38:29 CST 2016
     */
    int updateByPrimaryKey(SUserRole record);
}