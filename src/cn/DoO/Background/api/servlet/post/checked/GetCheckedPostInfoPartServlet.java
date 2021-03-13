package cn.DoO.Background.api.servlet.post.checked;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.post.checked.CheckedPostDao;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 获取审核过的部分帖子
 * 
 * @author 孙雨桐
 *
 * @Time 2021年3月6日17点41分
 */
public class GetCheckedPostInfoPartServlet {

	TokenDao tokenDao = new TokenDao();
	CheckedPostDao checkedPostDao = new CheckedPostDao();

	public void getCheckedPostInfoPart(HttpServletRequest request, HttpServletResponse response)
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
				writer.write(NetCodeUtils.isToken());// 未登录
				return;
			}
			if (tokenDao.queryRootByToken(token) == null) {
				writer.write(NetCodeUtils.ErrorParam());// 非法调用
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
		System.out.println("word------------"+word);
		
		List<Map<String, Object>> dataList = checkedPostDao.findAllByWord(page, size, word);
		System.out.println(dataList);
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<JSONObject> postinfo = new ArrayList<JSONObject>();
		Map<String, Object> userinfo = null;
		Map<String, Object> place = null;
		Map<String, Object> count = null;
		JSONObject jsonObject2 = null;
		for (Map<String, Object> map : dataList) {
			jsonObject2 = new JSONObject();
			userinfo = new HashMap<String, Object>();
			place = new HashMap<String, Object>();
			count = new HashMap<String, Object>();

			userinfo.put("uname", map.get("username"));
			userinfo.put("useravatar", map.get("useravatar"));
			jsonObject2.put("userinfo", userinfo);

			place.put("placeid", map.get("placeid"));
			place.put("placename", map.get("placename"));
			jsonObject2.put("place", place);

			count.put("like", map.get("postlike"));
			count.put("share", map.get("postshare"));
			count.put("comment", map.get("postcomment"));
			jsonObject2.put("count", count);

			jsonObject2.put("postid", map.get("postid"));
			jsonObject2.put("posttext", map.get("posttext"));
			jsonObject2.put("postimg", map.get("postimg"));
			jsonObject2.put("postvideo", map.get("postvideo"));

			try {
				jsonObject2.put("createtime",
						formatter.format(new Date(Long.parseLong((String) map.get("createtime"))*1000)));
			} catch (Exception e) {
				jsonObject2.put("createtime", "");
			}

			postinfo.add(jsonObject2);
		}

		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("postinfo", postinfo);

		jsonObject.put("code", 200);
		jsonObject.put("msg", "获取成功");
		jsonObject.put("data", jsonObject3);

		writer.write(jsonObject.toJSONString());
	}
}
