package cn.DoO.Background.api.controller.post.checked;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.checked.DelCommentDataServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 删除指定帖子评论
 * 
 * @author 孙雨桐
 *
 * @Time 2021年3月6日23点50分
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/checked/delcomment")
public class DelCommentData extends HttpServlet {

	DelCommentDataServlet delCommentDataServlet = new DelCommentDataServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			delCommentDataServlet.delCommentData(request, response);
		} catch (Exception e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());// 服务器内部错误
			return;
		}

	}

}
