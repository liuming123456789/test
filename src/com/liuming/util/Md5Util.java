package com.liuming.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class Md5Util {

	// 日志定义
	public static final Logger log = Logger.getLogger("logger");

	/**
	 * 
	 * @param srcSignString
	 * @param key
	 * @param charset
	 * @return
	 */
	public static String getMd5(String srcSignString, String key, String charset) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(srcSignString.getBytes(charset));

			String result = "";
			byte[] temp;
			temp = md5.digest(key.getBytes(charset));
			for (int i = 0; i < temp.length; i++) {
				result += Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);
			}
			return result;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			log.error("[MD5生成][Md5Util.getMd5]拋异常NoSuchAlgorithmException>e:" + e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[MD5生成][Md5Util.getMd5]拋异常Exception>e:" + e);
		}
		return null;
	}

	public static void main(String[] a) {
		System.out.println(getMd5("123456", ConstantUtil.C_SYS_MD5KEY, ConstantUtil.C_SYS_CHARSET).toUpperCase());
	}

}
