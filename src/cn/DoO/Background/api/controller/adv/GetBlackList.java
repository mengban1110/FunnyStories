package cn.DoO.Background.api.controller.adv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.blacklist.GetInfoServlet;
import cn.DoO.Background.api.servlet.post.recommend.GetInfoPartServlet;
@SuppressWarnings("serial")
@WebServlet("/api/adv/GetBlackList")
public class GetBlackList extends HttpServlet{

		@Override
			protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
				try {
					GetInfoServlet.getBlackListPage(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

}
