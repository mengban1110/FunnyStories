package cn.DoO.Background.api.controller.status;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.status.WebPoststatusServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 写入开/关站控制状态
 * @author 云尧
 *
 */
@SuppressWarnings("serial")

@WebServlet("/api/controller/status/webpoststatus")
public class WebPoststatus extends HttpServlet {

	WebPoststatusServlet webPoststatusServlet = new WebPoststatusServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			webPoststatusServlet.webStatus(request, response);
		} catch (Exception e) {
			out.print(NetCodeUtils.errorTomCat());
		} finally {
			out.close();
		}

	}

}
