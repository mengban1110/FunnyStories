package cn.DoO.FrontEnd.api.servlet.adv;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.DoO.FrontEnd.api.dao.adv.AdvDaolmpl;
import cn.DoO.Utils.NetCode.NetCodeUtils;

/**
* @desc 获取广告数据
* 
* @author TianShuo
* 
* @version 2021年4月2日 下午5:17:05
*/
public class GetadvServlet {
	AdvDaolmpl advDaolmpl = new AdvDaolmpl();
	public void getadv(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		PrintWriter out = response.getWriter();
		
		if (!"GET".equals(request.getMethod())) {
			out.write(NetCodeUtils.otherErrMsg("-404", "请求方式有误"));//请求方式错误
			return;
		}
		// 获取token
		String token = request.getParameter("token");
		System.out.println("token"+token);
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> dataMap= new HashMap<>();
	
		data.put("code", "200");
		data.put("msg", "请求成功");
		if ( token == null || token.equals("")) 
		{
			dataMap.put("advinfo", advDaolmpl.getAllAdv());
		}
		else 
		{
			dataMap.put("advinfo", null);
		}
		data.put("data", dataMap);
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
