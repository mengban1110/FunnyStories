package cn.DoO.Background.api.servlet.post.checking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.DoO.Background.api.dao.homepage.RootDaoImpl;
import cn.DoO.Background.api.dao.post.checking.CheckingPostDaolmpl;
import cn.DoO.Utils.Tools.IPUtils;

/**
 * @desc 审核指定帖子
 * 
 * @author TianShuo
 * 
 * @version 2021年3月6日 下午11:12:50
 */
public class AuditpostServlet {

	// 管理员用户的dao
	RootDaoImpl rootDaoImpl = new RootDaoImpl();

	// 待审核帖子的dao
	CheckingPostDaolmpl checkingPostDaolmpl = new CheckingPostDaolmpl();

	public void auditPost(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		// 获取token
		String token = request.getParameter("token");
		// 帖子id
		String postid = request.getParameter("postid");
		System.out.println(postid);
		// 审核状态 (1/审核通过,0审核未通过,未通过则直接delete帖子 直接删除!!!!)
		String audit = request.getParameter("audit");

		PrintWriter out = response.getWriter();

		Map<String, Object> data = new HashMap<String, Object>();

		// 如果token为空显示 参数无 audit不是1和0
		if (token == null || token.equals("") || postid == null || audit == null
				|| (audit.equals("0") == false && audit.equals("1") == false)) {
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

		// 查询是否有这个帖子 并且 此帖子是否需要审核
		if (checkingPostDaolmpl.getOnePost(postid) == null) {
			print(out, data, "-2", "非法调用");
			return;
		}

		int rootid = (int) rootMap.get("rootid");// 管理员ID

		String ip = IPUtils.getClientIpAddr(request);// Ip
		// 如果帖子通过审核
		if (audit.equals("1")) {
			checkingPostDaolmpl.upateAudit(postid);
			checkingPostDaolmpl.addLog(rootid, System.currentTimeMillis(), "【审核通过】id为:" + postid + "的帖子", ip, "4");
			print(out, data, "200", "请求成功");
			return;
		}
		// 如果帖子不通过 则删除
		else if (audit.equals("0")) {
			checkingPostDaolmpl.delPost(postid);
			checkingPostDaolmpl.addLog(rootid, System.currentTimeMillis(), "【未审核通过】id为:" + postid + "的帖子 【已删除】", ip,
					"6");
			print(out, data, "200", "请求成功");
			return;
		} else {
			print(out, data, "-2", "非法调用");
			return;
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
