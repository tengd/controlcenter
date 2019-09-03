package com.evisible.os.controlcenter.util;

import com.evisible.os.controlcenter.util.MD5.MD5;

/**
 * <P>订单模块工具类</P>
 * @author wangwenbao
 * @date 2015年8月12日
 * @email wangwenbao@xiaojiuwo.cn
 */
public class TokenUtils {
		public static void main(String[] args) {
			System.out
					.println(getToken("{\"sortBy\":0,\"sortType\":1,\"categoryId\":\"\",\"keyWord\":\"华为\",\"pageSize\":10,\"pageNum\":1}"));
		}

	/**
	 * 获取商家token
	 * @param params
	 * @return
	 */
	public static String getToken(Object params) {
		String token = params + "|" + "ForTest";
		MD5 md5=new MD5(token);
		token = md5.mac32();
		return token;
	}
	

	/**
	 * 根据传入的参数和秘钥获取商家token
	 * @param params
	 * @param secretKey
	 * @return
	 */
	public static String getToken(String params, String secretKey) {
		String token = params + "|" + secretKey;
		MD5 md5=new MD5(token);
		token = md5.mac32();
		return token;
	}

	/**
	 * 获取商家token
	 * @param params
	 * @return
	 */
	public static String getToken(Object params, String key) {
		String token = params + "|" + key;
		MD5 md5=new MD5(token);
		token = md5.mac32();
		return token;
	}

}
