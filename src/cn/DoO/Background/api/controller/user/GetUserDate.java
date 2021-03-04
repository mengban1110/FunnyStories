package cn.DoO.Background.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.user.GetUserDateServlet;

@WebServlet("/api/user/getdata")
public class GetUserDate extends HttpServlet{

	GetUserDateServlet getUserDateServlet = new GetUserDateServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getUserDateServlet.getUserDate(request, response);
	}
}
