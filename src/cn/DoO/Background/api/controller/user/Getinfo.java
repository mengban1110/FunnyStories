package cn.DoO.Background.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.servlet.user.GetUserInfoServlet;

/**
 * 获取用户
 * 
 * @author 孙雨桐
 * 
 * @Time 2021年3月4日21点52分
 */
@WebServlet("/api/user/getinfo")
public class Getinfo extends HttpServlet{

	GetUserInfoServlet getUserInfoServlet = new GetUserInfoServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			getUserInfoServlet.getUserInfo(request, response);
			
		} catch (Exception e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", "500");
			jsonObject.put("msg", "服务器内部错误");
			response.getWriter().write(jsonObject.toJSONString());
		}
	}

}
