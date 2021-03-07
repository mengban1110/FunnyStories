package cn.DoO.Background.api.controller.post.checked;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.checked.GetCheckedPostInfoPartServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;


/**
 * 获取审核过的部分帖子
 * 
 * @author 孙雨桐
 * 
 * @Time 2021年3月6日15点52分
 */
@WebServlet("/api/post/checked/getinfopart")
public class GetCheckedPostInfoPart extends HttpServlet{

	GetCheckedPostInfoPartServlet getCheckedPostInfoPartServlet = new GetCheckedPostInfoPartServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			getCheckedPostInfoPartServlet.getCheckedPostInfoPart(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(NetCodeUtils.errorTomCat());//服务器内部错误
			return;
		}
	}
	
	

}
