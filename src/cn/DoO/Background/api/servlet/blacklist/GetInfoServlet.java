package cn.DoO.Background.api.servlet.blacklist;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.blacklist.BlackListDao;
import cn.DoO.Background.api.dao.getpage.GetPage;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/***
 * @desc 获取部分黑名单
 * @author 云尧
 *
 */

public class GetInfoServlet {

	static TokenDao tDao = new TokenDao();
	static BlackListDao bDao = new BlackListDao();

	public static void getInfo(HttpServletRequest request, HttpServletResponse response)
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
		String word = request.getParameter("word");
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
	
		List<Map<String, Object>> dataList = bDao.findBypart(word, page, size);
		System.out.println(dataList);

		List<JSONObject> blacklist = new ArrayList<JSONObject>();
		JSONObject jsonObject2 = null;
		for (Map<String, Object> map : dataList) {
			jsonObject2 = new JSONObject();

			jsonObject2.put("bid", map.get("bid"));
			jsonObject2.put("userid", map.get("uid"));
			jsonObject2.put("username", map.get("username"));
			jsonObject2.put("useravatar", map.get("useravatar"));
			jsonObject2.put("createtime", map.get("createtime"));

			blacklist.add(jsonObject2);
		}

		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("blacklist", blacklist);

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
	public static void getBlackListPage(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException{
		// json对象
				JSONObject jsonObject = new JSONObject();
				PrintWriter writer = null;
				try {
					writer = response.getWriter();
				} catch (IOException e) {
					System.out.println("printwriter获取异常");
				}
				String word = request.getParameter("word");
				int count = GetPage.getBlackListPage(word);
				System.out.println(count);
				jsonObject.put("data", count);
				writer.write(jsonObject.toJSONString());
	}

	
	
	
}
