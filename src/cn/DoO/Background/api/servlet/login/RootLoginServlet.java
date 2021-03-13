package cn.DoO.Background.api.servlet.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.DoO.Background.api.dao.login.loginDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;
import cn.DoO.Utils.Token.TokenUtils;
import cn.DoO.Utils.Tools.Md5Utils;

/**
 * @desc 登陆验证
 * @author 齐云尧
 * 
 */

public class RootLoginServlet {
	loginDao loginDao = new loginDao();
	Md5Utils md5 = new Md5Utils();
	

	/**
	 * @desc 登陆
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "static-access" })
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		PrintWriter out = response.getWriter();
		Map<String, Object> dataP = new HashMap<String, Object>();
		
		
		if (!"POST".equals(request.getMethod())) {
			out.write(NetCodeUtils.otherErrMsg("-404", "请求方式有误"));//请求方式错误
			return;
		}
		
		// 获取管理员名称和密码
		String rootName = request.getParameter("rootname");
		String rootpassWord = request.getParameter("rootpassword");
		System.out.println("rootname:"+rootName);
		System.out.println("rootpassword:"+rootpassWord);
		// 判断管理员名称和密码是否为空
		if (rootName == null || "".equals(rootName)) {
			print(out, dataP, "-3", "请输入正确的账号");
			return;
		}
		if (rootpassWord == null || "".equals(rootpassWord)) {
			print(out, dataP, "-4", "请输入正确的密码");
			return;
		}

		// 查询是否存在该用户
		Map<String, Object> root = loginDao.getpasswordByU(rootName);
		
		if (root == null) {
			print(out, dataP, "-3", "请输入正确的账号");
			return;
		}
		// md5加密
		String newrootPassWord = md5.makeMd5(rootpassWord);
		System.out.println(newrootPassWord);
		// 判断密码是否正确
		
		String oldrootpassword = root.get("rootpassword") + "";
		System.out.println(oldrootpassword);
		if (!oldrootpassword.equals(newrootPassWord)) {
			print(out, dataP, "-4", "请输入正确的密码");
			return;
		}
		
		String rootid = root.get("rootid")+"";
		String oldtoken = root.get("token") + "";
		String rootname = root.get("rootname") + "";
		String rootavatar = root.get("rootavatar") + "";
		
		String updateToken = TokenUtils.updateToken(oldtoken, rootname);
		Map<String, Object> mapzz = new HashMap<String, Object>();
		mapzz.put("managername", rootname);
		mapzz.put("manageravatar", rootavatar);
		mapzz.put("token", updateToken);
		dataP.put("data", mapzz);
		print(out, dataP, "200", "登陆成功");
		
		//添加到登陆记录
		loginDao.addRootLogin(rootid,request);
		
		return;

	}

	public void print(PrintWriter out, Map<String, Object> data, String coed, String msg) {
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));

		out.close();
	}

}
