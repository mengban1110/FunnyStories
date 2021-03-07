package cn.DoO.Background.api.servlet.adv;

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

import cn.DoO.Background.api.dao.adv.AdvertDao;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;
/**
 * 获取广告信息
 * 
 * @author 孙雨桐
 *
 * @Time 2021年3月7日11点03分
 */
public class GetAdvertInfoServlet {

	TokenDao tokenDao = new TokenDao();
	AdvertDao advertDao = new AdvertDao();
	
	public void getAdverInfo(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		
		// json对象
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		if ("POST".equals(request.getMethod())) {
			writer.write(NetCodeUtils.otherErrMsg("-3", "请求方式有误"));//未登录
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
				writer.write(NetCodeUtils.isToken());//未登录
				return;
			}
			if (tokenDao.queryRootByToken(token)==null) {
				writer.write(NetCodeUtils.ErrorParam());//非法调用
				return;
			}
			//判断page 和 size 是否为空
			if (pagestr == null || pagestr.equals("")) {
				pagestr = "1";
				page = Integer.parseInt(pagestr);
			}else{
				page = Integer.parseInt(pagestr);
			}
			if (sizestr == null  || sizestr.equals("")) {
				sizestr = "20";
				size = Integer.parseInt(sizestr);
			}else{
				size = Integer.parseInt(sizestr);
			}
		
		} catch (Exception e) {
			writer.write(NetCodeUtils.ErrorParam());//非法调用
			return;
		}
		
		
		List<Map<String, Object>> dataList = advertDao.findAll(word,page,size); 
		
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<JSONObject> advert = new ArrayList<JSONObject>();
		JSONObject jsonObject2 = null;
		Date date;
		for (Map<String, Object> map : dataList) {
			jsonObject2 = new JSONObject();
			
			jsonObject2.put("aid", map.get("aid"));
			jsonObject2.put("acontext", map.get("acontext"));
			jsonObject2.put("aimg", map.get("aimg"));
			jsonObject2.put("createtime", formatter.format(new Date(Long.parseLong((String)map.get("createtime")))));
			
			advert.add(jsonObject2);
		}
		
		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("advert", advert);
		
		jsonObject.put("code", 200);
		jsonObject.put("msg", "获取成功");
		jsonObject.put("data", jsonObject3);
		
		writer.write(jsonObject.toJSONString());
	}
}
