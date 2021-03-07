package cn.DoO.Background.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.user.GetUserDateServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 获取用户指定数据
 * 
 * @author 孙雨桐
 * 
 * @Time 2021年3月4日21点53分
 */
@SuppressWarnings("serial")
@WebServlet("/api/user/getdata")
public class GetUserDate extends HttpServlet {

	GetUserDateServlet getUserDateServlet = new GetUserDateServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			getUserDateServlet.getUserDate(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());
			return;
		}
	}
}
