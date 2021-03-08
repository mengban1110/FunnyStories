package cn.DoO.Background.api.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.user.UserDao;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 获取用户
 * 
 * @author 孙雨桐
 * 
 * @Time 2021年3月4日14点34分
 */
public class GetUserInfoServlet {

	TokenDao tokenDao = new TokenDao();
	UserDao userDao = new UserDao();

	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) {

		PrintWriter writer = null;
		// json对象
		JSONObject jsonObject = new JSONObject();

		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		if ("POST".equals(request.getMethod())) {
			writer.write(NetCodeUtils.otherErrMsg("-3", "请求方式有误"));// 未登录
			return;
		}
		// 接值
		String token = request.getParameter("token");
		String pagestr = request.getParameter("page");
		String sizestr = request.getParameter("size");

		int page = 0;
		int size = 0;

		try {
			if (token == null || "".equals(token)) {
				writer.write(NetCodeUtils.isToken());// 未登录
				return;
			}
			if (tokenDao.queryRootByToken(token) == null) {
				writer.write(NetCodeUtils.ErrorParam());// 非法调用
				return;
			}
			// 判断page 和 size 是否为空
			if (pagestr == null) {
				pagestr = "1";
				page = Integer.parseInt(pagestr);
			} else {
				page = Integer.parseInt(pagestr);
			}
			if (sizestr == null) {
				sizestr = "20";
				size = Integer.parseInt(sizestr);
			} else {
				size = Integer.parseInt(sizestr);
			}

			// 查询数据
			List<Map<String, Object>> userList = userDao.findUserByPageAndSize(page, size);

			// json赋值
			List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> map : userList) {
				Map<String, Object> user = new HashMap<String, Object>();
				user.put("userid", map.get("uid"));
				user.put("username", map.get("username"));
				user.put("useravatar", map.get("useravatar"));
				user.put("userbir", map.get("userbirth"));
				user.put("email", map.get("email"));
				user.put("usersign", map.get("usersign"));
				user.put("usersex", map.get("usersex"));

				users.add(user);
			}

			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("users", users);

			jsonObject.put("code", 200);
			jsonObject.put("msg", "获取成功");
			jsonObject.put("data", jsonObject2);
			writer.write(jsonObject.toJSONString());
			return;

		} catch (Exception e) {
			jsonObject = new JSONObject();
			jsonObject.put("code", "-2");
			jsonObject.put("msg", "非法调用");
			writer.write(jsonObject.toJSONString());
			return;
		}

	}

}
