package cn.DoO.Background.api.servlet.adv;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.adv.AdvertDao;

public class GetAdvCountServlet {

	AdvertDao advertDao = new AdvertDao();
	
	public void getAdvCount(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException{
		
		// json对象
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		String word = request.getParameter("word");
		int count = advertDao.getCount(word);
		System.out.println(count);
		jsonObject.put("data", count);
		writer.write(jsonObject.toJSONString());
	}
}
