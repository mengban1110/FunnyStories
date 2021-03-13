package cn.DoO.Background.api.controller.adv;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.DoO.Background.api.servlet.adv.GetAdvCountServlet;
@WebServlet("/api/adv/getadvCount")
public class GetAdvCount extends HttpServlet{

	GetAdvCountServlet getAdvCountServlet = new GetAdvCountServlet();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			getAdvCountServlet.getAdvCount(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
