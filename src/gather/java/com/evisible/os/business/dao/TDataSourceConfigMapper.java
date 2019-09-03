package com.evisible.os.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.evisible.os.business.entity.TDataSourceConfig;
import com.evisible.os.business.entity.TDataSourceConfigExample;

public interface TDataSourceConfigMapper {
    int countByExample(TDataSourceConfigExample example);

    int deleteByExample(TDataSourceConfigExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(TDataSourceConfig record);

    int insertSelective(TDataSourceConfig record);

    List<TDataSourceConfig> selectByExample(TDataSourceConfigExample example);

    TDataSourceConfig selectByPrimaryKey(String uuid);
    
    TDataSourceConfig getDataSourceConfigByTriggerId(@Param("triggerUuid")String triggerUuid);

    int updateByExampleSelective(@Param("record") TDataSourceConfig record, @Param("example") TDataSourceConfigExample example);

    int updateByExample(@Param("record") TDataSourceConfig record, @Param("example") TDataSourceConfigExample example);

    int updateByPrimaryKeySelective(TDataSourceConfig record);

    int updateByPrimaryKey(TDataSourceConfig record);
    
    List<TDataSourceConfig> selectByType(Map<String , Object> conditions);
}