package cn.DoO.Background.api.controller.post.recommend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.recommend.GetInfoPartServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;
/**
 * @desc 获取推荐的部分帖子
 * @author 云尧
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/post/recommend/getinfopart")
public class GetInfopartController extends HttpServlet {

	GetInfoPartServlet getInfopartServlet = new GetInfoPartServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			getInfopartServlet.Getinfopart(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(NetCodeUtils.errorTomCat());
		} finally {
			out.close();
		}

	}

}
