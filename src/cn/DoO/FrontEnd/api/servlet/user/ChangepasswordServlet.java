package cn.DoO.FrontEnd.api.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.FrontEnd.api.dao.user.UserDao;
import cn.DoO.Utils.Tools.Md5Utils;

/**
 * 修改密码
 * @author LYP
 *
 */
public class ChangepasswordServlet {

	UserDao userDao = new UserDao();
	
	public void changePWD(HttpServletRequest request, HttpServletResponse response) {
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
		String newpassword = request.getParameter("newpassword");
		String token = request.getParameter("token");
		
		try {
			//判空
			if(token == null || token.equals("")){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "非法请求");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//判断token是否存在
			Map<String, Object> map = userDao.queryUserByToken(token);
			if (map == null || map.size() == 0) {
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "登录失效");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			if(email == null || email.equals("")){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入邮箱");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(newpassword == null || newpassword.equals("")){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入新密码");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//判断邮箱是否是本账号的
			if(!email.equals(map.get("email").toString())){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "邮箱错误");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			//判断密码是否简单
			boolean ziMu = false;
			boolean shuZi = false;
			//判断数字
			for(int i = 0;i<11;i++){
				if(newpassword.indexOf(i+"") != -1){//有数字
					shuZi = true;
				}
			}
			
			//判断字母
			for(int i = 0;i<newpassword.length();i++){
				//asc码
				int asc = Integer.valueOf(newpassword.charAt(i));
				//判断是否有字母
				if((asc >= 65 && asc <= 90) || (asc >= 97 && asc <= 122)){
					ziMu = true;
				}
			}
			
			if(newpassword.length() < 11 || ziMu == false || shuZi == false){
				jsonObject.put("code", "-4");
				jsonObject.put("msg", "密码过于简单");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			
			//判断密码是否相同
			String newPwd = Md5Utils.makeMd5(newpassword);
			if(newPwd.equals(map.get("password").toString())){
				jsonObject.put("code", "-3");
				jsonObject.put("msg", "请勿与原密码相同");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			//判断完成修改密码
			int count = userDao.updatePwd(newPwd,map.get("uid").toString());
			if(count == 0){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "修改失败");
				writer.write(jsonObject.toJSONString());
				return;
			}
			jsonObject.put("code", "200");
			jsonObject.put("msg", "修改成功");
			
			//更新token
			String tokenTemp = Md5Utils.makeMd5(map.get("uid").toString()+System.currentTimeMillis()+"");
			int tokenCount = userDao.updateToken(tokenTemp,map.get("uid").toString());
			
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
