package cn.DoO.Background.api.controller.adv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.adv.GetAdvertInfoServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 获取广告信息
 * 
 * @author 孙雨桐
 *
 * @Time 2021年3月7日11点03分
 */
@SuppressWarnings("serial")
@WebServlet("/api/adv/getinfo")
public class GetAdvertInfo extends HttpServlet {
	
	GetAdvertInfoServlet getAdvertInfoServlet = new GetAdvertInfoServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			getAdvertInfoServlet.getAdverInfo(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());// 服务器内部错误
			return;
		}
	}

}
