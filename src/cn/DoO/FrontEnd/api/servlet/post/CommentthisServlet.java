package cn.DoO.FrontEnd.api.servlet.post;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.DoO.FrontEnd.api.dao.post.PostDaolmpl;
import cn.DoO.Utils.NetCode.NetCodeUtils;
import cn.DoO.Utils.Tools.VerifyUtils;

/**
* @desc 
* 
* @author TianShuo
* 
* @version 2021年4月1日 下午7:52:45
*/
public class CommentthisServlet {
	PostDaolmpl postDaoImpl = new PostDaolmpl();
	public void commentthis(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		PrintWriter out = response.getWriter();

		if (!"POST".equals(request.getMethod())) {
			out.write(NetCodeUtils.otherErrMsg("-404", "请求方式有误"));//请求方式错误
			return;
		}
		// 获取token
			String token = request.getParameter("token");
			String postid = request.getParameter("postid");
			String commenttext = request.getParameter("commenttext");
			Map<String, Object> data = new HashMap<String, Object>();

			// 如果token为空显示
			if (token == null || token.equals("") || postid == null || postid.equals("")||commenttext == null||!VerifyUtils.isInteger(postid)) 
			{
				print(out, data, "-2", "非法调用");
				return;
			}
			if (commenttext.equals("")) {
				print(out, data, "-4", "评论不能为空");
				return;
			}
			
			// 获取用户的Map
			Map<String, Object>userMap = postDaoImpl.getUserByToken(token);
			// 获取结果为空 提交的token于查询后的token不一致将返回未登录
			if (userMap == null || userMap.get("usertoken").equals(token) == false) {
				print(out, data, "-1", "未登录");
				return;
			}
			
			if (!postDaoImpl.getPostById(postid)) {
				print(out, data, "-3", "帖子不存在");
				return;
			}
			
			try {
				int  uid = (Integer) userMap.get("uid");
				postDaoImpl.comment(uid,postid,commenttext);
				postDaoImpl.addComment(postid);
				print(out, data, "200", "评论成功");
				
			} catch (Exception e) {
				e.printStackTrace();
				print(out, data, "-3", "评论失败");
				
			}
			
	}
	/**
	 * @desc 输出
	 * @param out
	 * @param data
	 * @param coed
	 * @param msg
	 */
	public void print(PrintWriter out, Map<String, Object> data, String coed, String msg) {
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		System.out.println(JSON.toJSONString(data));
		out.close();
	}

}
