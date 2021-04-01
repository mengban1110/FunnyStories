package cn.DoO.FrontEnd.api.controller.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.post.GetrecommendServlet;

/**
 * 推荐帖子
 * @author LYP
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/getrecommend")
public class GetrecommendController extends HttpServlet{
	
	GetrecommendServlet getrecommend = new GetrecommendServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getrecommend.getTuiJian(request,response);
	}
}
