package com.evisible.os.business.util.file.impl;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.evisible.os.business.util.file.IFileService;
import com.evisible.os.controlcenter.util.StringConvert;




/**
 * 文件保存在本地服务器
 * <p>
 * 文件按照上传日期保存在ROOT_PATH中对应日期的文件夹中
 * <p>
 * 文件的URL为ADDRESS/{folder-name}/{file-name}
 * <p>
 * 需要配置http服务器以通过http服务器来获取文件
 * @author HouYu
 */
public class LocalFileService implements IFileService {
    private MultipartFile img;
	private static final Logger log = Logger.getLogger(LocalFileService.class);
	private static final String ROOT;
	private static final String IMAGE_ROOT;
	private static final String HOST;
	private static final String IMAGE_HOST;
	
	private static final Pattern IMG_PATTERN = Pattern.compile(".+\\.(jpg|png|gif)$", Pattern.CASE_INSENSITIVE);

	/**
	 * 从file.properties加载配置信息
	 */
	static {
		Properties p = new Properties();
		try {
			InputStream in = LocalFileService.class.getClassLoader().getResourceAsStream("properties/file.properties");
			p.load(in);
		} catch (Exception e) {
			log.fatal("加载文件存储配置文件失败");
			System.exit(1);
		}
		ROOT = p.getProperty("root");
		HOST = p.getProperty("host");
		if (StringUtils.isEmpty(ROOT) || StringUtils.isEmpty(HOST)) {
			log.fatal("文件上传配置文件配置不正确，找不到root或host。");
			System.exit(1);
		}
		IMAGE_ROOT = ROOT + "images/";
		IMAGE_HOST = HOST + "images/";
	}
	
	public LocalFileService(){}
	
	public LocalFileService(MultipartFile img){
		this.img=img;
	}
	
	/**
	 * @param file
	 * @return 文件后缀
	 */
	private String getFileSuffix(MultipartFile file) {
		return file.getOriginalFilename().substring(
				file.getOriginalFilename().lastIndexOf("."));
	}
	
	/**
	 * @param file
	 * @return 是否是图片
	 */
	private boolean isImage(MultipartFile file) {
		return IMG_PATTERN.matcher(file.getOriginalFilename()).matches();
	}

	private String getName(MultipartFile file) {
		return StringConvert.getUUIDString() + getFileSuffix(file);
	}
	
	/**
	 * @param folder
	 * @return 文件路径
	 */
	private static String getImagePath(String folder) {
		String path = IMAGE_ROOT + folder;
		File dir = new File(path);
		if (!dir.exists()) {
			log.debug("Create directory ["+path+"]");
			dir.mkdirs();
		}
		return path;
	}
	
	/**
	 * @return 文件夹
	 */
	private String getFolder() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	public String saveImage(MultipartFile img) {
		log.debug("Start to save image");
		if (!isImage(img)) {
			log.error("Save image failed, given file is not a valid image file");
			throw new IllegalArgumentException("非图片文件["+img.getOriginalFilename()+"]");
		}
		String folder = getFolder();   
		String path = getImagePath(folder);
		String fileName = getName(img);
		File outImg = new File(path + File.separator + fileName);
		try {
			outImg.createNewFile();
			log.debug("Create file ["+outImg.getName()+"]");
		} catch (IOException e) {
			log.error("创建文件失败", e);
			return null;
		}
		try (BufferedInputStream in = new BufferedInputStream(img.getInputStream());
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outImg))) {
			int i = -1;
			while ((i = in.read()) != -1) {
				out.write(i);
			}
			out.flush();
			String url = IMAGE_HOST + folder + "/" + fileName;
			log.debug("Save image succeed, image url is ["+url+"]");
			return url;
		} catch (Exception e) {
			log.error("Faild to save image file.", e);
			throw new RuntimeException("上传文件失败");
		}
	}
	
	

	@Override
	public void run() {
		log.debug("Start to save image");
		if (!isImage(img)) {
			log.error("Save image failed, given file is not a valid image file");
			throw new IllegalArgumentException("非图片文件["+img.getOriginalFilename()+"]");
		}
		String folder = getFolder();
		String path = getImagePath(folder);
		String fileName = getName(this.getImg());
		File outImg = new File(path + File.separator + fileName);
		try {
			outImg.createNewFile();
			log.debug("Create file ["+outImg.getName()+"]");
		} catch (IOException e) {
			log.error("创建文件失败", e);
			
		}
		try (BufferedInputStream in = new BufferedInputStream(this.getImg().getInputStream());
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outImg))) {
			int i = -1;
			while ((i = in.read()) != -1) {
				out.write(i);
			}
			out.flush();
			String url = IMAGE_HOST + folder + "/" + fileName;
			log.debug("Save image succeed, image url is ["+url+"]");
		} catch (Exception e) {
			log.error("Faild to save image file.", e);
			throw new RuntimeException("上传文件失败");
		}
		
	}
	
	
	

	public MultipartFile getImg() {
		return img;
	}

	public void setImg(MultipartFile img) {
		this.img = img;
	}
	
}
