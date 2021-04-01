package cn.DoO.FrontEnd.api.controller.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.post.GetphotopostServlet;

/**
 * 获取图片帖子
 * @author LYP
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/getphotopost")
public class GetphotopostController extends HttpServlet{
	
	GetphotopostServlet getPhotoPost = new GetphotopostServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getPhotoPost.getPhotoPost(request,response);
	}
}
