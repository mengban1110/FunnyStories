package cn.DoO.Background.api.servlet.post.checked;

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

import cn.DoO.Background.api.dao.post.checked.CheckedPostDao;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 获取指定帖子的评论信息 
 * 
 * @author 孙雨桐
 * 
 * @Time 2021年3月6日22点11分
 *
 */
public class GetPostcommentInfoServlet {

	TokenDao tokenDao = new TokenDao();
	CheckedPostDao checkedPostDao = new CheckedPostDao();
	
	public void getPostCommentInfo(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		
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
		String postid = request.getParameter("postid");
		
		int page = 0;
		int size = 0;
		
		try {
			if (token == null || "".equals(token)) {
				writer.write(NetCodeUtils.isToken());//未登录
				return;
			}
			Integer.parseInt(postid);
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
		
		List<Map<String, Object>> dataList = checkedPostDao.getCommendByPostId(postid,word,page,size);
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<JSONObject> comments = new ArrayList<JSONObject>();
		Map<String, Object> userinfo = null;
		JSONObject jsonObject2 = null;
		Date date;
		for (Map<String, Object> map : dataList) {
			jsonObject2 = new JSONObject();
			userinfo = new HashMap<String, Object>();
			
			userinfo.put("uname",map.get("username"));
			userinfo.put("useravatar", map.get("useravatar"));
			jsonObject2.put("userinfo", userinfo);
			
			int likecount = checkedPostDao.getLikeCountByComment((int)map.get("commentid"));
			
			jsonObject2.put("likecount", likecount);
			jsonObject2.put("commentid", map.get("commentid"));
			jsonObject2.put("commenttext", map.get("commenttext"));
			jsonObject2.put("createtime", formatter.format(new Date(Long.parseLong((String)map.get("createtime")))));
			
			comments.add(jsonObject2);
		}
		
		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("comments", comments);
		
		jsonObject.put("code", 200);
		jsonObject.put("msg", "获取成功");
		jsonObject.put("data", jsonObject3);
		
		writer.write(jsonObject.toJSONString());
	}
}
