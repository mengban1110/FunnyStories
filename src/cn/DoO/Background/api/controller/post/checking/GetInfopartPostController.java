package cn.DoO.Background.api.controller.post.checking;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.checking.GetInfopartPostServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
* @desc  获取待审核的部分帖子
* 
* @author TianShuo
* 
* @version 2021年3月6日 下午10:42:31
*
*/
@SuppressWarnings("serial")
@WebServlet("/api/post/checking/getinfopart")
public class GetInfopartPostController extends HttpServlet {
	GetInfopartPostServlet getInfopartPostServlet = new GetInfopartPostServlet();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		try 
		{
			getInfopartPostServlet.getInfopartPost(request, response);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			out.print(NetCodeUtils.errorTomCat());
		} 
		finally 
		{
			out.close();
		}
	}
}
