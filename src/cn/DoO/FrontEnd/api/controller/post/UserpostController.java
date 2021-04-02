package cn.DoO.FrontEnd.api.controller.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.post.UserpostServlet;

/**
 * 发帖
 * @author JZH
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/userpost")
public class UserpostController extends HttpServlet{
	
	UserpostServlet post = new UserpostServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		post.sendPost(request,response);
	}
}
