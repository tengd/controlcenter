package com.evisible.os.business.util.file;

import org.springframework.web.multipart.MultipartFile;

/**
 * 处理文件
 * @author HouYu
 */
public interface IFileService extends Runnable {

	/**
	 * 保存文件，并返回文件路径
	 * @param file
	 * @param request
	 * @return
	 */
	public String saveImage(MultipartFile file);
	
	
}
