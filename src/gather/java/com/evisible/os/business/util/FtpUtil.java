package com.evisible.os.business.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPCmd;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evisible.os.business.entity.TFtpFile;
import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.resolve.ResolveFactory;

/**
 * 
 * <p>
 * ftp工具类
 * </p>
 * 
 * @author JiangWanDong
 * @Date 2018年1月30日
 */
public class FtpUtil {

	private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

	/**
	 * 登陆FTP服务器
	 * 
	 * @param host
	 *            FTPServer IP地址
	 * @param port
	 *            FTPServer 端口
	 * @param username
	 *            FTPServer 登陆用户名
	 * @param password
	 *            FTPServer 登陆密码
	 * @return 是否登录成功
	 * @throws IOException
	 */
	public static FTPClient getFtpClientAngLogin(String host, int port, String username, String password)
			throws IOException {
		FTPClient ftp = new FTPClient();
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		ftp.connect(host, port);
		if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
			if (ftp.login(username, password)) {
				ftp.setControlEncoding("utf-8");
			}
		}
		return ftp;
	}

	public static void disconnectFtp(FTPClient ftp) {
		if (ftp != null && ftp.isConnected()) {
			try {
				ftp.disconnect();
				logger.info("-----ftp Connect已关闭--------------");
			} catch (IOException e) {
				logger.info("-----ftp Connect关闭出错--------------");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @Description: 获取指定路径下ftp文件列表
	 * @param pathName
	 *            路径名
	 * @param isRecur
	 *            是否递归遍历
	 * @param ftp
	 *            ftpClient
	 * @return void
	 * @throws IOException
	 */
	public static List<TFtpFile> getFileList(String pathName, boolean isRecur, FTPClient ftp) throws IOException {
		List<TFtpFile> fileList = new ArrayList<>();
		if (pathName.startsWith("/") && pathName.endsWith("/")) {
			String directory = pathName;
			// 更换目录到当前目录
			ftp.changeWorkingDirectory(directory);
			FTPFile[] files = ftp.listFiles();
			for (FTPFile file : files) {
				if (file.isFile()) {
					TFtpFile ftpFile = new TFtpFile();
					//若文件名中存在乱码或者文件名为空， 不读取
					if(StringConvert.isMessive(file.getName()) || StringConvert.isEmpty(file.getName())){
						continue;
					}
					String fileName = file.getName();
					ftpFile.setCreatedate(file.getTimestamp().getTime());
					ftpFile.setCreator(file.getUser());
					ftpFile.setType(fileName.substring(fileName.lastIndexOf(".")));
					ftpFile.setFilename(fileName);
					ftpFile.setStorepath(pathName);
					fileList.add(ftpFile);
					file.toFormattedString();
				} else {
					if (isRecur) {
						if (file.isDirectory()) {
							getFileList(directory + file.getName() + "/", isRecur, ftp);
						}
					}
				}
			}
		}
		return fileList;
	}
	
/**
 * <p>直接获取ftp服务器上xml文件的内容并返回</p>
 */
	public static String getFtpXmlFileContent(String directory, String fileName, FTPClient ftpClient) throws IOException {
		StringBuffer resultBuffer = new StringBuffer();
		InputStream in = null;
		logger.info("开始读取绝对路径" + directory + "文件!");
		ftpClient.setControlEncoding("UTF-8"); // 中文支持
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();
		ftpClient.changeWorkingDirectory(directory);
		FTPFile[] files = ftpClient.listFiles();
		for (FTPFile file : files) {
			if (fileName.equals(file.getName())) {
				in = ftpClient.retrieveFileStream(new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
			}
		}
		if (in != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String data = null;
			try {
				while ((data = br.readLine()) != null) {
					resultBuffer.append(data);
				}
			} finally {
				in.close();
			}
		} else {
			logger.error("in为空，不能读取。");
			return null;
		}
		return resultBuffer.toString();
	}
	
	/*
	 * 将excel从ftp服务器下载至本地临时文件， 解析成xml，删除临时文件， 返回xml
	 */
	public static String getFtpExcelFileContent(String directory, String fileName, FTPClient ftpClient , String unResolveField) throws FileNotFoundException{
		File file = downFile(directory,fileName, ftpClient);
		String path = file.getAbsolutePath();
		String xmlContent = ResolveFactory.createExcelUtil().reslveExcelAsXml(path, unResolveField);
		file.delete();
		return xmlContent;
	}

	/**
	 * 
	 * @Description: 移动文件 @param directory 当前文件所在目录 @param fileName 文件名 @param
	 *               destination 移动目的地 @param ftp @return void @throws
	 */
	public static boolean moveFile(String directory, String fileName, String destination, FTPClient ftp)
			throws IOException {
		ftp.changeWorkingDirectory(directory);
		String destDir = destination.substring(0, destination.lastIndexOf("/"));
		ftp.mkd(destDir);
		int RNFRReplayCode = ftp.sendCommand(FTPCmd.RNFR, fileName);
		int RANTOReplayCode = ftp.sendCommand(FTPCmd.RNTO, destination);
		//如果文件已存在则将文件名加上bak之后再尝试移动
		if(RANTOReplayCode == 553){
			String suffix = destination.substring(destination.lastIndexOf("."));
			ftp.sendCommand(FTPCmd.RNFR, fileName);
			ftp.sendCommand(FTPCmd.RNTO, destination.substring(0, destination.lastIndexOf("."))+"_bak"+System.currentTimeMillis()+suffix);
		}
		return FTPReply.isPositiveIntermediate(RNFRReplayCode) || !FTPReply.isPositiveCompletion(RANTOReplayCode);
	}

	public static boolean mkdir(String dirName, FTPClient ftp) {
		try {
			ftp.changeWorkingDirectory("/");
			return ftp.makeDirectory(dirName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public static void deleteDir(String dirName, FTPClient ftp) {
		try {
			ftp.changeWorkingDirectory("/");
			ftp.removeDirectory(dirName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 将ftp上的文件下载到本地解析， 对于excel这类不能在远程直接获取内容的，需要下载到本地解析
	 */
	public static File downFile(String directory, String fileName, FTPClient ftp) {  
	    File localFile = null;
	    try {  
	        int reply;  
	        reply = ftp.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            ftp.disconnect();  
	            return null;  
	        }  
	        ftp.changeWorkingDirectory(directory);//转移到FTP服务器目录  
	        FTPFile[] fs = ftp.listFiles();  
	        for(FTPFile ff:fs){  
	            if(ff.getName().equals(fileName)){
	            	String filePath = FtpUtil.class.getResource("/").getPath()+"/"+ff.getName();
	            	localFile = new File(filePath);                                       
	                OutputStream is = new FileOutputStream(localFile);
	                ftp.retrieveFile(ff.getName(), is);  
	                is.close();  
	            }  
	        }
	        return localFile;
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } 
	    return null;  
	}  

	public static void main(String[] args) {
		FTPClient ftp = null;
		try {
//			long t1 = System.currentTimeMillis();
			ftp = getFtpClientAngLogin("116.52.7.73", 21, "jzny", "jzny@compal_20180328");
			System.out.println("--"+getFtpExcelFileContent("/" , "jzny-payment-201803291519.xls" , ftp ,"编号"));
//			long t2 = System.currentTimeMillis();
//			long t3 = System.currentTimeMillis();
//			System.out.println("===========ready");
//			 List<TFtpFile> fileList = getFileList("/bak/" , false , ftp);
//			 System.out.println("==========="+fileList.size());
//			 List<String> fileNameList = new ArrayList<>();
//			 for(TFtpFile file : fileList){
//				 fileNameList.add(file.getFilename());
//			 }
//			 System.out.println(fileNameList.size());
//			 
//			 File file1 =  new File("F:/bak/");
//			 File[] fileArr = file1.listFiles();
//			 List<String> localFileNameList = new ArrayList<>();
//			 for(int i = 0 ; i < fileArr.length ; i++){
//				 localFileNameList.add(fileArr[i].getName());
//			 }
//			 fileNameList.removeAll(localFileNameList);
//			 System.out.println(fileNameList);
			// for(TFtpFile file : fileList){
			// System.out.println(file.getFilename());
			// }
			// if(fileList.size()>0){
			//
			// }

			// System.out.println(getFtpFileContent("/bak/",
			// "ythgf_instore_weight_20180109010008.xml", ftp));
			// mkdir("testDir/bak" , ftp);
//			moveFile("/", "ythgf_instore_check_20171123020005.xml",
//					"/testDir/ythgf_instore_check_20171123020005.xml", ftp);
			// mkdir("testDir/bak" , ftp);
			// deleteDir("testDir" , ftp);
//			long t4 = System.currentTimeMillis();
//			System.out.println("登录耗时：" + (t2 - t1) / 1000 + "秒 , 操作文件用时：" + (t4 - t3) / 1000 + "秒");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnectFtp(ftp);
		}
	}
}
