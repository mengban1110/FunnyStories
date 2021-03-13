package cn.DoO.Background.api.controller.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.user.GetUserCountServlet;


@WebServlet("/api/user/getcount")
public class GetUserCount extends HttpServlet{

	GetUserCountServlet getUserCountServlet = new GetUserCountServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			getUserCountServlet.getUserCount(request, response);
		} catch (Exception e) {
			
		}
	}

	
}
