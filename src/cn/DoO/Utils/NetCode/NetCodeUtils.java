package cn.DoO.Utils.NetCode;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * _常见code及msg返回 工具类
 * 
 * @author 梦伴
 *
 */
public class NetCodeUtils {
	/**
	 * @Desc 非法调用
	 * @return
	 */
	public static String ErrorParam() {
		Map<String, Object> data = new HashMap<>();
		data.put("code", "-2");
		data.put("msg", "非法调用");
		return JSON.toJSONString(data);
	}

	/**
	 * @Desc 未登录
	 * @return
	 */
	public static String isToken() {
		Map<String, Object> data = new HashMap<>();
		data.put("code", "-1");
		data.put("msg", "未登录");
		return JSON.toJSONString(data);
	}

	/**
	 * @Desc 请求成功
	 * @return
	 */
	public static String successful() {
		Map<String, Object> data = new HashMap<>();
		data.put("code", "200");
		data.put("msg", "请求成功");
		return JSON.toJSONString(data);
	}

	/**
	 * @Desc 服务器内部错误
	 * @return
	 */
	public static String errorTomCat() {
		Map<String, Object> data = new HashMap<>();
		data.put("code", "500");
		data.put("msg", "服务器内部错误");
		return JSON.toJSONString(data);
	}
	
	/**
	 * @Desc 用户不存在
	 * @return
	 */
	public static String userIsNo() {
		Map<String, Object> data = new HashMap<>();
		data.put("code", "-3");
		data.put("msg", "不存在");
		return JSON.toJSONString(data);
	}
}
