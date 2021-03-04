package cn.DoO.Background.api.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.user.UserDao;
import cn.DoO.Utils.Dao.Token.TokenDao;

/**
 * 修改用户指定数据
 * 
 * @author 孙雨桐
 * 
 * @Time 2021年3月4日19点37分
 */
public class PostUserDataServlet {
	
	TokenDao tokenDao = new TokenDao();
	UserDao userDao = new UserDao();
	
	public void postUserData(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		
		// json对象
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		
		// 接值
		String token = request.getParameter("token");
		String uid = request.getParameter("uid");
		String username = request.getParameter("username");
		String useravatar = request.getParameter("useravatar");
		String usersex = request.getParameter("usersex");
		String userbir = request.getParameter("userbir");
		String usersign = request.getParameter("usersign");
		String userstatus = request.getParameter("userstatus");
		String password = request.getParameter("password");
		
		Map<String, Object> user = null;
		try {
			if (token ==null || "".equals(token) || tokenDao.queryRootByToken(token)==null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}
			if (tokenDao.queryRootByToken(token)==null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-2");
				jsonObject.put("msg", "非法调用");
				writer.write(jsonObject.toString());
				return;
			}
			//判断值是否为空
			if (uid == null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-2");
				jsonObject.put("msg", "非法调用");
				writer.write(jsonObject.toString());
				return;
			}else if(userDao.findUserById(uid) == null){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-3");
				jsonObject.put("msg", "没有此用户");
				writer.write(jsonObject.toString());
				return;
			}else {
				user = userDao.findUserById(uid);
			}
			
			if (username == null || username.equals("")) {
				username = user.get("username").toString();
			}
			if (useravatar == null || useravatar.equals("")) {
				useravatar = user.get("useravatar").toString();
			}
			if (usersex == null || usersex.equals("")) {
				usersex = user.get("usersex").toString();
			}
			if (userbir == null || userbir.equals("")) {
				userbir = user.get("userbirth").toString();
			}
			if (usersign == null || username.equals("")) {
				usersign = user.get("usersign").toString();
			}
			if (userstatus == null || username.equals("")) {
				userstatus = user.get("userstatus").toString();
			}
			if (password == null || username.equals("")) {
				password = user.get("password").toString();
			}
			
			userDao.update(uid,username,useravatar,usersex,userbir,usersign,userstatus,password);
			
			jsonObject.put("code", "200");
			jsonObject.put("msg", "修改成功调用");
			writer.write(jsonObject.toJSONString());
			
		}catch (Exception e) {
			jsonObject = new JSONObject();
			jsonObject.put("code", "-2");
			jsonObject.put("msg", "非法调用");
			writer.write(jsonObject.toJSONString());
			return;
		}
		
	}

}
