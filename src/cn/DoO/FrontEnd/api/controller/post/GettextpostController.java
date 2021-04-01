package cn.DoO.FrontEnd.api.controller.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.post.GettextpostServlet;

/**
 * 获取文本帖子
 * @author LYP
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/gettextpost")
public class GettextpostController extends HttpServlet{
	
	GettextpostServlet getTextPost = new GettextpostServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getTextPost.getTextPost(request,response);
	}
}
