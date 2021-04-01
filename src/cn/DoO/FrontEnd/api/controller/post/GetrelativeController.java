package cn.DoO.FrontEnd.api.controller.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.post.GetrelativeServlet;

/**
 * 获取相关推荐
 * @author LYP
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/getrelative")
public class GetrelativeController extends HttpServlet {
	
	GetrelativeServlet getrela = new GetrelativeServlet();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getrela.getrela(request,response);
	}
}
