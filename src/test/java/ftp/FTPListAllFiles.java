package ftp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * <p>FTP测试例子</p>
 * @author Administrator
 * @Date 20180124
 */
public class FTPListAllFiles {

	  private static Logger log = LoggerFactory.getLogger(FTPListAllFiles.class);
	  public FTPClient ftp;  
	  public java.util.List<String> arFiles;  
	      
	    /** 
	     * 重载构造函数 
	     * @param isPrintCommmand 是否打印与FTPServer的交互命令 
	     */  
	    @Autowired
		public FTPListAllFiles(boolean isPrintCommmand){  
	        ftp = new FTPClient();  
	        arFiles = new ArrayList<String>();  
	        if(isPrintCommmand){  
	            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));  
	        }  
	    }  
	      
	    /** 
	     * 登陆FTP服务器 
	     * @param host FTPServer IP地址 
	     * @param port FTPServer 端口 
	     * @param username FTPServer 登陆用户名 
	     * @param password FTPServer 登陆密码 
	     * @return 是否登录成功 
	     * @throws IOException 
	     */  
	    public boolean login(String host,int port,String username,String password) throws IOException{  
	        this.ftp.connect(host,port);  
	        if(FTPReply.isPositiveCompletion(this.ftp.getReplyCode())){  
	            if(this.ftp.login(username, password)){  
	                this.ftp.setControlEncoding("utf-8");  
	                return true;  
	            }  
	        }  
	        if(this.ftp.isConnected()){  
	            this.ftp.disconnect();  
	        }  
	        return false;  
	    }  
	      
	    /** 
	     * 关闭数据链接 
	     * @throws IOException 
	     */  
	    public void disConnection() throws IOException{  
	        if(this.ftp.isConnected()){  
	            this.ftp.disconnect();  
	        }  
	    }  
	      
	    /** 
	     * 递归遍历出目录下面所有文件 
	     * @param pathName 需要遍历的目录，必须以"/"开始和结束 
	     * @throws IOException 
	     */  
	    public void List(String pathName) throws IOException{  
	        if(pathName.startsWith("/")&&pathName.endsWith("/")){  
	            String directory = pathName;  
	            //更换目录到当前目录  
	            this.ftp.changeWorkingDirectory(directory);  
	            FTPFile[] files = this.ftp.listFiles();  
	            for(FTPFile file:files){ 
	            	System.out.println(file.getName());
	                if(file.isFile()){  
	                    arFiles.add(directory+file.getName());  
	                }
//	                else if(file.isDirectory()){  
//	                    List(directory+file.getName()+"/");  
//	                }  
	            }  
	        }  
	    }  
	      
	    /** 
	     * 递归遍历目录下面指定的文件名 
	     * @param pathName 需要遍历的目录，必须以"/"开始和结束 
	     * @param ext 文件的扩展名 
	     * @throws IOException  
	     */  
	    public void List(String pathName,String ext) throws IOException{  
	        if(pathName.startsWith("/")&&pathName.endsWith("/")){  
	            String directory = pathName;  
	            //更换目录到当前目录  
	            this.ftp.changeWorkingDirectory(directory);  
	            FTPFile[] files = this.ftp.listFiles();
	            
	            for(FTPFile file:files){  
	                if(file.isFile()){  
	                    if(file.getName().endsWith(ext)){  
	                        arFiles.add(directory+file.getName());  
	                    }  
	                }
//	                else if(file.isDirectory()){  
//	                    List(directory+file.getName()+"/",ext);  
//	                }  
	            }  
	        }  
	    } 
	    
	    
	    public static void main(String[] args) throws IOException {  
//	        FTPListAllFiles f = new FTPListAllFiles(true);  
//	        if(f.login("116.52.7.73", 21, "ythgf", "ythgf@20170309")){  
//	            f.List("/");  
//	        }  
//	        f.disConnection();  
//	        Iterator<String> it = f.arFiles.iterator();  
//	        while(it.hasNext()){  
//	        	log.info(it.next());  
//	        }  
	        File file1 =  new File("F:/bak/");
	        File[] files =  file1.listFiles();
	        System.out.println(files.length);
	    }  
    
}
