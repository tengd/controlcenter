package com.evisible.os.business.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 * 文件下载基类
 * </p>
 * 
 * @author TengDong
 * @date 0170505
 */
public class FileDownUtil {
	
	
	private static FileDownUtil env;
	
	public static  synchronized FileDownUtil getEnv(){
		if(env==null){
			env=new FileDownUtil();
			return env;
		}
		return env;
	}
	
	/**
	 * <P>流下载文件</p>
	 * @param path
	 * @param response
	 * @return HttpServletResponse
	 */
	public HttpServletResponse download(String path,
			HttpServletResponse response) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return response;
	}

	/**
	 * <P>流下载文件</p> 带文件名版本
	 * @param path
	 * @param response
	 * @return HttpServletResponse
	 */
	public HttpServletResponse download(String path,
			HttpServletResponse response,String filename) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return response;
	}
	
	/**
	 * <p>下载本地文件</p>
	 * @param response
	 * @param fileName 文件名
	 * @param path 文件的存放路径
	 * @throws FileNotFoundException
	 */
	public void downloadLocal(HttpServletResponse response,String fileName,String path)
			throws FileNotFoundException {
		// 读到流中
		InputStream inStream = new FileInputStream(path);// 文件的存放路径
		// 设置输出的格式
		response.reset();
		response.setContentType("bin");
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
		// 循环取出流中的数据
		byte[] b = new byte[100];
		int len;
		try {
			while ((len = inStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>下载网络文件</p>
	 * @param response
	 * @param urlpath  url地址
	 * @param outputPathAndFileName 输出文件路径与文件名
	 * @throws MalformedURLException
	 */
	public void downloadNet(HttpServletResponse response,String urlpath,String outputPathAndFileName)
			throws MalformedURLException {
		int bytesum = 0;
		int byteread = 0;
		URL url = new URL(urlpath);
		try {
			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			@SuppressWarnings("resource")
			FileOutputStream fs = new FileOutputStream(outputPathAndFileName);
			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread;
				System.out.println(bytesum);
				fs.write(buffer, 0, byteread);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>支持在线打开文件的一种方式</p>
	 * @param filePath   文件在线路径
	 * @param response
	 * @param isOnLine   是否在线打开，开关
	 * @throws Exception
	 */
	public void downLoad(String filePath, HttpServletResponse response,
			boolean isOnLine) throws Exception {
		File f = new File(filePath);
		if (!f.exists()) {
			response.sendError(404, "File not found!");
			return;
		}
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
		byte[] buf = new byte[1024];
		int len = 0;
		response.reset(); // 非常重要
		if (isOnLine) { // 在线打开方式
			URL u = new URL("file:///" + filePath);
			response.setContentType(u.openConnection().getContentType());
			response.setHeader("Content-Disposition",
					"inline; filename=" + f.getName());
			// 文件名应该编码成UTF-8
		} else { // 纯下载方式
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());
		}
		OutputStream out = response.getOutputStream();
		while ((len = br.read(buf)) > 0)
			out.write(buf, 0, len);
		br.close();
		out.close();
	}
}
