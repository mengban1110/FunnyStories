package cn.DoO.Background.api.servlet.homepage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.DoO.Background.api.dao.homepage.RootDaoImpl;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
* @desc 首页数据之各个板块帖子数
* 
* @author TianShuo
* 
* @version 2021年3月5日 下午3:47:15
*/
public class GetPostPartCountServlet {
	// 管理员用户的dao
		RootDaoImpl rootDaoImpl = new RootDaoImpl();

		public void getPostPartCount(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
			PrintWriter out = response.getWriter();

			if (!"GET".equals(request.getMethod())) {
				out.write(NetCodeUtils.otherErrMsg("-404", "请求方式有误"));//请求方式错误
				return;
			}
			// 获取token
			String token = request.getParameter("token");


			Map<String, Object> data = new HashMap<String, Object>();

			// 如果token为空显示
			if (token == null || token.equals("")) 
			{
				print(out, data, "-2", "非法调用");
				return;
			}

			// 获取管理员用户的Map
			Map<String, Object> rootMap = rootDaoImpl.getUserByToken(token);

			// 获取结果为空 提交的token于查询后的token不一致将返回未登录
			if 
			(rootMap == null || rootMap.get("token").equals(token) == false) 
			{
				print(out, data, "-1", "未登录");
				return;
			}
			
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>  ();
//			图片帖子
			Map<String, Object> postImgCountMap = new HashMap<>();
			postImgCountMap.put("name", "图片板块");
			postImgCountMap.put("value", rootDaoImpl.getPostImgCount().get("Count(*)"));
//			文字帖子
			Map<String, Object> postTextCountMap = new HashMap<>();
			postTextCountMap.put("name", "文字板块");
			postTextCountMap.put("value", rootDaoImpl.getPostTextCount().get("Count(*)"));
//			视频帖子
			Map<String, Object> postVideoCountMap = new HashMap<>();
			postVideoCountMap.put("name", "视频板块");
			postVideoCountMap.put("value", rootDaoImpl.getPostImgCount().get("Count(*)"));
			
			dataList.add(postVideoCountMap);
			dataList.add(postTextCountMap);
			dataList.add(postImgCountMap);
			
			data.put("code", "200");
			data.put("msg", "请求成功");
			data.put("data", dataList);
			out.print(JSON.toJSONString(data));
			
		}

	public void print(PrintWriter out, Map<String, Object> data, String coed, String msg) {
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		System.out.println(JSON.toJSONString(data));
		out.close();
	}
}
