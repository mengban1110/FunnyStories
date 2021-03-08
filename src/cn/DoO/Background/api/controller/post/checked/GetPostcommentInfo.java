package cn.DoO.Background.api.controller.post.checked;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.checked.GetPostcommentInfoServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 获取指定帖子的评论信息
 * 
 * @author 孙雨桐
 * 
 * @Time 2021年3月6日22点11分
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/checked/getcomment")
public class GetPostcommentInfo extends HttpServlet {

	GetPostcommentInfoServlet getPostcommentInfoServlet = new GetPostcommentInfoServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			getPostcommentInfoServlet.getPostCommentInfo(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			response.getWriter().write(NetCodeUtils.errorTomCat());// 服务器内部错误
			return;
		}
	}

}
