package cn.DoO.Background.api.controller.post.checked;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.checked.GetPostcommentCountServlet;

@WebServlet("/api/post/checked/getpostCommentCount")
public class GetPostCommentCount extends HttpServlet{

	GetPostcommentCountServlet getPostcommentCountServlet = new GetPostcommentCountServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			getPostcommentCountServlet.getPostcommentcount(request, response);
		} catch (Exception e) {
			
		}
	}
	
	
}
