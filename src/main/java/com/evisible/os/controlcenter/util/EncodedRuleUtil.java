package com.evisible.os.controlcenter.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * @author TengD
 * @Date 2014/10/17
 * 编码规则基类
 */
public class EncodedRuleUtil {
	private static EncodedRuleUtil env;
	public static  synchronized EncodedRuleUtil getEnv(){
		if(env==null){
			env=new EncodedRuleUtil();
			return env;
		}
		return env;
	}
	
	/**
	 * @return 订单号
	 */
	public String getOrderCodeUtil(){
		return "TJ"+this.get7Random()+this.getFormatDate("yyMMdd");
	}
	
	/**
	 * @param max4 0000-9999之间的整数
	 * @return 批次号
	 */
	public String getBatchCodeUtil(int max4){
		return this.getFormatDate("yyyyMMdd")+this.getAccumulator(max4);
	}
	/**
	 * @return 验证码
	 */
	public String getValidateCodeUtil(){
		return this.getCharAndNumr(6);
	}
	
	
	
	
	/**
	 * @return 返回9位不重复随机数
	 */
	@SuppressWarnings({ "unchecked",  "rawtypes" })
	private Integer get7Random(){
		 Integer item=0;
		 try{
			 Random rd = new Random();
			 HashSet set = new HashSet();  
			 while(true){
				 int i= rd.nextInt(999999999);
				 set.add(new Integer(i));
				 if(set.size()==1){
					 break;
				 }
			 }
			 Iterator iter = set.iterator();
			
			 while (iter.hasNext()) {
				 item = (Integer) iter.next();
				 System.out.println(item);
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return item;
	}
	
	/**
	 * @param serialNoMax4系统号最大为4位整数,此参数从数据库中取
	 * @return 
	 */
	public String getAccumulator(int serialNoMax4){
		String code=null;
		try{
			serialNoMax4++;
			if(serialNoMax4<=0)serialNoMax4=1;
			code=serialNoMax4<10000?(serialNoMax4<10?("000"+serialNoMax4):(serialNoMax4<100?"00"+serialNoMax4:serialNoMax4<1000?"0"+serialNoMax4:""+serialNoMax4)):"0001";
			return code;
		}catch(Exception e){
			e.printStackTrace();
			return code;
		}
	}
	/**
	  * java生成随机数字和字母组合
	  * @param length[生成随机数的长度]
	  * @return
	  */
	private String getCharAndNumr(int length) {
		  String val = "";
		  try{
			  Random random = new Random();
			  for (int i = 0; i < length; i++) {
				    // 输出字母还是数字
				    String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; 
				    // 字符串
				    if ("char".equalsIgnoreCase(charOrNum)) {
					    // 取得大写字母还是小写字母
					    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; 
					    val += (char) (choice + random.nextInt(26));
				   } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
					   val += String.valueOf(random.nextInt(10));
				   }
			  }
			  return val;
		  }catch(Exception e){
			  e.printStackTrace();
			  return val;
		  }
	 }
	/**
	 *@param format 
	 * @return 当前格式日期
	 */
	private String getFormatDate(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
		
	}
	
	
	
	
	public static void main(String[] args){
		System.out.println("订单号为："+EncodedRuleUtil.getEnv().getOrderCodeUtil());
		System.out.println("批次号为："+EncodedRuleUtil.getEnv().getBatchCodeUtil(12));
		System.out.println("验证码为："+EncodedRuleUtil.getEnv().getValidateCodeUtil());
	}
	
}
