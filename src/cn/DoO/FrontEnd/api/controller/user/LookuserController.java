package cn.DoO.FrontEnd.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.user.LookuserServlet;

/**
 * 获取指定用户数据(个人主页)
 * @author JZH
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/lookuser")
public class LookuserController extends HttpServlet{
	
	LookuserServlet lookuser = new LookuserServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		lookuser.lookUser(request,response);
	}
}
