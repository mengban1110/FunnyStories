package cn.DoO.FrontEnd.api.controller.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.post.GetvideopostServlet;

/**
 * 获取视频板块
 * @author LYP
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/getvideopost")
public class GetvideopostController extends HttpServlet{
	
	GetvideopostServlet getVideoPost = new GetvideopostServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getVideoPost.getVideoPost(request,response);
	}
}
