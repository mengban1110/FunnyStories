package cn.DoO.FrontEnd.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.user.UserLoginServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 1.3 : 登录
 * @author 孙雨桐
 * @Time 2021年3月18日21点26分
 */
@WebServlet("/api/user/login")
public class UserLoginController extends HttpServlet{
	UserLoginServlet userLoginServlet = new UserLoginServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			userLoginServlet.userLogin(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());
		}
	}
}
