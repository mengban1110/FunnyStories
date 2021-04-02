package cn.DoO.FrontEnd.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.user.RegisterServlet;

/**
 * 注册
 * @author JZH
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/register")
public class RegisterController extends HttpServlet{
	
	RegisterServlet reg = new RegisterServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reg.zhuCe(request,response);
	}
}
