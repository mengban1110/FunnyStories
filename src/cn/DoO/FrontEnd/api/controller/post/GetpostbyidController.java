package cn.DoO.FrontEnd.api.controller.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.post.GetpostbyidServlet;

/**
 * 获取指定帖子数据(内有评论)
 * @author LYP
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/getpostbyid")
public class GetpostbyidController extends HttpServlet{
	
	GetpostbyidServlet get = new GetpostbyidServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		get.getPostbyId(request,response);
	}
}
