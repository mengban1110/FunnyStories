package cn.DoO.FrontEnd.api.servlet.user;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.NotEquals;

import cn.DoO.FrontEnd.api.dao.post.PostDao;
import cn.DoO.FrontEnd.api.dao.user.UserDao;
import cn.DoO.Utils.date.DateUtil;

/**
 * 获取指定用户数据(个人主页)
 * @author JZH
 *
 */
public class LookuserServlet {

	UserDao userDao = new UserDao();
	PostDao postDao = new PostDao();

	public void lookUser(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PrintWriter创建异常");
		}
		
		//接值
		String userid = request.getParameter("userid");
		String token = request.getParameter("token");
		try {
			
			//判空
			if(userid == null || userid.equals("")){
				jsonObject.put("code", "-2");
				jsonObject.put("msg", "非法调用");
				writer.write(jsonObject.toJSONString());
				return;
			}
			if(token == null || token.equals("")){
				jsonObject.put("code", "-2");
				jsonObject.put("msg", "非法调用");
				writer.write(jsonObject.toJSONString());
				return;
			}
			//验证
			Map<String, Object> user = userDao.queryUserByTokenAndId(token,userid);
			if(user == null){
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "登录失效");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			//获取信息
			int postcount = postDao.queryUserPostCount(user.get("uid").toString());
			int postlikecount = postDao.queryUserPostZanCount(user.get("uid").toString());
			int commentcount = postDao.queryUserCommentCount(user.get("uid").toString());
			
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String, Object> postinfo = new HashMap<String, Object>();
			Map<String, Object> userinfo = new HashMap<>();
				userinfo.put("uname", user.get("username").toString());
				userinfo.put("uid",user.get("uid").toString());
				userinfo.put("useravatar",user.get("useravatar").toString());
				if(user.get("usersex") == null){//性别
					userinfo.put("usersex","");
				}
				else{
					userinfo.put("usersex",user.get("usersex").toString());
				}
				
				if(user.get("usersign") == null){//签名
					userinfo.put("usersign","");
				}
				else{
					userinfo.put("usersign",user.get("usersign").toString());
				}
				
				if(user.get("userbirth") == null){//生日
					userinfo.put("userbirth","");
				}
				else{
					userinfo.put("userbirth",user.get("userbirth").toString());
				}
			postinfo.put("userinfo", userinfo);
			Map<String, Object> count = new HashMap<>();
				count.put("postcount", postcount);
				count.put("postlikecount", postlikecount);
				count.put("commentcount", commentcount);
			postinfo.put("count", count);
			
			//历史记录
			List<Map<String, Object>> historyData = postDao.queryHistoryPostById(userid);
			List<Map<String, Object>> history = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : historyData) {
				Map<String, Object> map2 = new HashMap<>();
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
				//纯文本
				if(map.get("postvideo") == null && map.get("posttext") == null && map.get("posttext") != null ){
					map2.put("posttext", "https://static.qiushibaike.com/images/web/v4/textDefault.png?v=12eaf94cfd4d3ae0423a3925bb5bbf9c");
				}
				//时间
				map2.put("createtime", DateUtil.getHaoMiaoToShiJian(map.get("btime").toString()));
				history.add(map2);
			}
			postinfo.put("history", history);
			
			
			//用户发的帖子
			List<Map<String, Object>> postTemp = postDao.queryUserPostById(userid); 
			List<Map<String, Object>> post = new ArrayList<>();
			for (Map<String, Object> map : postTemp) {
				Map<String, Object> map2 = new HashMap<>();
				Map<String, Object> count2 = new HashMap<>();
				//count
				int like = postDao.queryPostLike(map.get("postid").toString());
				int comment = postDao.queryPostComment(map.get("postid").toString());
				count2.put("like", like);
				count2.put("comment", comment);
				map2.put("count", count2);
				
				map2.put("postid", map.get("postid").toString());
				//图片
				if(map.get("postimg") != null && !map.get("postimg").toString().equals("")){
					map2.put("postimg", map.get("posttext").toString());
				}
				//视频
				if(map.get("postvideo") != null && !map.get("postvideo").toString().equals("")){
					map2.put("postvideo", map.get("postvideo").toString());
				}
				if(map.get("postvideo") == null && map.get("postimg") == null && map.get("posttext") != null){
					map2.put("posttext", map.get("posttext").toString());
				}
				
				//时间
				map2.put("createtime", DateUtil.getHaoMiaoToShiJian(map.get("ptime").toString()));
				post.add(map2);
			}
			
			//评论
			List<Map<String, Object>> postcommentTemp = postDao.queryCommentById(userid);
			List<Map<String, Object>> postcomment = postDao.queryCommentById(userid);
			for (Map<String, Object> map : postcommentTemp) {
				Map<String, Object> map2 = new HashMap<>();
				Map<String, Object> userinfo2 = new HashMap<>();
					userinfo2.put("username", user.get("username").toString());
					userinfo2.put("userid", user.get("uid").toString());
					userinfo2.put("useravatar", user.get("useravatar").toString());
				map2.put("userinfo", userinfo2);
				map2.put("postid", map.get("postid").toString());
				map2.put("commentid", map.get("commentid").toString());
				map2.put("commenttext", map.get("commenttext").toString());
				postcomment.add(map2);
			}
			
			data.put("postinfo", postinfo);
			data.put("post", post);
			data.put("postcomment", postcomment);
			
			jsonObject.put("code", "200");
			jsonObject.put("msg", "获取成功");
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
