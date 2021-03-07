package cn.DoO.Background.api.controller.history;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.history.GetLogInfoServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 获取后台日志信息
 * @author 云尧
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/history/getloginfo")
public class GetLogInfoController extends HttpServlet {

	GetLogInfoServlet getLogInfoServlet = new GetLogInfoServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		try {
			getLogInfoServlet.getLogInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(NetCodeUtils.errorTomCat());
		} finally {
			out.close();
		}
	}
}
