package com.evisible.os.business.util.file.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.evisible.os.business.util.PropertiesUtil;

/**
 * 文件保存类
 * @author DuanRan
 *
 */
public class FileService{
	private ArrayList<MultipartFile> files; //待保存的文件队列
	private ArrayList<String> path; 		//文件保存的路径
	private ArrayList<String> type; 		//文件保存的父文件夹名
	private static final Logger log = Logger.getLogger(LocalFileService.class);
	private static final String ROOT;		//文件夹根目录
	private final String FOLDER;			//日期文件夹
	
	static {
		String rootpath = PropertiesUtil.readValue("properties/file.properties", "root"); //获取文件保存根目录
		if(!rootpath.substring(rootpath.length()-1).equals("/")) //如果不以/结尾，加一个/
			rootpath += "/";
		ROOT = rootpath;
		
	}
	
	public FileService()
	{	
		FOLDER = (String) new SimpleDateFormat("yyyyMMdd").format(new Date());
		files = new ArrayList<MultipartFile>();
		type = new ArrayList<String>();
	}
	
	public FileService(ArrayList<MultipartFile> file)
	{	
		FOLDER = (String) new SimpleDateFormat("yyyyMMdd").format(new Date());
		this.files = file;
		type = new ArrayList<String>();
	}
	
	public FileService(MultipartFile file)
	{	
		FOLDER = (String) new SimpleDateFormat("yyyyMMdd").format(new Date());
		files = new ArrayList<MultipartFile>();
		files.add(file);
		type = new ArrayList<String>();
	}
	/**
	 * 取文件列表
	 */
	public ArrayList<MultipartFile> getList() 
	{
		return this.files;
	}
	/**
	 * 文件队列增加
	 * @param file
	 */
	public void add(MultipartFile file,String type)
	{
		this.files.add(file);
		this.type.add(type);
	}
	/**
	 * 执行保存
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<String> saveFile() throws Exception
	{
		if(files.size() == 0)
			return new ArrayList<String>();
		else
		{
			path = new ArrayList<String>();
			for(int i =0;i < files.size() ; i++)
			{
				String savepath = ROOT + type.get(i)+ "/" + FOLDER;
				File dir = new File(savepath);//检测文件夹是否存在
				if (!dir.exists()) {
					log.debug("Create directory ["+ savepath + "]");
					dir.mkdirs();//创建文件夹
				}
				try{
					//设置文件名为时间+乱码
					//取后缀
					String[] suffix = files.get(i).getOriginalFilename().split("\\.");
					savepath += "/";
					String time = (String) new SimpleDateFormat("hhmmssSS").format(new Date());
					if (suffix.length > 1) //如果原始文件名有后缀
					{
						save(files.get(i), savepath + time + RandomStringUtils.randomAlphanumeric(4) + "." + suffix[1]);
					}
					else
					{
						save(files.get(i), savepath + time + RandomStringUtils.randomAlphanumeric(4));
					}
				} catch (Exception e)
				{
					rollback();
					log.error("Faild to save file,rollback.", e);
					throw e;
				}
			}
		}
		return path;
	}
	/**
	 * 保存
	 * @param file
	 * @param path
	 * @throws IOException
	 */
	private void save(MultipartFile file,String path) throws IOException
	{
		File outfile = new File(path);
		outfile.createNewFile();
		try (BufferedInputStream in = new BufferedInputStream(file.getInputStream());
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outfile))) 
		{
			int i = -1;
			while ((i = in.read()) != -1) {
				out.write(i);
			}
			log.debug("File ["+file.getOriginalFilename()+"] saved.");
			this.path.add(path);
		} catch(Exception e)
		{	
			log.error("Faild to save ["+file.getOriginalFilename()+"].");
			throw e;	
		}
	}
	
	/**
	 * 回滚，删除文件
	 */
	public void rollback() 
	{
		for(int i =0;i < path.size() ; i++)
		{
			File file = new File(path.get(i));  
			file.delete();
		}
	}
	
}
