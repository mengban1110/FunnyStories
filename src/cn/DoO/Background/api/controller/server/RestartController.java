package cn.DoO.Background.api.controller.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.server.TomcatRestartServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 重启tomcat
 * 
 * @author TianShuo
 * 
 * @version 2021年3月7日 下午12:29:52
 */
@SuppressWarnings("serial")
@WebServlet("/api/server/restart")
public class RestartController extends HttpServlet {
	
	TomcatRestartServlet tomcatRestartServlet = new TomcatRestartServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			tomcatRestartServlet.Restart(request, response);
		} catch (Exception e) {
			out.print(NetCodeUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
