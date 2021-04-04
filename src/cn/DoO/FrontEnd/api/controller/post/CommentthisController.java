package cn.DoO.FrontEnd.api.controller.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.post.CommentthisServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
* @desc 评论
* 
* @author TianShuo
* 
* @version 2021年4月1日 下午7:51:17
*/
@SuppressWarnings("serial")
@WebServlet("/api/post/commentthis")
public class CommentthisController extends HttpServlet{
	CommentthisServlet commentthisServlet = new CommentthisServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			commentthisServlet.commentthis(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());
		}
	}
}
