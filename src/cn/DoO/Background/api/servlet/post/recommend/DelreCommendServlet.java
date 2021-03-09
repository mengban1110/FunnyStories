package cn.DoO.Background.api.servlet.post.recommend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.post.PostDao;
import cn.DoO.Background.api.dao.status.StatusDao;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 取消推荐
 * @author 云尧
 *
 */
public class DelreCommendServlet {
	StatusDao statusDao=new StatusDao();
	TokenDao tDao = new TokenDao();
	PostDao pDao = new PostDao();

	public void delreCommend(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
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

		int postid = 0;
		try {
			postid = Integer.parseInt(request.getParameter("postid"));
			if (token == null || "".equals(token)) {
				writer.write(NetCodeUtils.ErrorParam());// 非法调用

				return;
			}
			if (tDao.queryRootByToken(token) == null) {
				writer.write(NetCodeUtils.isToken());// 未登录
				return;
			}

		} catch (Exception e) {
			writer.write(NetCodeUtils.ErrorParam());// 非法调用
			return;
		}
		Map<String, Object> map = pDao.query(postid);
		if (map == null || postid == 0) {
			writer.write(NetCodeUtils.ErrorParam());// 非法调用
			return;
		}
		
		Map<String, Object> root = pDao.getRootId(token);
		int rid = (int) root.get("rootid");
		pDao.cancel(postid);
		jsonObject.put("code", 200);
		jsonObject.put("msg", "取消成功");

		writer.write(jsonObject.toJSONString());
		statusDao.writerLog(rid, request, "取消推荐-postid:"+postid+"-帖子", 5);
		return;
	}

}
