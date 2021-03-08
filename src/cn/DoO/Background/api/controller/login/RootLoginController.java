package cn.DoO.Background.api.controller.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.login.RootLoginServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 登陆
 * @author 云尧
 *
 */
@SuppressWarnings("serial")

@WebServlet("/api/controller/login/rootlogincontroller")
public class RootLoginController extends HttpServlet {

	RootLoginServlet login = new RootLoginServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			login.login(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			out.print(NetCodeUtils.errorTomCat());

		} finally {
			out.close();
		}

	}

}
