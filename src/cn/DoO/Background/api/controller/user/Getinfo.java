package cn.DoO.Background.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.user.GetUserInfoServlet;

@WebServlet("/api/user/getinfo")
public class Getinfo extends HttpServlet{

	GetUserInfoServlet getUserInfoServlet = new GetUserInfoServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getUserInfoServlet.getUserInfo(request, response);
	}

}
