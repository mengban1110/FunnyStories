package cn.DoO.FrontEnd.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.user.ChangeinfoServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
* @desc 修改用户数据
* 
* @author TianShuo
* 
* @version 2021年4月4日 下午4:29:17
*/
@SuppressWarnings("serial")
@WebServlet("/api/user/changeinfo")
public class ChangeinfoController extends HttpServlet{
	ChangeinfoServlet changeinfoServlet =  new ChangeinfoServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			changeinfoServlet.changeinfo(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());
		}
	}
}
