package cn.DoO.FrontEnd.api.servlet.user;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.status.StatusDao;
import cn.DoO.FrontEnd.api.dao.user.UserDao;
import cn.DoO.Utils.Tools.CodeUtils;
import cn.DoO.Utils.Tools.EmailUtils;
import cn.DoO.Utils.Tools.Md5Utils;

/**
 * 注册
 * @author JZH
 *
 */
public class RegisterServlet {
	UserDao userDao = new UserDao();
	StatusDao statusDao = new StatusDao();
	public void zhuCe(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PrintWriter创建异常");
		}
		
		//接值
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		System.out.println(username);
		System.out.println(password);
		System.out.println(email);
		try {
			//验证是否可以注册
			Map<String, Object> status = statusDao.query();
			if(status.get("register").toString().equals("0")){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "抱歉，注册暂时关闭");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(status.get("open").toString().equals("0")){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "程序维护中");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//判空
			if(username == null || username.equals("")){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入昵称");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//判断昵称是否合法
			if(username.length() > 11){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "昵称太长啦！");
				writer.write(jsonObject.toJSONString());
				return;
			}
			Map<String , Object> user = userDao.queryUserByNameZero(username);
			if(user != null && !user.get("userstatus").toString().equals("0")){
				jsonObject.put("code", "-3");
				jsonObject.put("msg", "昵称已被注册");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(password == null || password.equals("")){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入密码");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//验证密码是否合法
			//判断密码是否简单
			boolean ziMu = false;
			boolean shuZi = false;
			//判断数字
			for(int i = 0;i<11;i++){
				if(password.indexOf(i+"") != -1){//有数字
					shuZi = true;
				}
			}
			
			//判断字母
			for(int i = 0;i<password.length();i++){
				//asc码
				int asc = Integer.valueOf(password.charAt(i));
				//判断是否有字母
				if((asc >= 65 && asc <= 90) || (asc >= 97 && asc <= 122)){
					ziMu = true;
				}
			}
			System.out.println(ziMu);
			System.out.println(shuZi);
			if(password.length() < 8 || ziMu == false || shuZi == false){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "密码过于简单");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(email == null || email.equals("")){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "请输入邮箱");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//验证邮箱是否合法
			Map<String, Object> userForEmail = userDao.queryUserByEmailZero(email);
			if(userForEmail != null && !userForEmail.get("userstatus").toString().equals("0")){
				jsonObject.put("code", "-4");
				jsonObject.put("msg", "邮箱已被使用");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			
			//验证完成=======================================================================
			//新建用户
			String pwd = Md5Utils.makeMd5(password);
			if(user == null && userForEmail == null){
				int addUser = userDao.addUser(username,pwd,email);
			}
			//获取用户信息
			Map<String, Object> newUser = userDao.queryUserByEmail(email);
			//发送邮件
			String code = CodeUtils.createdCode();
			//开启线程
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						EmailUtils.createMimeMessage(email, code, new Date());
					} catch (UnsupportedEncodingException | MessagingException e) {
						e.printStackTrace();
					}
				}
			}).start();
			
			
			//将验证码存到数据库
			int codeTemp = userDao.codeInDataBase(code,newUser.get("uid").toString());
			
			//注册完成
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
