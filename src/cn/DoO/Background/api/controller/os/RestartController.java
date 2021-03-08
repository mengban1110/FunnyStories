package cn.DoO.Background.api.controller.os;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.os.RestartServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 重启服务器
 * 
 * @author TianShuo
 * 
 * @version 2021年3月7日 下午12:05:52
 */
@SuppressWarnings("serial")
@WebServlet("/api/os/restart")
public class RestartController extends HttpServlet {
	
	RestartServlet restartServlet = new RestartServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			restartServlet.Restart(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(NetCodeUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
