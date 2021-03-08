package cn.DoO.Background.api.controller.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.user.PostUserDataServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

@SuppressWarnings("serial")
@WebServlet("/api/user/postdata")
/**
 * 修改用户指定数据
 * 
 * @author 孙雨桐
 * 
 * @Time 2021年3月4日21点54分
 */
public class PostUserData extends HttpServlet {

	PostUserDataServlet postUserDataServlet = new PostUserDataServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			postUserDataServlet.postUserData(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());
			return;
		}
	}

}
