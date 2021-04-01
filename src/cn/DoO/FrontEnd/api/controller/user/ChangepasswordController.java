package cn.DoO.FrontEnd.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.user.ChangepasswordServlet;

/**
 * 修改密码
 * @author LYP
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/changepassword")
public class ChangepasswordController extends HttpServlet{
	
	ChangepasswordServlet changepwd = new ChangepasswordServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		changepwd.changePWD(request,response);
	}
}
