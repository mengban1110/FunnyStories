package cn.DoO.Utils.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author LYP
 *
 */
public class DateUtil {
	/**
	 * 毫秒转换具体时间
	 * @param mill
	 * @return
	 */
	public static String getHaoMiaoToShiJian(String mill){
		  Date date = new Date();
	      date.setTime(Long.parseLong(mill));
	      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      String fmDate=simpleDateFormat.format(date);
	      System.err.println(fmDate);
	      return fmDate;
	}
	
	
}
