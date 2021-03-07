package cn.DoO.Background.api.controller.blacklist;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.blacklist.DeLinfoServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 取消黑名单
 * @author 云尧
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/blacklist/delinfo")
public class DelInfoController extends HttpServlet {

	DeLinfoServlet delInfoServlet = new DeLinfoServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			delInfoServlet.deLinfo(request, response);
		} catch (Exception e) {
			out.print(NetCodeUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
