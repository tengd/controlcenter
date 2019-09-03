package com.evisible.os.controlcenter.util.sign;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.evisible.os.controlcenter.util.StringConvert;
import com.evisible.os.controlcenter.util.MD5.MD5;



/**
 * <p>微信支付签名规则</p>
 * @author TengDong
 * @Date 20160420
 */
public class Sign {
	
	private static Sign env;
	
	public static synchronized Sign getEnv(){
		if(env==null){
			env=new Sign();
			return env;
		}
		return env;
	}
	
	/**
	 * 微信签名
	 * @param map
	 * @return
	 */
	public String getSign(Map<String,Object> map,String Token){
		String StringA="";
		//非空参数值的参数按照参数名ASCII码从小到大排序
		Object[] str=map.keySet().toArray();
		Arrays.sort(str);
		for(int i=0;i<str.length;i++){
			StringA+=str[i].toString().trim()+"="+map.get(str[i]).toString().trim()+"&";
		}
		map.clear();
		StringA+="key="+Token;
		//MD5加密
		MD5 md5=new MD5(StringA);
		String sign=md5.mac32();
		return sign;
	}

	public static void main(String[] args) {
		Map<String,Object> mapSign=new HashMap<String,Object>();
		mapSign.put("appid", "wx108598be16e5342c ");    //公众账号ID
		mapSign.put("mch_id", "1286624101 ");					//商户号
		mapSign.put("device_info", "1000 ");						//设备号
		mapSign.put("body", "test ");                                    //商品供述
		mapSign.put("nonce_str", StringConvert.getUUIDString().toUpperCase());		//随机字符串
		String sign=Sign.getEnv().getSign(mapSign, "o8JvvL");
		System.out.println("签名:"+sign);
	}

}
