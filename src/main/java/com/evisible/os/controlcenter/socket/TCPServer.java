package com.evisible.os.controlcenter.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(5566); //创建一个Socket服务器，监听5566端口  
		 int i=0;  
		 //利用死循环不停的监听端口  
		 while(true){  
		    Socket s = ss.accept();//利用Socket服务器的accept()方法获取客户端Socket对象。  
		      i++;  
		     System.out.println("第" + i +"个客户端成功连接！");  
		     Client c = new Client(i,s); //创建客户端处理线程对象  
		     Thread t =new Thread(c); //创建客户端处理线程  
		      t.start(); //启动线程  
		    }  


	}

}
