package cn.DoO.Background.api.controller.homepage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.homepage.GetPostPartCountServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 各个板块帖子数
 * 
 * @author TianShuo
 * 
 * @version 2021年3月5日 下午3:44:57
 */

@SuppressWarnings("serial")
@WebServlet("/api/home/getpostpartcount")
public class GetPostPartCount extends HttpServlet {

	GetPostPartCountServlet getPostPartCountServlet = new GetPostPartCountServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		try {
			getPostPartCountServlet.getPostPartCount(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(NetCodeUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
