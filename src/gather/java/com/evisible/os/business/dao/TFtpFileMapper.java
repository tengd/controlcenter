package com.evisible.os.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.evisible.os.business.entity.TFtpFile;
import com.evisible.os.business.entity.TFtpFileExample;

public interface TFtpFileMapper {
    int countByExample(TFtpFileExample example);

    int deleteByExample(TFtpFileExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(TFtpFile record);

    int insertSelective(TFtpFile record);

    List<TFtpFile> selectByExample(TFtpFileExample example);

    TFtpFile selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") TFtpFile record, @Param("example") TFtpFileExample example);

    int updateByExample(@Param("record") TFtpFile record, @Param("example") TFtpFileExample example);

    int updateByPrimaryKeySelective(TFtpFile record);

    int updateByPrimaryKey(TFtpFile record);
    
    List<TFtpFile> selectFtpFiles(@Param("cond") Map<String , Object> cond);
    
    int findIsTableExists(String tableName);
	
	void createTableByXmlMap(Map<String , String> createTableMap);
	
	void insertResolvedBaseData(@Param("tableName")String tableName , @Param("fieldListStr")String fieldListStr , @Param("values")String values);
	
	void insertBatchResolvedBaseData(@Param("tableName")String tableName , @Param("fieldListStr")String fieldListStr , @Param("values")List<String> values);
}