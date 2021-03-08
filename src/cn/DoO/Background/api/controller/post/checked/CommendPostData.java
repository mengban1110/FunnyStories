package cn.DoO.Background.api.controller.post.checked;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.checked.CommendPostDataServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 推荐指定帖子
 * 
 * @author 孙雨桐
 *
 * @Time 2021年3月6日19点08分
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/checked/commend")
public class CommendPostData extends HttpServlet {

	CommendPostDataServlet commendPostdata = new CommendPostDataServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			commendPostdata.commendPost(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());// 服务器内部错误
			return;
		}

	}

}
