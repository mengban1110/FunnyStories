package cn.DoO.FrontEnd.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.user.RecallemailServlet;

/**
 * 找回密码(发送邮件)
 * @author JZH
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/recallemail")
public class RecallemailController extends HttpServlet{
	
	RecallemailServlet recall = new RecallemailServlet(); 
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		recall.recall(request,response);
	}
}
