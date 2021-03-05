package cn.DoO.Background.api.controller.homepage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.homepage.InfoServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 
 * @desc 获取文本数据
 * 
 * @author TianShuo
 * 
 * @version 2021年3月4日 下午5:16:36
 */

@SuppressWarnings("serial")
@WebServlet("/api/home/getinfo")
public class GetinfoController extends HttpServlet {

	InfoServlet info = new InfoServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		try {
			info.getTextInfo(request, response);
		} catch (Exception e) {
			out.print(NetCodeUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
