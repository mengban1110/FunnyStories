package cn.DoO.Background.api.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.user.UserDao;
import cn.DoO.Utils.Dao.Token.TokenDao;

/**
 * 获取部分用户
 * 
 * @author 孙雨桐
 * 
 * @Time 2021年3月4日19点15分
 *
 */
public class GetUserInfoPartServlet {
	
	TokenDao tokenDao = new TokenDao();
	UserDao userDao = new UserDao();
	
	public void getUserInfoPart(HttpServletRequest request, HttpServletResponse response) {
		
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
		String pagestr = request.getParameter("page");
		String sizestr = request.getParameter("size");
		String world = request.getParameter("world");
		
		int page = 0;
		int size = 0;
		
		try {
			if (token == null ||token.length() == 0) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;
			}
			if (world == null || world.length() == 0) {
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
			
			//判断page 和 size 是否为空
			if (pagestr == null) {
				pagestr = "1";
				page = Integer.parseInt(pagestr);
			}else{
				page = Integer.parseInt(pagestr);
			}
			if (sizestr == null) {
				sizestr = "20";
				size = Integer.parseInt(sizestr);
			}else{
				size = Integer.parseInt(sizestr);
			}
			
			
			List<Map<String, Object>> userList = userDao.findUserBypageAndSizeAndLike(page,size,world);
			
			//返回数据
			List<Map<String, Object>> users = new ArrayList<Map<String,Object>>();
			
			for (Map<String, Object> map : userList) {
				Map<String, Object> user = new HashMap<String,Object>();
				user.put("userid", map.get("uid"));
				user.put("username", map.get("username"));
				user.put("useravatar", map.get("useravatar"));
				user.put("userbir", map.get("userbirth"));
				user.put("email", map.get("email"));
				user.put("usersign", map.get("usersign"));
				users.add(user);
			}
			
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("users", users);
			
			
			jsonObject.put("code", 200);
			jsonObject.put("msg", "获取成功");
			jsonObject.put("data", jsonObject2);
			writer.write(jsonObject.toJSONString());
			return;
			}
		catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("code", "-2");
			jsonObject.put("msg", "非法调用");
			writer.write(jsonObject.toJSONString());
			return;
		}
		
	}

}
