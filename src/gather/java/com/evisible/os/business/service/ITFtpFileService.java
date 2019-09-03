/**
* @author Jiangwandong
* @version 创建时间：2018年1月30日
*/
package com.evisible.os.business.service;

import java.io.IOException;
import java.util.Map;

import com.evisible.os.business.entity.TFtpFile;
import com.evisible.os.controlcenter.model.Message;
import com.evisible.os.controlcenter.model.PageUI;

/**
 * 
 * <p>
 * ftp文件业务类接口
 * </p>
 * 
 * @author JiangWanDong
 * @Date 2018年1月30日
 */
public interface ITFtpFileService {
	Message getFtpFilesInfo(String dataSourceId);

	Map<String, Object> getFtpBaseData(PageUI pageUI, TFtpFile file);

	boolean lockFtpFileData(String[] uuids);
	
	boolean deleteFtpFileData(String[] uuids);

	String getFtpFileContent(String uuid) throws IOException;
	
	Message resolveFtpFileData(String[] uuids);
	
	void resolveAllUnResolvedFtpData();
}
