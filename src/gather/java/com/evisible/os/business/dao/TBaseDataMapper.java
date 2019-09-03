package com.evisible.os.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.evisible.os.business.entity.TBaseData;
import com.evisible.os.business.entity.TBaseDataExample;

public interface TBaseDataMapper {
	 int countByExample(TBaseDataExample example);

	    int deleteByExample(TBaseDataExample example);

	    int deleteByPrimaryKey(String uuid);

	    int insert(TBaseData record);

	    int insertSelective(TBaseData record);

	    List<TBaseData> selectByExampleWithBLOBs(TBaseDataExample example);

	    List<TBaseData> selectByExample(TBaseDataExample example);

	    TBaseData selectByPrimaryKey(String uuid);
	    
	    TBaseData selectByPrimaryKeyWithoutBlob(String uuid);

	    int updateByExampleSelective(@Param("record") TBaseData record, @Param("example") TBaseDataExample example);

	    int updateByExampleWithBLOBs(@Param("record") TBaseData record, @Param("example") TBaseDataExample example);

	    int updateByExample(@Param("record") TBaseData record, @Param("example") TBaseDataExample example);

	    int updateByPrimaryKeySelective(TBaseData record);

	    int updateByPrimaryKeyWithBLOBs(TBaseData record);

	    int updateByPrimaryKey(TBaseData record);
	    
	    int validateToken(String token);
	    
		int insertTransferData (TBaseData baseData) throws Exception;
		
		List<TBaseData> selectBaseDatas(@Param("cond") Map<String , Object> cond);
		
		int findIsTableExists(String tableName);
		
		void createTableByXmlMap(Map<String , String> createTableMap);
				
		TBaseData getBaseDataInfoByTriggerUuid(@Param("triggerUuid") String triggerUuid);
		
		/**
		 * 修改表
		 */
		void alterTable(@Param("tableName") String tableName, @Param("alterType") String alterType , @Param("alterDesc") String alterDesc);
		
		void insertBatchResolvedBaseData(@Param("tableName")String tableName , @Param("fieldListStr")String fieldListStr , @Param("values")List<String> values);

}