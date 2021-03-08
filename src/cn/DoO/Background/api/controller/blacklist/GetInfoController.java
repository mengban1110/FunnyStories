package cn.DoO.Background.api.controller.blacklist;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.blacklist.GetInfoServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc     获取黑名单成员信息
 * @author 云尧
 *
 */
@SuppressWarnings("serial")
@WebServlet("/api/blacklist/getinfo")
public class GetInfoController extends HttpServlet{

	NetCodeUtils nUtil=new NetCodeUtils();
	GetInfoServlet getInfo=new GetInfoServlet();
	@Override
		protected void service(HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
			
			PrintWriter out = response.getWriter();
			
			try {
				getInfo.getInfo(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				out.print(nUtil.errorTomCat());

			} finally {
				out.close();
			}
		}
	
}
