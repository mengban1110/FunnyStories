package cn.DoO.Background.api.servlet.status;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.status.StatusDao;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
 * @desc 写入注册控制状态
 * @author 云尧
 *
 */
public class RegisterPoststatusServlet {
	TokenDao tokenDao = new TokenDao();
	StatusDao sDao = new StatusDao();

	/**
	 * @desc 写入注册状态
	 * @param request
	 * @param response
	 */
	public void registerStatus(HttpServletRequest request, HttpServletResponse response) {

		// json对象
		JSONObject jsonObject = null;
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
		// 判断参数
		try {
			// 取值
			String token = request.getParameter("token");
			int status = Integer.parseInt(request.getParameter("status"));
			if ((token == null || "".equals(token)) || (status != 0 && status != 1)) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-2");
				jsonObject.put("msg", "非法调用");
				writer.write(jsonObject.toString());
				return;
			}

			if (tokenDao.queryRootByToken(token) == null) {
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "未登录");
				writer.write(jsonObject.toString());
				return;

			}

			//判断status的取值
			String text;
			if(status==0){
				text="开启";
			}else if(status==1){
				text="关闭";
			}else{
				jsonObject = new JSONObject();
				jsonObject.put("code", "-2");
				jsonObject.put("msg", "非法调用");
				writer.write(jsonObject.toJSONString());
				return;
			}
			
			
			
			
			// 修改注册状态
			sDao.editRegisterPoststatus(status);
			jsonObject = new JSONObject();
			jsonObject.put("code", "200");
			jsonObject.put("msg", "请求成功");
			writer.write(jsonObject.toString());
			// 写入后台日志
			Map<String, Object> map = tokenDao.queryRootByToken(token);
			int rid = (int) map.get("rootid");
			sDao.writerLog(rid, request, "修改全站权限(rootid:"+rid+text+" 全站注册功能) ", 7);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = new JSONObject();
			jsonObject.put("code", "-2");
			jsonObject.put("msg", "非法调用");
			writer.write(jsonObject.toJSONString());
			return;
		}

	}

}
