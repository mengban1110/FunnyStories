package cn.DoO.Background.api.servlet.post.checking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.DoO.Background.api.dao.homepage.RootDaoImpl;
import cn.DoO.Background.api.dao.post.checking.CheckingPostDaolmpl;
import cn.DoO.Utils.NetCode.NetCodeUtils;
import cn.DoO.Utils.Tools.DateUtils;

/**
 * @desc 获取待审核的部分帖子
 * 
 * @author TianShuo
 * 
 * @version 2021年3月6日 下午10:44:19
 */
public class GetInfopartPostServlet {

	// 管理员用户的dao
	RootDaoImpl rootDaoImpl = new RootDaoImpl();

	// 待审核帖子的dao
	CheckingPostDaolmpl checkingPostDaolmpl = new CheckingPostDaolmpl();

	public void getInfopartPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		PrintWriter out = response.getWriter();
		
		if (!"GET".equals(request.getMethod())) {
			out.write(NetCodeUtils.otherErrMsg("-404", "请求方式有误"));//请求方式错误
			return;
		}
		// 获取token
		String token = request.getParameter("token");


		Map<String, Object> data = new HashMap<String, Object>();

		int page = 1;// 页数
		int size = 20;// 每页的数量

		String pageStr = request.getParameter("page");
		String sizeStr = request.getParameter("size");
		String word = request.getParameter("word");

		// 如果token为空显示 和必须保证有page和size这两个参数
		if (token == null || token.equals("") || pageStr == null || sizeStr == null || word == null) {
			print(out, data, "-2", "非法调用");
			return;
		}

		// 当前端数据都不为空时
		else if (pageStr.equals("") == false && sizeStr.equals("") == false) {
			try {
				page = Integer.valueOf(pageStr);// 获取页数(如果为空 默认为1)
				size = Integer.valueOf(sizeStr);// 获取每页的数量(如果为空 默认为20)
			} catch (Exception e) {
				print(out, data, "-2", "非法调用");
				return;
			}
		}
		// 当页数为空 获取每页数量
		else if (pageStr.equals("") && sizeStr.equals("") == false) {
			try {
				size = Integer.valueOf(sizeStr);// 获取每页的数量(如果为空 默认为20)
			} catch (Exception e) {
				print(out, data, "-2", "非法调用");
				return;
			}
		}
		// 当每页数量为空 获取 页数
		else if (pageStr.equals("") == false && sizeStr.equals("")) {
			try {
				page = Integer.valueOf(pageStr);// 获取页数(如果为空 默认为1)
			} catch (Exception e) {
				print(out, data, "-2", "非法调用");
				return;
			}
		}

		// 获取管理员用户的Map
		Map<String, Object> rootMap = rootDaoImpl.getUserByToken(token);

		// 获取结果为空 提交的token于查询后的token不一致将返回未登录
		if (rootMap == null || rootMap.get("token").equals(token) == false) {
			print(out, data, "-1", "未登录");
			return;
		}

		List<Map<String, Object>> postinfoList = new ArrayList<Map<String, Object>>();

		// 初始结果集
		List<Map<String, Object>> postList = null;
		// 如果关键字是空 则查询不是文本的帖子
		if (word.equals("")) {
			postList = checkingPostDaolmpl.getNotTextPost(page, size);
		} else // 查询帖子文本值中带关键字的帖子
		{
			postList = checkingPostDaolmpl.getInfopartTextPost(page, size, word);
		}

		// 遍历结果集
		for (Map<String, Object> map : postList) {

			Map<String, Object> maps = new HashMap<>();

			Map<String, Object> userinfo = new HashMap<>();
			userinfo.put("uname", map.get("username"));// 发帖人姓名
			userinfo.put("useravatar", map.get("useravatar"));// 用户头像

			// 帖子模块的信息
			Map<String, Object> place = new HashMap<>();
			place.put("placeid", map.get("placeid"));
			place.put("placename", map.get("placename"));

			maps.put("userinfo", userinfo);// 用户信息
			maps.put("place", place);// 帖子模块信息

			maps.put("postid", map.get("postid"));// 帖子ID
			maps.put("posttext", map.get("posttext"));// 文本数值
			maps.put("postimg", map.get("postimg"));// 图片Url
			maps.put("postvideo", map.get("postvideo"));// 视频 如果有视频的话就没有图片了
			maps.put("createtime", DateUtils.MillToHourAndMin(map.get("createtime").toString()));// 时间

			postinfoList.add(maps);
		}

		data.put("code", "200");
		data.put("msg", "请求成功");
		// data的所以结构
		Map<String, Object> dataP = new HashMap<>();
		dataP.put("postinfo", postinfoList);
		data.put("data", dataP);
		out.print(JSON.toJSONString(data));

	}

	public void print(PrintWriter out, Map<String, Object> data, String coed, String msg) {
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		System.out.println(JSON.toJSONString(data));
		out.close();
	}
}
