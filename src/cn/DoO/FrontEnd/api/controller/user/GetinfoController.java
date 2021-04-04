package cn.DoO.FrontEnd.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.user.GetinfoServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
* @desc 获取用户数据
* 
* @author TianShuo
* 
* @version 2021年4月1日 下午8:06:45
*/
@SuppressWarnings("serial")
@WebServlet("/api/user/getuserinfo")
public class GetinfoController extends HttpServlet {
	GetinfoServlet getinfoServlet = new GetinfoServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			getinfoServlet.getinfo(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());
		}
	}
}
