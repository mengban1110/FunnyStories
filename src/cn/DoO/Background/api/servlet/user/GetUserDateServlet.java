package cn.DoO.Background.api.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.user.UserDao;
import cn.DoO.Utils.Dao.Token.TokenDao;

/**
 * 获取用户指定数据
 * 
 * @author 孙雨桐
 *
 * @Time 2021年3月4日19点16分
 */
public class GetUserDateServlet {

	TokenDao tokenDao = new TokenDao();
	UserDao userDao = new UserDao();
	
	public void getUserDate(HttpServletRequest request, HttpServletResponse response) {
		
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		
		// 接值
		String token = request.getParameter("token");
		String uid = request.getParameter("uid");
		
		//判断参数
		try {
			if (token == null ||token.length() == 0) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}
			if (uid == null || uid.length() == 0) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-2");
				jsonObject.put("msg", "非法调用");
				writer.write(jsonObject.toString());
				return;
			}
			if (tokenDao.queryUserByToken(token)==null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-2");
				jsonObject.put("msg", "非法调用");
				writer.write(jsonObject.toString());
				return;
			}
			
			//存值
			Map<String, Object> map = userDao.findUserById(uid);
			Map<String, Object>user = new HashMap<String, Object>();
			
			user.put("userid", map.get("uid"));
			user.put("username", map.get("username"));
			user.put("useravatar", map.get("useravatar"));
			user.put("userbir", map.get("userbirth"));
			user.put("email", map.get("email"));
			user.put("usersign", map.get("usersign"));
			
			
			jsonObject.put("code", 200);
			jsonObject.put("msg", "获取成功");
			jsonObject.put("data", user);
			writer.write(jsonObject.toJSONString());
			return;
		}catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("code", "-2");
			jsonObject.put("msg", "非法调用");
			writer.write(jsonObject.toJSONString());
			return;
		}
		
	}
}
