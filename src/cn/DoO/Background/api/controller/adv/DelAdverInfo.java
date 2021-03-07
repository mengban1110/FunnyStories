package cn.DoO.Background.api.controller.adv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.adv.DelAdverInfoServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 删除指定广告
 * 
 * @author 孙雨桐
 * 
 *         2021年3月7日11点33分
 */
@SuppressWarnings("serial")
@WebServlet("/api/adv/deladv")
public class DelAdverInfo extends HttpServlet {

	DelAdverInfoServlet delAdverInfoServlet = new DelAdverInfoServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			delAdverInfoServlet.delAdverInfo(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());// 服务器内部错误
			return;
		}
	}

}
