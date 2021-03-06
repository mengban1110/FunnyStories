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
 * 获取相关推荐
 * @author LYP
 *
 */
public class GetrelativeServlet {
	PostDao postDao = new PostDao();
	
	public void getrela(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PrintWriter创建异常");
		}
		
		
		try {
			
			List<Map<String, Object>> list = postDao.queryRandPost("1","5");
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
						map2.put("postvideo", "https://static.qiushibaike.com/images/web/v4/textDefault.png?v=12eaf94cfd4d3ae0423a3925bb5bbf9c");
					}
					if(map.get("posttext") != null){
						map2.put("posttext", map.get("posttext").toString());
					}
					else{
						map2.put("posttext", "暂无文本");
					}
					if(map.get("postvideo") == null && map.get("posttext") == null && map.get("posttext") != null ){
						map2.put("posttext", "https://static.qiushibaike.com/images/web/v4/textDefault.png?v=12eaf94cfd4d3ae0423a3925bb5bbf9c");
					}
				//时间
				map2.put("createtime", DateUtil.getHaoMiaoToShiJian(map.get("createtime").toString()));
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
