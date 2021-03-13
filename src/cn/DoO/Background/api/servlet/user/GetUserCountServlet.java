package cn.DoO.Background.api.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.user.UserDao;

public class GetUserCountServlet {

	
	UserDao userDao = new UserDao();
	public void getUserCount(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// json对象
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		
		
		int count = userDao.getcount();
		jsonObject.put("data", count);
		writer.write(jsonObject.toJSONString());
	}
}
