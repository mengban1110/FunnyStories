package cn.DoO.FrontEnd.api.controller.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.post.LikethisServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
* @desc 点赞指定帖子
* 
* @author TianShuo
* 
* @version 2021年4月1日 下午7:17:23
*/
@SuppressWarnings("serial")
@WebServlet("/api/post/likethis")
public class LikethisController extends HttpServlet{
	LikethisServlet likethisServlet = new LikethisServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			likethisServlet.likethis(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());
		}
	}
}
