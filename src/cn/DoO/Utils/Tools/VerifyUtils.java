package cn.DoO.Utils.Tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 数据校验相关 : <br>
 * 1 : 正则匹配是否手机号---isPhone(String phone) <br>
 * 2 : 正则匹配是否邮箱号---isEmail(String email) <br>
 * 3 : 验证字符串长度---isStrSize(String str, int size) <br>
 * 4 : 判断 密码是否 合法---verify(String passwordLowerCase, int limitCount)<br>
 * 
 * @author 梦伴
 *
 */
public class VerifyUtils {

	// 包含横向连续的键盘输入或者连续的数字和字母
	private static final String[] KEYBOARD_METADATA = { "qwertyuiop[]\\", "asdfghjkl;'", "zxcvbnm,./", "1234567890-=",
			"0123456789012345678", "abcdefghijklmnopqrstuvwxyz" };
	// 包含连续的斜向键盘输入或者常用的数据库、操作系统等词组
	private static final String[] COTAINEDSTR_METADATA = { "qaz", "wsx", "edc", "rfv", "tgb", "yhn", "ujm", "ik,",
			"ol.", "p;/", "esz", "rdx", "tfc", "ygv", "uhb", "ijn", "okm", "pl,", "[;.", "]'/", "1qa", "2ws", "3ed",
			"4rf", "5tg", "6yh", "7uj", "8ik", "9ol", "0p;", "-['", "=[;", "-pl", "0ok", "9ij", "8uh", "7yg", "6tf",
			"5rd", "4es", "3wa", "root", "admin", "mysql", "oracle", "system", "windows", "linux", "java", "python",
			"unix" };

	/**
	 * @Desc 验证手机号
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		String pattern = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(phone);
		return m.matches();
	}

	/**
	 * @Desc 验证邮箱
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (null == email || "".equals(email)) {
			return false;
		}
		String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p = Pattern.compile(regEx1);
		Matcher m = p.matcher(email);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Desc 验证长度
	 * @param str
	 * @param size
	 * @return
	 */
	public static boolean isStrSize(String str, int size) {
		if (str.length() < size) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否包含连续的斜向键盘输入或者常用的数据库、操作系统等词组
	 *
	 * @param lowerCasPassword 转换成小写的密码串
	 * @return 返回不符合规范的字符序列
	 */
	private static String containTargetStrVerify(String lowerCasPassword) {
		for (String string : COTAINEDSTR_METADATA) {
			if (lowerCasPassword.contains(string)) {
				return string;
			}
		}
		return null;
	}

	/**
	 * 判断是否包含连续的横向键盘输入或者连续的数字和字母
	 *
	 * @param lowerCasPassword 转换成小写的密码串
	 * @param limitCount       限制连续字符数的最大值
	 * @return 返回不符合规范的字符序列
	 */
	private static String containTargetSequenceVerify(String lowerCasPassword, int limitCount) {
		char[] chars = lowerCasPassword.toCharArray();
		for (int c = 0; c < chars.length - limitCount; c++) {
			char passwordChar = chars[c];
			String repeatStr = "";
			for (int i = 0; i < limitCount + 1; i++) {
				repeatStr += c;
			}
			if (lowerCasPassword.contains(repeatStr)) {
				return repeatStr;
			}
			for (String strings : KEYBOARD_METADATA) {
				if (strings.contains(String.valueOf(passwordChar))) {
					String substring = lowerCasPassword.substring(c, c + limitCount + 1);
					if (strings.contains(substring)) {
						return substring;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 判断是否含有特殊字符、大写字母、小写字母、数字中的三种及以上
	 * <p>
	 * true为包含，false为不包含
	 */
	public static boolean containTypesVerify(String password) {
		int contains = 0;
		// 判断是否包含特殊字符
		String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(password);
		if (m.find()) {
			contains++;
		}
		// 判断是否含有数字
		p = Pattern.compile(".*\\d+.*");
		m = p.matcher(password);
		if (m.find()) {
			contains++;
		}
		// 判断是否含有大写字母
		p = Pattern.compile("[A-Z]");
		m = p.matcher(password);
		if (m.find()) {
			contains++;
		}
		// 判断是否含有小写字母
		p = Pattern.compile("[a-z]");
		m = p.matcher(password);
		if (m.find()) {
			contains++;
		}
		return contains > 2;
	}

	/**
	 * _判断 密码情况
	 * 
	 * @param passwordLowerCase
	 * @param limitCount
	 * @return
	 */
	public static String verify(String passwordLowerCase, int limitCount) {
		if (!containTypesVerify(passwordLowerCase)) {
			return "密码需要包含特殊字符、大写字母、小写字母、数字中的三种及以上！";
		}
		String s1 = containTargetStrVerify(passwordLowerCase.toLowerCase());
		if (s1 != null) {
			return "密码包含连续的斜向键盘输入或者常用的数据库、操作系统等词组！内容为：" + s1 + "。";
		}
		String s2 = containTargetSequenceVerify(passwordLowerCase.toLowerCase(), limitCount);
		if (s2 != null) {
			return "密码包含连续的横向键盘输入或者连续的数字和字母或者重复的字符输入！";
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(verify("Wushang816",6));
	}

}
