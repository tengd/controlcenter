package com.evisible.os.controlcenter.socket;

import java.io.DataInputStream;
import java.net.Socket;

public class Client implements Runnable{

	 int clientIndex = 0; 	//保存客户端id  
	 Socket s = null; 		//保存客户端Socket对象  
	     
	 Client(int i,Socket s){  
	     clientIndex = i;  
	     this.s = s;  
	  }  

	@Override
	public void run() {
		//打印出客户端数据  
		 try{  
		      DataInputStream dis = new DataInputStream(s.getInputStream());  
		      System.out.println("第" + clientIndex + "个客户端发出消息：" + dis.readUTF());  
		      dis.close();  
		      s.close();  
		    }  
		   catch(Exception e)  
		   {}  

	}

}
