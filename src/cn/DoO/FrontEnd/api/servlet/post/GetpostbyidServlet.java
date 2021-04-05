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
import cn.DoO.FrontEnd.api.dao.user.UserDao;
import cn.DoO.Utils.date.DateUtil;

/**
 * 获取指定帖子数据(内有评论)
 * @author LYP
 *
 */
public class GetpostbyidServlet {
	PostDao postDao = new PostDao();
	UserDao userDao = new UserDao();
	public void getPostbyId(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PrintWriter创建异常");
		}
		
		//接值
		String postid = request.getParameter("postid");
		String token = request.getParameter("token");
		
		try {
			//判空
			if(postid == null || postid.equals("")){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "非法调用");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//判断post是否存在
			Map<String, Object> post = postDao.queryPostById(postid);
			if(post == null){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "帖子不存在");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			Map<String, Object> user = null;
			//判断是否有token
			if(token != null){
				user = userDao.queryUserByToken(token);
				if(user == null){
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "登录失效");
					writer.write(jsonObject.toJSONString());
					return;
				}
			}
			
			//获取信息
			//帖子信息
			Map<String, Object> post2 = postDao.queryPostInfoById(postid);
			//评论信息
			List<Map<String, Object>> pingLun = postDao.queryPingLunByPostId(postid);
			System.out.println("==========="+pingLun);
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String, Object> postinfo = new HashMap<String, Object>();
			Map<String, Object> userinfo = new HashMap<String, Object>();
				userinfo.put("uname", post2.get("username").toString());
				userinfo.put("uid", post2.get("uid").toString());
				userinfo.put("useravatar", post2.get("useravatar").toString());
				//发帖人发帖数量
				int postcount = postDao.queryUserPostCount(post2.get("uid").toString());
				//发帖人帖子被点赞数量
				int postlikecount = postDao.queryUserPostZanCount(post2.get("uid").toString());
				userinfo.put("postcount", postcount);
				userinfo.put("postlikecount", postlikecount);
			postinfo.put("userinfo", userinfo);
			Map<String, Object> count = new HashMap<String, Object>();
				count.put("like", post2.get("postlike").toString());
				count.put("comment", post2.get("postcomment").toString());
			postinfo.put("count", count);
			postinfo.put("postid", post2.get("postid").toString());
			//图片
			if(post2.get("postimg") != null){
				postinfo.put("postimg", post2.get("postimg").toString());
			}
			//视频
			if(post2.get("postvideo") != null){
				postinfo.put("postvideo", post2.get("postvideo").toString());
			}
			if(post2.get("posttext") != null){
				postinfo.put("posttext", post2.get("posttext").toString());
			}
			else{
				postinfo.put("posttext", "");
			}
			//时间
			postinfo.put("createtime", DateUtil.getHaoMiaoToShiJian(post2.get("ptime").toString()));
			data.put("postinfo", postinfo);
			//评论信息
			List<Map<String, Object>> postcomment = new ArrayList<>();
			System.out.println(pingLun.size());
			for (Map<String, Object> map : pingLun) {
				Map<String, Object> map2 = new HashMap<>();
				Map<String, Object> userinfo2 = new HashMap<>();
				if(map.get("username") == null){
					jsonObject.put("code", "-1");
					jsonObject.put("msg", "帖子信息异常");
					writer.write(jsonObject.toJSONString());
					return;
				}
				userinfo2.put("username", map.get("username").toString());
				userinfo2.put("userid", map.get("uid").toString());
				map2.put("userinfo", userinfo2);
				map2.put("commentid", map.get("commentid").toString());
				if(map.get("commenttext") == null) continue;
				map2.put("commenttext", map.get("commenttext").toString());
				postcomment.add(map2);
			}
			data.put("postcomment", postcomment);
			jsonObject.put("code", "200");
			jsonObject.put("msg", "获取成功");
			jsonObject.put("data", data);
			//添加历史记录
			if(user != null){
				System.out.println(user.get("uid").toString());
				System.out.println(post2.get("postid").toString());
				System.out.println(post2.get("placeid").toString());
				int flag = postDao.addHistory(user.get("uid").toString(),post2.get("postid").toString(),post2.get("placeid").toString());
			}
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
