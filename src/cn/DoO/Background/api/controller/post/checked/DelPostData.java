package cn.DoO.Background.api.controller.post.checked;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.checked.DelPostDataServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 删除指定帖子
 * 
 * @author 孙雨桐
 * 
 * @Time 2021年3月6日21点46分
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/checked/delpost")
public class DelPostData extends HttpServlet {

	DelPostDataServlet delpostDataServlet = new DelPostDataServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			delpostDataServlet.delPostData(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());// 服务器内部错误
			return;
		}
	}
}
