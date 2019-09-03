package com.evisible.os.controlcenter.system.dao;

import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.entity.UserExample;
import com.evisible.os.controlcenter.system.entity.UserWithBLOBs;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(UserWithBLOBs record);

    int insertSelective(UserWithBLOBs record);

    List<UserWithBLOBs> selectByExampleWithBLOBs(UserExample example);

    List<User> selectByExample(UserExample example);
    
    
    
    /**
     * <p>u_user(用户表)</p>
     * <p>s_dic_date(字典信息)</p>
     * @param example 此参数只包含排序与分页
     * @return  用户信息
     */
    List<User> selectByExampleKZ(Map<String,Object> example);
    
    /**
     * @param example
     * @return 
     */
    public List<User> getUsersByNotLocked(Map<String,Object> example);
    
    
    /**
     * @param example  条件统计
     * @return int
     */
    int countUsersByNotLocked(Map<String,Object> example);
    
    
    

    UserWithBLOBs selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") UserWithBLOBs record, @Param("example") UserExample example);

    int updateByExampleWithBLOBs(@Param("record") UserWithBLOBs record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(UserWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UserWithBLOBs record);

    int updateByPrimaryKey(User record);
}