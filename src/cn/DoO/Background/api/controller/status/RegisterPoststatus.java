package cn.DoO.Background.api.controller.status;

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
import cn.DoO.Background.api.servlet.status.CommentPoststatusServlet;
import cn.DoO.Background.api.servlet.status.RegisterPoststatusServlet;
import cn.DoO.Background.api.servlet.status.WebPoststatusServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;



/**
 * @desc     写入注册控制状态
 * @author 云尧
 *
 */
@SuppressWarnings("serial")

@WebServlet("/api/controller/status/registerpoststatus")
public class RegisterPoststatus extends HttpServlet{

	RegisterPoststatusServlet registerPoststatusServlet=new RegisterPoststatusServlet();
	
	
	@Override
	protected void service(HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		NetCodeUtils nUtil=new NetCodeUtils();
		try {
			registerPoststatusServlet.registerStatus(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(nUtil.errorTomCat());

		} finally {
			out.close();
		}
		
		
	}

	
}
