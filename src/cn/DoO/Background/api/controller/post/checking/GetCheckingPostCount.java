package cn.DoO.Background.api.controller.post.checking;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.checking.GetCheckingPostCountServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;
/**
 * @desc 待审核帖子数量
 * 
 * @author TianShuo
 * 
 * @Time 2021年3月13日 22点54分
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/checking/getpostCount")
public class GetCheckingPostCount extends HttpServlet{
	GetCheckingPostCountServlet  getCheckingPostCountServlet = new GetCheckingPostCountServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			getCheckingPostCountServlet.getPostCount(request, response);
		} catch (Exception e) {
			out.print(NetCodeUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
