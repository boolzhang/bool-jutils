package com.bool.jutils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;


/**
 * MD5工具类
 * ClassName: MD5Util <br/>
 * date: 2018年1月13日 下午6:54:53 <br/>
 *
 * @author Bool
 * @version
 */
public class MD5Util {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d","e", "f" };

	/***
	 * 将字符创处理成MD5值.
	 * 
	 * @param str
	 * @return
	 */
	public static String MD5(String str) {

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			
			byte[] btInput = str.getBytes("utf-8");

			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str1[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str1[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str1[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * byteArrayToHexString:字节转换成String
	 * @author Bool
	 * @param b
	 * @return
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resSb.append(byteToHexDigits(b[i]));
		}
		return resSb.toString();
	}

	/**
	 * byteToHexDigits:字节转换成String
	 * @author Bool
	 * @param b
	 * @return
	 */
	private static String byteToHexDigits(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * calcFileMD5:计算文件的MD5值
	 * @author Bool
	 * @param file
	 * @return
	 */
	public static String calcFileMD5(File file) {
		FileInputStream fis = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			fis.close();
			return byteArrayToHexString(md.digest());
		} catch (Exception ex) {
			return null;
		}
	}

}
