package cn.DoO.Background.api.servlet.status;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.status.StatusDao;
import cn.DoO.Utils.Dao.Token.TokenDao;

/**
 * @desc     写入登陆控制状态
 * @author 云尧
 *
 */
public class LoginPoststatusServlet {
	TokenDao tokenDao = new TokenDao();
	StatusDao sDao = new StatusDao();
	
	
	/**
	 * @desc    写入登陆状态
	 * @param request
	 * @param response
	 */
	public void lPostStatus(HttpServletRequest request, HttpServletResponse response){

		// json对象
				JSONObject jsonObject = null;
				PrintWriter writer = null;
				try {
					writer = response.getWriter();
				} catch (IOException e) {
					System.out.println("printwriter获取异常");
				}
				
		
		
		
		//判断参数
				try {
					//取值
					String token = request.getParameter("token");
					int status =Integer.parseInt(request.getParameter("status")) ;
					
					if (token == null ||"".equals(token)) {
						jsonObject = new JSONObject();
						jsonObject.put("code", "-1");
						jsonObject.put("msg", "未登录");
						writer.write(jsonObject.toString());
						return;
					}
					
					if (tokenDao.queryRootByToken(token)==null || (status!=0 && status!=1) ) {
						jsonObject = new JSONObject();
						jsonObject.put("code", "-2");
						jsonObject.put("msg", "非法调用");
						writer.write(jsonObject.toString());
						return;
					}
					
					//修改登陆状态
					sDao.editLoginPoststatus(status);
					jsonObject = new JSONObject();
					jsonObject.put("code", "200");
					jsonObject.put("msg", "请求成功");
					writer.write(jsonObject.toString());
					return;
				}catch (Exception e) {
					e.printStackTrace();
					jsonObject = new JSONObject();
					jsonObject.put("code", "-2");
					jsonObject.put("msg", "非法调用");
					writer.write(jsonObject.toJSONString());
					return;
				}
		
		
	}
}
