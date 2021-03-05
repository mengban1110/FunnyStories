package cn.DoO.Background.api.controller.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.DoO.Background.api.servlet.login.RootLoginServlet;



/**
 * @desc     登陆
 * @author 云尧
 *
 */
@SuppressWarnings("serial")

@WebServlet("/api/controller/login/rootlogincontroller")
public class RootLoginController extends HttpServlet{

	RootLoginServlet login=new RootLoginServlet();
	@Override
	protected void service(HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			login.login(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			
			Map<String, Object> data = new HashMap<>();
			data.put("code", "500");
			data.put("msg", "服务器内部错误");
			out.print(JSON.toJSONString(data));

		} finally {
			out.close();
		}
		
		
	}

	
}
