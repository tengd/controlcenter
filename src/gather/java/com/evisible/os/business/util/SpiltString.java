package com.evisible.os.business.util;
/**
 * <p>字符串加引号,批量处理时可用</p>
 * @author tengd
 * @date 2017-10-10
 */
public class SpiltString {
	
	private static SpiltString env;
	public static  synchronized SpiltString getEnv(){
		if(env==null){
			env=new SpiltString();
			return env;
		}
		return env;
	}
	
	public  String  spilt(String str) {
		  StringBuffer sb = new StringBuffer();
		  String[] temp = str.split(",");
		  for (int i = 0; i < temp.length; i++) {
		   if (!"".equals(temp[i]) && temp[i] != null)
		    sb.append("'" + temp[i] + "',");
		  }
		  String result = sb.toString();
		  String tp = result.substring(result.length() - 1, result.length());
		  if (",".equals(tp))
		   return result.substring(0, result.length() - 1);
		  else
		   return result;
		 }
	
	public static void main(String[] args) {
		String str = SpiltString.getEnv().spilt("a,b,c,d,e,f");
		System.out.println(str);

	}

}