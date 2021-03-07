package cn.DoO.Background.api.servlet.status;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.status.StatusDao;
import cn.DoO.Utils.Dao.Token.TokenDao;

/**
 * @desc 控制状态
 * @author 云尧
 *
 */
public class ControllerGetstatusServlet {

	TokenDao tokenDao = new TokenDao();
	StatusDao sDao = new StatusDao();

	public void getStatus(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		// json对象
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}

		// 接值
		String token = request.getParameter("token");

		// 判断参数

		if (token == null || "".equals(token)) {
			jsonObject = new JSONObject();
			jsonObject.put("code", "-2");
			jsonObject.put("msg", "非法调用");
			writer.write(jsonObject.toString());
			return;
		}

		if (tokenDao.queryRootByToken(token) == null) {
			jsonObject = new JSONObject();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "未登录");
			writer.write(jsonObject.toString());
			return;

		}

		// 存值
		Map<String, Object> map = sDao.query();
		Map<String, Object> user = new HashMap<String, Object>();

		user.put("register", map.get("register"));
		user.put("login", map.get("login"));
		user.put("open", map.get("open"));
		user.put("post", map.get("post"));
		user.put("comment", map.get("comment"));

		jsonObject = new JSONObject();
		jsonObject.put("code", "200");
		jsonObject.put("msg", "获取成功");
		jsonObject.put("data", user);
		writer.write(jsonObject.toJSONString());
		return;

	}

}
