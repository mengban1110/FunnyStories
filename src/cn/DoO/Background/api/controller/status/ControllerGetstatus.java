package cn.DoO.Background.api.controller.status;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.status.ControllerGetstatusServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 控制状态
 * @author 云尧
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/controller/status/controllergetstatus")
public class ControllerGetstatus extends HttpServlet {

	ControllerGetstatusServlet controller = new ControllerGetstatusServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			controller.getStatus(request, response);
		} catch (Exception e) {
			out.print(NetCodeUtils.errorTomCat());
		} finally {
			out.close();
		}
	}

}
