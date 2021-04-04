package cn.DoO.FrontEnd.api.controller.adv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.FrontEnd.api.servlet.adv.GetadvServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
* @desc 获取广告数据
* 
* @author TianShuo
* 
* @version 2021年4月2日 下午5:15:06
*/
@SuppressWarnings("serial")
@WebServlet("/api/adv/getadv")
public class GetadvController  extends HttpServlet{
	GetadvServlet getadvServlet = new GetadvServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			getadvServlet.getadv(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());
		}
	}
}
