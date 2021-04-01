package cn.DoO.FrontEnd.api.servlet.user;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.FrontEnd.api.dao.user.UserDao;

/**
 * 激活（验证验证码）
 * @author LYP
 *
 */
public class ActivateServlet {

	UserDao userDao = new UserDao();
	
	public void activeCode(HttpServletRequest request, HttpServletResponse response) {
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
		String code = request.getParameter("code");
		
		try {
			
			//判空
			if(email == null || email.equals("")){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "非法请求");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(code == null || code.equals("")){
				jsonObject.put("code", "-4");
				jsonObject.put("msg", "激活码错误");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//根据邮箱获取用户
			Map<String, Object> user = userDao.queryUserByEmail(email);
			if (user == null) {
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "非法请求");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			//id
			int id =Integer.parseInt(user.get("uid").toString()); 
			//获取code
			Map<String, Object> codeTemp = userDao.queryCodeById(id);
			if(codeTemp == null){//没有激活码
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请重新获取验证码");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//判断激活码输入错误
			if(!code.equals(codeTemp.get("code").toString())){
				jsonObject.put("code", "-4");
				jsonObject.put("msg", "激活码错误");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			long codeTime = Long.parseLong(codeTemp.get("createtime").toString());
			long nowTime = System.currentTimeMillis();
			if(nowTime - codeTime > 300000){//超过5分钟
				jsonObject.put("code", "-3");
				jsonObject.put("msg", "激活码失效");
				//修改激活码状态
				int count = userDao.updateCode(id,codeTemp.get("createtime").toString());
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			jsonObject.put("code", "200");
			jsonObject.put("msg", "激活成功");
			//修改用户状态 1
			int count = userDao.updateUserZtById(id);
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
