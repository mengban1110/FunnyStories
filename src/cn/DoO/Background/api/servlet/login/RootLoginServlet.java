package cn.DoO.Background.api.servlet.login;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.DoO.Background.api.dao.login.loginDao;
import cn.DoO.Utils.Token.TokenUtils;
import cn.DoO.Utils.Tools.Md5Utils;



/**
 * @desc     登陆验证
 * @author 齐云尧
 * 
 */

public class RootLoginServlet{
	loginDao loginDao = new loginDao();
	Md5Utils md5 = new Md5Utils();
	TokenUtils token =new TokenUtils();
	/**
	 * @desc    登陆
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void login(HttpServletRequest request , HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException{
		PrintWriter out = response.getWriter();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> dataP = new HashMap<String, Object>();
		
		//获取管理员名称和密码
		String rootName = request.getParameter("rootname");
		String rootpassWord = request.getParameter("rootpassword");
		
		//查询是否存在该用户
		
		int rootuserNumber = loginDao.getUserByU(rootName);
		if (rootuserNumber  != 1) {
			print(out, dataP, "-3", "请输入正确的账号");
			return;
		}
		//md5加密
		String newrootPassWord =  md5.makeMd5(rootpassWord);
		 System.out.println(newrootPassWord);
		//判断密码是否正确
		Map<String, Object> root = loginDao.getpasswordByU(rootName);
	    String	oldrootpassword = root.get("rootpassword")+"";
	    System.out.println(oldrootpassword);
		if (!oldrootpassword.equals(newrootPassWord)) {
			print(out, dataP, "-4", "请输入正确的密码");
			return;
		}
		
		
		String oldtoken =root.get("token")+"";
		String email =root.get("email")+"";
		token.updateToken(oldtoken, email);
		
		print(out, dataP, "200", "登陆成功");
		return;
		
	}
	
	
	public void print(PrintWriter out, Map<String, Object> data, String coed, String msg) {
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		System.out.println(JSON.toJSONString(data));
		out.close(); 
	}
	
	
	
}
