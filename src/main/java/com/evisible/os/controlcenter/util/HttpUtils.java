package com.evisible.os.controlcenter.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * <P>工具类,</P>
 * @author TengDong
 * @date 2015年8月18日
 */
public class HttpUtils {

	/**
	 * @param is
	 * @param contentLen
	 * @return  消息流
	 */
	public static final byte[] readBytes(InputStream is, int contentLen) {
		if (contentLen > 0) {
			int readLen = 0;
			int readLengthThisTime = 0;
			byte[] message = new byte[contentLen];
			try {
				while (readLen != contentLen) {
					readLengthThisTime = is.read(message, readLen, contentLen - readLen);
					if (readLengthThisTime == -1) {// Should not happen.
						break;
					}
					readLen += readLengthThisTime;
				}
				return message;
			} catch (IOException e) {
				 e.printStackTrace();
			}
		}
		return new byte[] {};
	}
}
