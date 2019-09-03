package test;

import java.io.File;

/** 
* @author Jiangwandong
* @version 创建时间：2018年1月16日
* 类说明 
*/
public class PropertyTest {

	public static void main(String[] args) {
		new PropertyTest().set();

	}
	public void set(){
		String path = this.getClass().getProtectionDomain().getCodeSource()  
                .getLocation().getPath();  
		System.out.println(path);
        String rootPath = path.substring(0, path.lastIndexOf("/classes"));
        System.out.println(rootPath);
        String propertyFilePath = rootPath  
                + "/classes/properties/webservice_config.properties";
        System.out.println(propertyFilePath);
        File file = new File(propertyFilePath);
	}

}
