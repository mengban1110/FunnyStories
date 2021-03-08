package cn.DoO.Background.api.servlet.blacklist;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.blacklist.BlackListDao;
import cn.DoO.Background.api.dao.status.StatusDao;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 取消黑名单
 * @author 云尧
 *
 */
public class DeLinfoServlet {
	StatusDao sDao = new StatusDao();
	TokenDao tDao = new TokenDao();
	BlackListDao bDao = new BlackListDao();

	public void deLinfo(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		// json对象
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}

		// 接值
		String token = request.getParameter("token");

		int uid = 0;
		try {
			uid = Integer.parseInt(request.getParameter("uid"));
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
		Map<String, Object> map = bDao.query(uid);
		if (map == null || uid == 0) {
			writer.write(NetCodeUtils.ErrorParam());// 非法调用
			return;
		}
		bDao.cancel(uid);
		jsonObject.put("code", 200);
		jsonObject.put("msg", "取消成功");

		writer.write(jsonObject.toJSONString());
		// 写入后台日志
		Map<String, Object> map1 = tDao.queryRootByToken(token);
		int rid = (int) map1.get("rootid");
		sDao.writerLog(rid, request, "移除用户黑名单", 2);
		return;
	}
}
