package cn.DoO.Background.api.controller.adv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.adv.PostAdverInfoServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 发布广告
 * 
 * @author 孙雨桐
 * 
 * 2021年3月7日11点52分
 */
@WebServlet("/api/adv/postadv")
public class PostAdverInfo extends HttpServlet{
	
	PostAdverInfoServlet postAdverInfoServlet = new PostAdverInfoServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			postAdverInfoServlet.postAdverInfo(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());//服务器内部错误
			return;
		}
		
	}
	
	
}
