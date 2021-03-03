package cn.DoO.Utils;

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
 * <p> 加密相关 : <br>
 * 1 : MD5加密---MD5(String plainText) <br>
 * 
 * <p> 校验相关 : <br>
 * 2 : 正则匹配是否手机号---isPhone(String phone) <br>
 * 3 : 正则匹配是否邮箱号---isEmail(String email) <br>
 * 4 : 验证字符串长度---isStrSize(String str, int size) <br>
 * 5 : 判断 密码是否 合法---verify(String passwordLowerCase, int limitCount)<br>
 * 
 * <p> 时间相关 : <br>
 * 6 : 根据毫秒返回具体时间---MillToHourAndMin(String lo)<br>
 * 7 : 根据毫秒返回时间 大于7天返回具体时间、大于1天返回几天、小于一天返回小时---MillToTime(String lo)<br>
 * 8 : 判断是否超过指定分钟---differTime(String strTime, int min)<br>
 * 9 : 获取当前时间 格式：yyyyMMdd---CurrentyyyymmddTime()<br>
 * 10 : 获取当前时间 格式：yyyy年MM月dd日 HH:mm:ss---CurrentYMDHSMTime()<br>
 * 11 : 求两个日期相差的天数---differentDaysByMillisecond(String strDate, String strDate2)<br>
 * 
 * @author 梦伴
 *
 */
public class Tools {

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
	 * @desc MD5加密
	 * @author 梦伴
	 * @param plainText
	 * @return 加密值
	 */
	public static String MD5(String plainText) {
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
	private static boolean containTypesVerify(String password) {
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
		String s1 = containTargetStrVerify(passwordLowerCase);
		if (s1 != null) {
			return "密码包含连续的斜向键盘输入或者常用的数据库、操作系统等词组！内容为：" + s1 + "。";
		}
		String s2 = containTargetSequenceVerify(passwordLowerCase, limitCount);
		if (s2 != null) {
			return "密码包含连续的横向键盘输入或者连续的数字和字母或者重复的字符输入！";
		}
		return null;
	}

	/**
	 * @desc 求两个日期相差的天数
	 * @param strDate yyyy-MM-dd
	 * @param strDate2 yyyy-MM-dd
	 * @return
	 * @throws ParseException
	 */
	public static int differentDaysByMillisecond(String strDate, String strDate2) throws ParseException {
		// 1.初始化日期格式化类
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 2.将传入的字符串类型的日期转变为日期类型
		Date fDate = format.parse(strDate);
		Date oDate = format.parse(strDate2);
		// 3.初始化日历工具类
		Calendar aCalendar = Calendar.getInstance();
		// 4.将上面获取到的日期转变为天
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;
	}

	/**
	 * 获取当前时间 格式：yyyy年MM月dd日 HH:mm:ss
	 * 
	 * @return
	 */
	public static final String CurrentYMDHSMTime() {
		String curTime = "";
		// 格式化时间开始
		SimpleDateFormat formatter;
		java.util.Date currentDate = new java.util.Date();
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		currentDate = Calendar.getInstance().getTime();
		// 格式化时间结束
		curTime = formatter.format(currentDate);
		return curTime;
	}

	/**
	 * 获取当前时间 格式：yyyyMMdd
	 * 
	 * @return
	 */
	public static final String CurrentyyyymmddTime() {
		String curTime = "";
		// 格式化时间开始
		SimpleDateFormat formatter;
		java.util.Date currentDate = new java.util.Date();
		formatter = new SimpleDateFormat("yyyyMMdd");
		currentDate = Calendar.getInstance().getTime();
		// 格式化时间结束
		curTime = formatter.format(currentDate);
		return curTime;
	}

	/**
	 * 判断是否超过指定分钟
	 * 
	 * @param strTime yyyy-MM-dd HH:mm:ss 开始时间
	 * @param min     指定分钟数
	 * @return 返回1：超过 返回0：没超过
	 * @throws ParseException
	 */
	public static int differTime(String strTime, int min) throws ParseException {
		// 初始化日期格式
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 字符串变为日期类型
		Date date = format.parse(strTime);
		// 如果时间不超过5分钟
		if (new Date().getTime() - date.getTime() < (min * 60 * 1000)) {
			return 0;
		}
		// 超过
		return 1;
	}

	/**
	 * 根据毫秒返回时间 大于7天返回具体时间、大于1天返回几天、小于一天返回小时
	 * 
	 * @param lo 时间戳
	 * @return
	 */
	public static String MillToTime(String lo) {
		long time = Long.parseLong(lo);
		// 大于7天
		if (System.currentTimeMillis() - time > 604800000) {
			Date date = new Date(time);
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			return sd.format(date);
		}
		// 大于1天
		else if (System.currentTimeMillis() - time > 86400000) {
			return Math.abs(time / 1000 / 60 / 60 / 24) + "天前";
		}
		// 小于一天
		else if (System.currentTimeMillis() - time > 3600000) {
			return Math.abs((System.currentTimeMillis() - time) / 1000 / 60 / 60) + "小时前";
		}
		return "不到1小时前";

	}

	/**
	 * 根据毫秒返回具体时间
	 * 
	 * @param lo 时间戳
	 * @return
	 */
	public static String MillToHourAndMin(String lo) {
		long time = Long.parseLong(lo);
		Date date = new Date(time);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sd.format(date);
	}
	public static void main(String[] args) {
		System.out.println(MD5("wushang816"));
		System.out.println(MD5("wushang816"));
		System.out.println(MD5("wushang816"));

	}
	
}
