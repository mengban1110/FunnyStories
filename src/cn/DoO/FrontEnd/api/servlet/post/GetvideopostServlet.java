package cn.DoO.FrontEnd.api.servlet.post;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.FrontEnd.api.dao.post.PostDao;
import cn.DoO.Utils.date.DateUtil;

/**
 * 获取视频贴
 * @author LYP
 *
 */
public class GetvideopostServlet {
	PostDao postDao = new PostDao();

	public void getVideoPost(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PrintWriter创建异常");
		}
		
		//接值
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		
		
		try {
			//生成默认
			if(page == null || page.equals("")){
				page = "1";
			}
			if(size == null || size.equals("")){
				size = "10";
			}
			
			List<Map<String, Object>> list = postDao.queryVideoPost(page,size);
			if(list == null || list.size() == 0){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "暂无帖子");
				writer.write(jsonObject.toJSONString());
				return;
			}
			List<Map<String, Object>> postinfo = new ArrayList<>();
			for (Map<String, Object> map : list) {
				Map<String, Object> map2 = new HashMap<>();
				Map<String, Object> userinfo = new HashMap<>();
					if(map.get("username") == null ) continue;
					userinfo.put("uname", map.get("username").toString());
					userinfo.put("useravatar", map.get("useravatar").toString());
					userinfo.put("uid", map.get("uid").toString());
				map2.put("userinfo", userinfo);
				Map<String, Object> count = new HashMap<>();
					count.put("like", map.get("postlike").toString());
					count.put("comment", map.get("postcomment").toString());
				map2.put("count", count);
				map2.put("postid", map.get("postid").toString());
					//图片
					if(map.get("postimg") != null && !map.get("postimg").toString().equals("")){
						map2.put("postimg", map.get("postimg").toString());
					}
					//视频
					if(map.get("postvideo") != null && !map.get("postvideo").toString().equals("")){
						map2.put("postvideo", map.get("postvideo").toString());
					}
					if(map.get("posttext") != null){
						map2.put("posttext", map.get("posttext").toString());
					}
					else{
						map2.put("posttext", "暂无文本");
					}
				//时间
				map2.put("createtime", DateUtil.getHaoMiaoToShiJian(map.get("ptime").toString()));
				postinfo.add(map2);
			}
			
			jsonObject.put("code", "200");
			jsonObject.put("msg", "请求成功");
			Map<String, Object> data = new HashMap<>();
			data.put("postinfo", postinfo);
			jsonObject.put("data", data);
			writer.write(jsonObject.toJSONString());
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "请求异常");
			writer.write(jsonObject.toJSONString());
			return;
		}finally {
			writer.flush();
			writer.close();
		}
	}
}
