package com.evisible.os.timing.quartz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.evisible.os.timing.quartz.entity.TTriggerConfig;
import com.evisible.os.timing.quartz.entity.TTriggerConfigExample;

public interface TTriggerConfigMapper {
    int countByExample(TTriggerConfigExample example);

    int deleteByExample(TTriggerConfigExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(TTriggerConfig record);

    int insertSelective(TTriggerConfig record);

    List<TTriggerConfig> selectByExample(TTriggerConfigExample example);

    TTriggerConfig selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") TTriggerConfig record, @Param("example") TTriggerConfigExample example);

    int updateByExample(@Param("record") TTriggerConfig record, @Param("example") TTriggerConfigExample example);

    int updateByPrimaryKeySelective(TTriggerConfig record);

    int updateByPrimaryKey(TTriggerConfig record);
    
    Map<String , Object> getDataSourceInfoForTrigger(@Param("dataSourceId")String dataSourceId , @Param("tableName")String tableName);
}