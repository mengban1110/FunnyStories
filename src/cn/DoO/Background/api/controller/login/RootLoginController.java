package cn.DoO.Background.api.controller.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.sun.org.glassfish.gmbal.ManagedAttribute;

import cn.DoO.Background.api.servlet.login.RootLoginServlet;
import cn.DoO.Utils.NetCode.NetCodeUtils;
import cn.DoO.Utils.Tools.IPUtils;



/**
 * @desc     登陆
 * @author 云尧
 *
 */
@SuppressWarnings("serial")

@WebServlet("/api/controller/login/rootlogincontroller")
public class RootLoginController extends HttpServlet{
	NetCodeUtils nUtil=new NetCodeUtils();
	RootLoginServlet login=new RootLoginServlet();
	@Override
	protected void service(HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			login.login(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			out.print(nUtil.errorTomCat());

		} finally {
			out.close();
		}
		
		
	}

	
	}



