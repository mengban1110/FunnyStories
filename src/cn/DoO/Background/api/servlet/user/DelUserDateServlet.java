package cn.DoO.Background.api.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.user.UserDao;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;

public class DelUserDateServlet {

	TokenDao tokenDao = new TokenDao();
	UserDao userDao = new UserDao();
	
	public void delUserDate(HttpServletRequest request, HttpServletResponse response) {
		// json对象
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		if (!"POST".equals(request.getMethod())) {
			writer.write(NetCodeUtils.otherErrMsg("-404", "请求方式有误"));// 未登录
			return;
		}
		// 接值
		String token = request.getParameter("token");
		String uid = request.getParameter("uid");

		// 判断参数
		try {
			if (token == null || "".equals(token)) {
				writer.write(NetCodeUtils.ErrorParam());// 非法调用
				return;
			}
			if (tokenDao.queryRootByToken(token) == null) {
				writer.write(NetCodeUtils.ErrorParam());// 非法调用
				return;
			}
			try {
				Integer.parseInt(uid);
			} catch (Exception e) {
				writer.write(NetCodeUtils.ErrorParam());// 非法调用
				return;
			}
			if (uid == null || uid.length() == 0) {
				writer.write(NetCodeUtils.ErrorParam());// 非法调用
				return;
			}
			
			userDao.delUserById(uid);
			jsonObject.put("code", 200);
			jsonObject.put("msg", "删除成功");
			writer.write(jsonObject.toJSONString());
			
		}catch (Exception e) {
			jsonObject = new JSONObject();
			jsonObject.put("code", "-2");
			jsonObject.put("msg", "非法调用");
			writer.write(jsonObject.toJSONString());
			return;
		}
	}
}
	