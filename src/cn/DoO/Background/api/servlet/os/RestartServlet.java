package cn.DoO.Background.api.servlet.os;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.DoO.Background.api.dao.homepage.RootDaoImpl;
import cn.DoO.Background.api.dao.post.checking.CheckingPostDaolmpl;
import cn.DoO.Utils.NetCode.NetCodeUtils;
import cn.DoO.Utils.Tools.DateUtils;
import cn.DoO.Utils.Tools.IPUtils;

/**
 * @desc 重启服务器
 * 
 * @author TianShuo
 * 
 * @version 2021年3月7日 下午12:09:44
 */
public class RestartServlet {
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
			checkingPostDaolmpl.addLog(rootid, DateUtils.getSecondTimestamp(new Date()), "重启服务器", ip, "8");// 写入

			String strcmd = "shutdown -r -t 0"; // 重启服务器
			String result = run_cmd(strcmd);
			if (result.equals("重启成功")) {
				data.put("code", "200");
				data.put("msg", "请求成功");
			} else {
				data.put("code", "200");
				data.put("msg", "启动失败");
			}
			out.print(JSON.toJSONString(data));
		}

	}

	/**
	 * @desc 运行cmd
	 * @param strcmd
	 * @return
	 */
	private String run_cmd(String strcmd) {

		Runtime rt = Runtime.getRuntime(); // Runtime.getRuntime()返回当前应用程序的Runtime对象
		Process ps = null; // Process可以控制该子进程的执行或获取该子进程的信息。

		try {
			ps = rt.exec(strcmd); // 该对象的exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
			ps.waitFor();
		} catch (InterruptedException | IOException e) {
			return "执行失败";
		} // 等待子进程完成再往下执行。
		int i = ps.exitValue(); // 接收执行完毕的返回值
		if (i == 0) {
			ps.destroy(); // 销毁子进程
			ps = null;
			return "重启成功";

		} else {
			ps.destroy(); // 销毁子进程
			ps = null;
			return "执行失败";
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
