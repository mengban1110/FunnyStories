package cn.DoO.Background.api.controller.post.checking;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.checking.AuditpostServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
* @desc 审核指定帖子
* 
* @author TianShuo
* 
* @version 2021年3月6日 下午11:11:28
*/
@SuppressWarnings("serial")
@WebServlet("/api/post/checking/auditpost")
public class AuditpostController extends HttpServlet {
	AuditpostServlet  auditPostServlet = new AuditpostServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{

		PrintWriter out = response.getWriter();
		try 
		{
			auditPostServlet.auditPost(request, response);
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
