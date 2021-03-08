package cn.DoO.Background.api.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.user.DelUserDateServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;
/**
 * 4.4 : 删除指定用户
 * @author 孙雨桐
 *
 */
@WebServlet("/api/user/deluser")
public class DelUserDate extends HttpServlet{

	DelUserDateServlet delUserDateServlet = new DelUserDateServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			delUserDateServlet.delUserDate(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());
			return;
		}
	
	}
	

}
