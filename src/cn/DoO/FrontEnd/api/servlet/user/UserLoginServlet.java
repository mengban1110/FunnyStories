package cn.DoO.FrontEnd.api.servlet.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.status.StatusDao;
import cn.DoO.Background.api.dao.user.UserDao;
import cn.DoO.Utils.Token.TokenUtils;
import cn.DoO.Utils.Tools.Md5Utils;

/**
 * 1.3 : 登录
 * @author 孙雨桐
 * @Time 2021年3月18日21点26分
 */
public class UserLoginServlet {
	
	StatusDao statusDao = new StatusDao();
	UserDao userDao = new UserDao();
	
	public void userLogin(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		
		// json对象
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		
		// 接值
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username);
		System.out.println(password);
		if (username == null || "".equals(username)) {
			jsonObject.put("code", -3);
			jsonObject.put("msg", "请输入正确的账号");
			writer.write(jsonObject.toJSONString());
			return;
		}
		
		if (password == null || "".equals(password)) {
			jsonObject.put("code", -4);
			jsonObject.put("msg", "请输入正确的密码");
			writer.write(jsonObject.toJSONString());
			return;
		}
		
		if (!((Integer)(statusDao.query().get("login")) == 1)) {
			jsonObject.put("code", 1);
			jsonObject.put("msg", "网站暂时关闭登录功能");
			writer.write(jsonObject.toJSONString());
			return;
		}
		
		
		
		Map<String, Object> user = userDao.findByUnameOrEmail(username);
		if (user == null) {
			jsonObject.put("code", -3);
			jsonObject.put("msg", "请输入正确的账号");
			writer.write(jsonObject.toJSONString());
			return;
		}
		String pwd = (String) user.get("password");
		if (!pwd.equals(Md5Utils.makeMd5(password))) {
			jsonObject.put("code", -4);
			jsonObject.put("msg", "请输入正确的密码");
			writer.write(jsonObject.toJSONString());
			return;
		}
		//验证黑名单
		Map<String, Object> blackName = userDao.queryUserByBlack(user.get("uid").toString());
		if (blackName != null) {
			jsonObject.put("code", -5);
			jsonObject.put("msg", "账户已被封禁");
			writer.write(jsonObject.toJSONString());
			return;
		}
		
		if ((Integer)(user.get("userstatus")) != 1) {
			jsonObject.put("code", -5);
			jsonObject.put("msg", "账户已被封禁");
			writer.write(jsonObject.toJSONString());
			return;
		}
		String token = TokenUtils.getToken((String)user.get("email"));
		userDao.updateTokenByid((Integer)user.get("uid"),token);
		
		jsonObject.put("code", 200);
		jsonObject.put("msg", "登录成功");
		jsonObject.put("username", user.get("username"));
		jsonObject.put("token", token);
		jsonObject.put("useravatar", user.get("useravatar"));
		jsonObject.put("uid", user.get("uid"));
		writer.write(jsonObject.toJSONString());

	}
	
	
}
