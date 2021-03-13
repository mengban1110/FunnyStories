package cn.DoO.Background.api.servlet.post.checking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.post.checking.CheckingPostDaolmpl;

/**
* @desc 待审核帖子数量
* 
* @author TianShuo
* 
* @version 2021年3月13日 下午10:32:44
*/
public class GetCheckingPostCountServlet {
	
	CheckingPostDaolmpl checkingPostDaolmpl = new CheckingPostDaolmpl();
	
	public void getPostCount(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// json对象
				JSONObject jsonObject = new JSONObject();
				PrintWriter writer = null;
				try {
					writer = response.getWriter();
				} catch (IOException e) {
					System.out.println("printwriter获取异常");
				}
				
				int count = checkingPostDaolmpl.getCount();
				System.out.println(count);
				jsonObject.put("data", count);
				writer.write(jsonObject.toJSONString());
	}

}
