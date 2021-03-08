package cn.DoO.Background.api.servlet.adv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.adv.AdvertDao;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * 获取广告信息
 * 
 * @author 孙雨桐
 *
 * @Time 2021年3月7日11点03分
 */
public class DelAdverInfoServlet {

	TokenDao tokenDao = new TokenDao();
	AdvertDao advertDao = new AdvertDao();

	public void delAdverInfo(HttpServletRequest request, HttpServletResponse response)
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
			writer.write(NetCodeUtils.otherErrMsg("-404", "请求方式有误"));// 未登录
			return;
		}
		// 接值
		String token = request.getParameter("token");
		String aid = request.getParameter("aid");

		try {
			Integer.parseInt(aid);

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
		advertDao.delAdvertById(aid);

		jsonObject.put("code", 200);
		jsonObject.put("msg", "删除成功");
		writer.write(jsonObject.toJSONString());
	}

}
