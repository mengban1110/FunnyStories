package cn.DoO.Utils.Tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * *
 * <p>
 * 加密相关 : <br>
 * 1 : MD5加密---MD5(String plainText) <br>
 * 
 * @author 梦伴
 *
 */
public class Md5Utils {
	/**
	 * @desc MD5加密
	 * @author 梦伴
	 * @param plainText
	 * @return 加密值
	 */
	public static String makeMd5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有这个md5算法！");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

	public static void main(String[] args) {
		 System.out.println(makeMd5("123"));
	}
}
