package cn.DoO.Background.api.controller.post.recommend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.recommend.DelreCommendServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 取消推荐
 * @author 云尧
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/checked/delrecommend")
public class DelreCommendController extends HttpServlet {

	DelreCommendServlet delreCommendServlet = new DelreCommendServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			delreCommendServlet.delreCommend(request, response);
		} catch (Exception e) {
			out.print(NetCodeUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
