package cn.DoO.FrontEnd.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.user.ActivateServlet;

/**
 * 激活（验证验证码）
 * @author LYP
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/activate")
public class ActivateController extends HttpServlet{
	
	ActivateServlet active = new ActivateServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		active.activeCode(request,response);
	}
}
