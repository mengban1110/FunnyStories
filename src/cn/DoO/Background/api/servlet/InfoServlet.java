package cn.DoO.Background.api.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.sun.management.OperatingSystemMXBean;

import cn.DoO.Background.api.dao.RootDaoImpl;
import cn.DoO.Utils.Tools.DateUtils;


/**
 * @desc 首页数据之文本数据
 * 
 * @author TianShuo
 * 
 * @version 2021年3月4日 下午5:20:12
 */
public class InfoServlet {

	// 管理员用户的dao
	RootDaoImpl rootDaoImpl = new RootDaoImpl();

	public void getTextInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		// 获取token
		String token = request.getParameter("token");

		PrintWriter out = response.getWriter();

		Map<String, Object> data = new HashMap<String, Object>();

		// 如果token为空显示
		if (token == null || token.equals("")) {
			print(out, data, "-2", "非法调用");
			return;
		}

		// 获取管理员用户的Map
		Map<String, Object> rootMap = rootDaoImpl.getUserByToken(token);

		// 获取结果为空 提交的token于查询后的token不一致将返回未登录
		if (rootMap == null || rootMap.get("token").equals(token) != false) {
			print(out, data, "-1", "未登录");
			return;
		}

		Map<String, Object> dataP = new HashMap<String, Object>();
		
		//管理员信息
		data.put("managername", rootMap.get("username"));//用户名
		data.put("manageravatar", rootMap.get("rootavatar"));//用户头像的直链

		/**
		 * =======================【服务器数据】=======================
		 */
		// 服务器运行时间
		long dateTime = System.currentTimeMillis() - getServerStqrtTime();

		// 服务器内存
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		// 总的物理内存
		long totalvirtualMemory = osmxb.getTotalSwapSpaceSize(); // 单位是字节数，除以1024是K
		// 剩余的内存
		long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
		// 使用的内存
		long compare = totalvirtualMemory - freePhysicalMemorySize;

		data.put("serverruntime", getDate(dateTime) + "小时");
		data.put("servermemory", totalvirtualMemory / 1024 / 1024 + "mb/" + (compare / 1024 / 1204) + "mb");
		// cpu核数
		dataP.put("servercores", Runtime.getRuntime().availableProcessors());
		// cpu线程数

		// 获得线程总数
		ThreadGroup parentThread;
		for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
				.getParent() != null; parentThread = parentThread.getParent()) {

		}

		int totalThread = parentThread.activeCount();
		data.put("serverthreads", totalThread);

		// 服务器操作系统
		data.put("serveros", System.getProperty("os.name"));
		// 获取java的版本
		data.put("serverjavaversion", System.getProperty("java.version"));
		/**
		 * =======================服务器数据【结束】=======================
		 */
		
		
		
		
		/**
		 * ============================countinfo========================
		 */
		Map<String, Object> countinfoDate = new HashMap<String, Object>();
		
		countinfoDate.put("user", rootDaoImpl.getUserCount().get("count"));//用户数
		countinfoDate.put("posts", rootDaoImpl.getPostsCount().get("count"));//共计发帖数
		countinfoDate.put("audits", rootDaoImpl.getAuditsCount().get("count"));//待审核帖子数
		
		data.put("countinfo", countinfoDate);
		/**
		 * =======================countinfo【结束】=======================
		 */
		
		/**
		 * ============================rootlogs========================
		 */
		//时间排序 取最后五个 log表中的数据
		List<Map<String, Object>> rootlogs = new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> log = rootDaoImpl.getAllShop();
		for (Map<String, Object> map : log) {
			//创建返回的数据 的容器
			Map<String, Object>  logMap = new HashMap<String, Object>();
			logMap.put("username",rootDaoImpl.getRootById(map.get("rootid").toString()).get("rootname"));
			logMap.put("ip", map.get("ip"));
			logMap.put("content", map.get("content"));
			logMap.put("time", DateUtils.MillToHourAndMin(map.get("time").toString()));
			rootlogs.add(logMap);
		}
		//添加数据
		data.put("rootlogs", rootlogs);
		
		/**
		 * =======================rootlogs【结束】=======================
		 */
		
		
		/**
		 * ============================userlogs========================
		 */
		List<Map<String, Object>> userlogs = new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> logs = rootDaoImpl.getAllUserlogs();
		for (Map<String, Object> map : logs) {
			//创建返回的数据 的容器
			Map<String, Object>  logMap = new HashMap<String, Object>();
			logMap.put("username",rootDaoImpl.getUserById(map.get("uid").toString()).get("username"));
			logMap.put("ip", map.get("userip"));
			logMap.put("time", DateUtils.MillToHourAndMin(map.get("logintime").toString()));
			userlogs.add(logMap);
		}
		//添加数据
		data.put("userlogs", userlogs);
		
		/**
		 * =======================userlogs【结束】=======================
		 */
		//热词
		List<String> hotwords = new ArrayList<String>();
		//获取5个热词
		List<Map<String, Object>> hots = rootDaoImpl.getAllSearchFrom5();
		for (Map<String, Object> map : hots) 
		{
			hotwords.add(map.get("word").toString());
		}
		
		data.put("hotwords", hotwords);
		
		dataP.put("code", "200");
		dataP.put("msg", "请求成功");
		dataP.put("data", data);
		out.print(JSON.toJSONString(dataP));

	}

	public void print(PrintWriter out, Map<String, Object> data, String coed, String msg) {
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		System.out.println(JSON.toJSONString(data));
		out.close();
	}

	public long getServerStqrtTime() {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return time;
	}

	public long getDate(long time) {
		long days = time / (1000 * 60 * 60);
		return days;
	}

}
