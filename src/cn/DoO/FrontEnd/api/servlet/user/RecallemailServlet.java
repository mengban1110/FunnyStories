package cn.DoO.FrontEnd.api.servlet.user;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.FrontEnd.api.dao.user.UserDao;
import cn.DoO.Utils.Tools.CodeUtils;
import cn.DoO.Utils.Tools.EmailUtils;

/**
 * 找回密码(发送邮件)
 * @author JZH
 *
 */
public class RecallemailServlet {
	UserDao userDao = new UserDao();

	public void recall(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PrintWriter创建异常");
		}
		
		//接值
		String email = request.getParameter("email");
		
		try {
			//判空
			if(email == null || email.equals("")){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入邮箱");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//验证邮箱合法
			Map<String, Object> user = userDao.queryUserByEmail(email);
			if(user == null){
				jsonObject.put("code", "-3");
				jsonObject.put("msg", "没有此邮箱");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			//发送邮件
			String code = CodeUtils.createdCode();
			EmailUtils.createMimeMessage(email, code, new Date());
			//将验证码存到数据库
			int codeTemp = userDao.codeInDataBase(code,user.get("uid").toString());
			
			jsonObject.put("code", "200");
			jsonObject.put("msg", "邮件发送成功");
			writer.write(jsonObject.toJSONString());
			return;
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toJSONString());
			return;
		}finally {
			writer.flush();
			writer.close();
		}
	}
}
