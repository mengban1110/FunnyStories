package cn.DoO.Background.api.servlet.post.checked;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.post.checked.CheckedPostDao;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.LogUtils.WriterLogUtil;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 删除指定帖子
 * 
 * @author 孙雨桐
 *
 * @Time 2021年3月6日23点50分
 */
public class DelPostDataServlet {

	TokenDao tokenDao = new TokenDao();
	CheckedPostDao checkedPostDao = new CheckedPostDao();

	public void delPostData(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {

		// json对象
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}

		if (!"POST".equals(request.getMethod())) {
			writer.write(NetCodeUtils.otherErrMsg("-404", "请求方式有误"));//请求方式错误
			return;
		}

		// 接值
		String token = request.getParameter("token");
		String postid = request.getParameter("postid");

		try {
			Integer.parseInt(postid);

			if (token == null || "".equals(token)) {
				writer.write(NetCodeUtils.isToken());// 未登录
				return;
			}
			if (tokenDao.queryRootByToken(token) == null) {
				writer.write(NetCodeUtils.ErrorParam());// 非法调用
				return;
			}

		} catch (Exception e) {
			writer.write(NetCodeUtils.ErrorParam());// 非法调用
			return;
		}

		checkedPostDao.deletePost(postid);
		
		WriterLogUtil.writeLog(token, request, "-删除-postid:"+postid+"-帖子 ", 6);
		
		jsonObject.put("code", 200);
		jsonObject.put("msg", "删除成功");
		writer.write(jsonObject.toJSONString());
	}

}
