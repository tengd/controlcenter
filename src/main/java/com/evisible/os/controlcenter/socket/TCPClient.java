package com.evisible.os.controlcenter.socket;

import java.io.DataOutputStream;
import java.net.Socket;

public class TCPClient {
	public static void main(String[] args) throws Exception{  
		   Socket s = new Socket("127.0.0.1",5566); //创建一个Socket对象，连接IP地址为192.168.24.177的服务器的5566端口  
		   DataOutputStream dos = new DataOutputStream(s.getOutputStream()); //获取Socket对象的输出流，并且在外边包一层DataOutputStream管道，方便输出数据  
		   Thread.sleep((int)(Math.random()*3000)); //让客户端不定时向服务器发送消息  
		   dos.writeUTF("客户端消息"); //DataOutputStream对象的writeUTF()方法可以输出数据，并且支持中文  
		   dos.flush(); //确保所有数据都已经输出  
		   dos.close(); //关闭输出流  
		   s.close(); //关闭Socket连接  
		 }  

}
