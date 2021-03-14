package cn.DoO.Background.api.controller.post.checking;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.checking.GetInfopartPostCountServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
* @desc 部分待审核帖子数量
* 
* @author TianShuo
* 
* @version 2021年3月14日 下午11:17:36
*/
@SuppressWarnings("serial")
@WebServlet("/api/post/checking/getInfopartpostCount")
public class GetInfopartCheckingPostCount extends HttpServlet{
	GetInfopartPostCountServlet getInfopartPostCountServlet = new GetInfopartPostCountServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			getInfopartPostCountServlet.getPostCount(request, response);
		} catch (Exception e) {
			out.print(NetCodeUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
