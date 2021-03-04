package cn.DoO.Background.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.user.GetUserInfoServlet;

@WebServlet("/api/user/getinfopart")
public class Getinfopart extends HttpServlet{

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetUserInfoServlet getUserInfoServlet = new GetUserInfoServlet();
		getUserInfoServlet.getUserInfo(request, response);
	}

}
