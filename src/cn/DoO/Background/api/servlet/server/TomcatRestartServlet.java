package cn.DoO.Background.api.servlet.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.DoO.Background.api.dao.homepage.RootDaoImpl;
import cn.DoO.Background.api.dao.post.checking.CheckingPostDaolmpl;
import cn.DoO.Utils.NetCode.NetCodeUtils;
import cn.DoO.Utils.Tools.DateUtils;
import cn.DoO.Utils.Tools.IPUtils;

/**
 * @desc 重启Tomcat
 * 
 * @author TianShuo
 * 
 * @version 2021年3月7日 下午12:28:39
 */
public class TomcatRestartServlet {

	// 管理员用户的dao
	RootDaoImpl rootDaoImpl = new RootDaoImpl();
	// 待审核帖子的dao
	CheckingPostDaolmpl checkingPostDaolmpl = new CheckingPostDaolmpl();

	public void Restart(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		PrintWriter out = response.getWriter();

		if (!"GET".equals(request.getMethod())) {
			out.write(NetCodeUtils.otherErrMsg("-404", "请求方式有误"));//请求方式错误
			return;
		}
		// 获取token
		String token = request.getParameter("token");


		Map<String, Object> data = new HashMap<String, Object>();

		// 如果token为空显示
		if (token == null || token.equals("")) {
			print(out, data, "-2", "非法调用");
			return;
		}

		// 获取管理员用户的Map
		Map<String, Object> rootMap = rootDaoImpl.getUserByToken(token);

		// 获取结果为空 提交的token于查询后的token不一致将返回未登录
		if (rootMap == null || rootMap.get("token").equals(token) == false) {
			print(out, data, "-1", "未登录");
			return;
		}
		// 如果token正确
		if (rootMap.get("token").equals(token))

		{
			// 将此条重启记录写到后台记录中
			int rootid = (int) rootMap.get("rootid");// 管理员ID
			String ip = IPUtils.getClientIpAddr(request);// Ip
			checkingPostDaolmpl.addLog(rootid, DateUtils.getSecondTimestamp(new Date()), "重启Tomcat", ip, "8");// 写入

			// 进行响应
			data.put("code", "200");
			data.put("msg", "请求成功");
			out.print(JSON.toJSONString(data));

			// 执行重启过程
			ResourceBundle myResources = ResourceBundle.getBundle("cn.DoO.Config.TomcatPath");
			String exec = myResources.getString("batPath");
			Runtime runtime = Runtime.getRuntime();
			runtime.exec("cmd /c start " + exec);

		}

	}

	public void print(PrintWriter out, Map<String, Object> data, String coed, String msg) {
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		System.out.println(JSON.toJSONString(data));
		out.close();
	}

}
