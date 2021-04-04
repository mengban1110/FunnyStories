package cn.DoO.FrontEnd.api.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.DoO.FrontEnd.api.dao.post.PostDaolmpl;
import cn.DoO.FrontEnd.api.dao.user.UserDaoImpl;
import cn.DoO.Utils.NetCode.NetCodeUtils;
import cn.DoO.Utils.Tools.VerifyUtils;

/**
* @desc 修改用户数据
* 
* @author TianShuo
* 
* @version 2021年4月1日 下午8:07:59
*/
public class GetinfoServlet {
	PostDaolmpl postDaoImpl = new PostDaolmpl();
	UserDaoImpl userDaoImpl =  new UserDaoImpl();
	public void getinfo(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		PrintWriter out = response.getWriter();

		if (!"POST".equals(request.getMethod())) {
			out.write(NetCodeUtils.otherErrMsg("-404", "请求方式有误"));//请求方式错误
			return;
		}
		// 获取token
		String token = request.getParameter("token");
		String uid = request.getParameter("uid");

		Map<String, Object> data = new HashMap<String, Object>();

		// 如果token为空显示
		if (token == null || token.equals("") || uid == null || uid.equals("")||!VerifyUtils.isInteger(uid)) 
		{
			print(out, data, "-2", "非法调用");
			return;
		}
		// 获取用户的Map
		Map<String, Object>userMap = postDaoImpl.getUserByToken(token);
	
		// 获取结果为空 提交的token于查询后的token不一致将返回未登录
		if (userMap == null || userMap.get("usertoken").equals(token) == false) {
			print(out, data, "-1", "未登录");
			return;
		}
		Map<String,Object> map =userDaoImpl.getUserById(uid);
		try {
			
		
		if (map==null) 
		{
			print(out, data, "-6", "没有找到此用户");
		}
		else 
		{
			String uname = (String) map.get("username");
			String useravatar = (String) map.get("useravatar");
			String usersex = (String) map.get("usersex");
			String usersign = (String) map.get("usersign");
			String userbirth = (String) map.get("userbirth");
			
			Map<String, Object> dataMap = new  HashMap<>();
			
			dataMap.put("uname", uname);
			dataMap.put("useravatar", useravatar);
			dataMap.put("usersex", usersex);
			dataMap.put("userbirth", userbirth);
			dataMap.put("uid", uid);
			dataMap.put("usersign", usersign);
			
			data.put("code", "200");
			data.put("msg", "请求成功");
			data.put("data", dataMap);
			out.print(JSON.toJSONString(data));
		}
		
		} catch (Exception e) {
			print(out, data, "-7", "查询失败");
		}
		
	}
	public void print(PrintWriter out, Map<String, Object> data, String coed, String msg) {
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		System.out.println(JSON.toJSONString(data));
		out.close();
	}
}
