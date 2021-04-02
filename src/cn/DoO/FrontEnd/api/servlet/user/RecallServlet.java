package cn.DoO.FrontEnd.api.servlet.user;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.FrontEnd.api.dao.user.UserDao;
import cn.DoO.Utils.Tools.Md5Utils;

/**
 * 找回密码(修改密码)
 * @author LYP
 *
 */
public class RecallServlet {
	
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
		String newpassword = request.getParameter("newpassword");
		String code = request.getParameter("code");
		
		try {
			//判空
			
			Map<String, Object> map = userDao.queryUserByEmail(email);
			if(map == null){
				jsonObject.put("code", "-5");
				jsonObject.put("msg", "没有此邮箱");
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
			if(code == null || code.equals("")){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入验证码");
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
			
			
			Map<String, Object> codeTemp = userDao.queryCodeById(Integer.parseInt(map.get("uid").toString()),code);
			if(codeTemp == null){//没有激活码
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请重新获取验证码");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//判断激活码输入错误
			System.out.println(codeTemp.get("code").toString());
			if(!code.equals(codeTemp.get("code").toString())){
				jsonObject.put("code", "-4");
				jsonObject.put("msg", "激活码错误");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			long codeTime = Long.parseLong(codeTemp.get("createtime").toString());
			long nowTime = System.currentTimeMillis();
			if(nowTime - codeTime > 300000){//超过5分钟
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "激活码失效");
				//修改激活码状态
				int count = userDao.updateCode(Integer.parseInt(map.get("uid").toString()),codeTemp.get("code").toString());
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
			//更新密码状态
			userDao.updateCode(Integer.parseInt(map.get("uid").toString()), codeTemp.get("createtime").toString());
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
