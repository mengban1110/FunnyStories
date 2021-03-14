package cn.DoO.Background.api.controller.adv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.post.recommend.GetInfoPartServlet;
import cn.DoO.Background.api.servlet.post.recommend.GetInfoServlet;
@SuppressWarnings("serial")
@WebServlet("/api/adv/GetInfoPart")
public class GetInfoPart extends HttpServlet{
	


		@Override
		protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			try {
				GetInfoPartServlet.GetInfoPartPage(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	}


