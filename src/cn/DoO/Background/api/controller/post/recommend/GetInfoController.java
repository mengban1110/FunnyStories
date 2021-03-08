package cn.DoO.Background.api.controller.post.recommend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.recommend.GetInfoServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 获取推荐的全部帖子
 * @author 云尧
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/checked/getinfo")
public class GetInfoController extends HttpServlet {

	GetInfoServlet gServlet = new GetInfoServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			gServlet.getInfo(request, response);
		} catch (Exception e) {
			out.print(NetCodeUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
