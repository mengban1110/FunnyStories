package cn.DoO.Background.api.servlet.history;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.getpage.GetPage;
import cn.DoO.Background.api.dao.history.HistoryDao;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;
import cn.DoO.Utils.Tools.DateUtils;

/**
 * @desc 获取后台日志
 * @author 云尧
 */
public class GetLogInfoServlet {
	TokenDao tDao = new TokenDao();
	HistoryDao hDao = new HistoryDao();

	public void getLogInfo(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		// json对象
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		if (!"GET".equals(request.getMethod())) {
			writer.write(NetCodeUtils.otherErrMsg("-404", "请求方式有误"));//请求方式错误
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
				writer.write(NetCodeUtils.ErrorParam());// 非法调用

				return;
			}
			if (tDao.queryRootByToken(token) == null) {
				writer.write(NetCodeUtils.isToken());// 未登录
				return;
			}
			// 判断page 和 size 是否为空
			if (pagestr == null || pagestr.equals("")) {
				pagestr = "1";
				page = Integer.parseInt(pagestr);
			} else {
				page = Integer.parseInt(pagestr);
			}
			if (sizestr == null || sizestr.equals("")) {
				sizestr = "20";
				size = Integer.parseInt(sizestr);
			} else {
				size = Integer.parseInt(sizestr);
			}

		} catch (Exception e) {
			writer.write(NetCodeUtils.ErrorParam());// 非法调用
			return;
		}
		List<Map<String, Object>> dataList = hDao.findAll(page, size);
		System.out.println(dataList);

		List<JSONObject> logs = new ArrayList<JSONObject>();
		JSONObject jsonObject2 = null;
		for (Map<String, Object> map : dataList) {
			jsonObject2 = new JSONObject();
			jsonObject2.put("rootid", map.get("rootid"));
			jsonObject2.put("rootname", map.get("rootname"));
			jsonObject2.put("rootavatar", map.get("rootavatar"));
			jsonObject2.put("content", map.get("content"));
			//String	createtime = DateUtils.MillToHourAndMin();
			jsonObject2.put("createtime", map.get("createtime").toString());
			jsonObject2.put("ip", map.get("ip"));
			logs.add(jsonObject2);
		}

		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("logs", logs);

		jsonObject.put("code", 200);
		jsonObject.put("msg", "获取成功");
		jsonObject.put("data", jsonObject3);

		writer.write(jsonObject.toJSONString());

	}
	/**
	 * @desc    获取总页数
	 * @param request
	 * @param response
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void GetHistoryPage(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException{
		// json对象
				JSONObject jsonObject = new JSONObject();
				PrintWriter writer = null;
				try {
					writer = response.getWriter();
				} catch (IOException e) {
					System.out.println("printwriter获取异常");
				}
				
				int count = GetPage.getHistory();
				System.out.println(count);
				jsonObject.put("data", count);
				writer.write(jsonObject.toJSONString());
	}

}
