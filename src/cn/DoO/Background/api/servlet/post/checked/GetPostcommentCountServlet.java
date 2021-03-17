package cn.DoO.Background.api.servlet.post.checked;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.post.checked.CheckedPostDao;

public class GetPostcommentCountServlet {
	
	CheckedPostDao checkedPostDao = new CheckedPostDao();
	
	public void getPostcommentcount(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException{
		// json对象
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		String word = request.getParameter("word");
		String postid = request.getParameter("postid");
		
		int count = checkedPostDao.getCommentCount(postid,word);
		
		System.out.println(count);
		jsonObject.put("data", count);
		writer.write(jsonObject.toJSONString());
	}
}
