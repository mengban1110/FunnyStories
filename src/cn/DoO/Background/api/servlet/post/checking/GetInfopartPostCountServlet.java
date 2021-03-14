package cn.DoO.Background.api.servlet.post.checking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.post.checking.CheckingPostDaolmpl;

/**
* @desc 
* 
* @author TianShuo
* 
* @version 2021年3月14日 下午11:19:47
*/
public class GetInfopartPostCountServlet {
	// 待审核帖子的dao
	CheckingPostDaolmpl checkingPostDaolmpl = new CheckingPostDaolmpl();
	
	public void getPostCount(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		String word = request.getParameter("word");
		System.out.println(word);
		int num = 0 ;
		
		// 如果关键字是空 则查询不是文本的帖子
			if (word.equals("")) {
				num = checkingPostDaolmpl.getNotTextPostNum();
			} else // 查询帖子文本值中带关键字的帖子
			{
				num = checkingPostDaolmpl.getInfopartTextPostNum(word);
			}
			System.out.println(num);
			JSONObject jsonObject = new JSONObject();
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
			} catch (IOException e) {
				System.out.println("printwriter获取异常");
			}
			jsonObject.put("data", num);
			writer.write(jsonObject.toJSONString());
	}

}
