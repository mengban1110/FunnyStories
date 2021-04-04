package cn.DoO.FrontEnd.api.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.DoO.FrontEnd.api.dao.user.UserDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;
import cn.DoO.Utils.QiNiu.PutFile;

/**
* @desc 修改用户数据
* 
* @author TianShuo
* 
* @version 2021年4月4日 下午4:31:03
*/
public class ChangeinfoServlet {
	UserDao userDao = new UserDao();
	public void changeinfo(HttpServletRequest request, HttpServletResponse response) throws IOException, FileUploadException, ClassNotFoundException, SQLException {
		PrintWriter out = response.getWriter();

		if (!"POST".equals(request.getMethod())) {
			out.write(NetCodeUtils.otherErrMsg("-404", "请求方式有误"));//请求方式错误
			return;
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		
		String uid  = null ;
		String username = null  ;
		String usersex  = null ;
		String userbirth  = null ;
		String usersign  = null ;
		String token = null ;
		
		
		
		
		
		
		
		
		
		
/**
 * =================================处理头像================================================
 */		FileItem imageItem = null;
		String imagePrifix = "";//图片后缀
		// 将请求消息实体中每一个项目封装成单独的DiskFileItem(FileItem接口的实现)对象的任务
		// 将本次请求的request封装成DiskFileItemFactory对象
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 使用ServletFileUpload解析器上传数据，解析结果返回一个List<FileItem>集合，每一个FileItem对应一个Form表单
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设定中文处理
		upload.setHeaderEncoding("utf-8");
		List<FileItem> formItemList;
		formItemList = upload.parseRequest(request);
		if ((formItemList != null) || (formItemList.size() > 0)) 
		{
			for (FileItem Item : formItemList) 
			{
				if (!Item.isFormField()) {// 如果不是表单（筛选出文件）
					// 获取文件名字
					String fileName = Item.getName();
					System.out.println("上传文件的名字:" + fileName);
					// 获取后缀
					String prifix = fileName.substring(fileName.lastIndexOf(".") + 1);
					// 后缀全部转小写 防止后缀大小写不统一
					prifix = prifix.toLowerCase();
					//System.out.println("上传文件的后缀:" + prifix);
					// bmp,,png,tif,gif和JPEG
					if (prifix.equals("png") || prifix.equals("jpg") || prifix.equals("bmp") || prifix.equals("tif")
							|| prifix.equals("gif") || prifix.equals("jpeg")) 
					{
						// 仅支持这几种格式的数据
						imageItem =Item;
						imagePrifix = prifix;
					}
					else
					{
						print(out, data, "-1", "文件类型不对");
					}
				}
				
				
				else {
					if (Item.getFieldName().equals("token")) {
						token = Item.getString("utf-8");
					}
					if (Item.getFieldName().equals("uid")) {
						uid = Item.getString("utf-8");
					}
					if (Item.getFieldName().equals("username")) {
						username = Item.getString("utf-8");
					}
					if (Item.getFieldName().equals("userbirth")) {
						userbirth = Item.getString("utf-8");
					}
					if (Item.getFieldName().equals("usersign")) {
						usersign = Item.getString("utf-8");
					}
					if (Item.getFieldName().equals("usersex")) {
						usersex = Item.getString("utf-8");
					}
				}
				
				
			}
		
		}
/**
 * ===========================================================================================
 */
		
		
		System.out.println(token);
		System.out.println(uid);
		System.out.println(username);
		System.out.println(usersex);
		System.out.println(userbirth);
		System.out.println(usersign);
		System.out.println(imageItem.getSize());
/**
 * ===========================================================================================
 */	
		if (
				token == null || token.equals("")
				||uid == null || uid.equals("")
				||username == null || username.equals("")
				||usersex == null || usersex.equals("")
				||userbirth == null || userbirth.equals("")
				||usersign == null || usersign.equals("")
				||imageItem == null ||imageItem.getSize()<1
				) 
		{
			print(out, data, "-2", "有参数为空不支持修改");
		}
		else 
		{
			
			try {
				
				
				Map<String, Object> user = userDao.queryUserByToken(token);
				if (user == null) 
				{
					print(out, data, "-1", "未登录");
				}
				else if (uid.equals(user.get("uid").toString())) 
				{
					 String useravatar = PutFile.Putimgs(imageItem.getInputStream(), imagePrifix);
					
					userDao.updateUser(uid,username,useravatar,usersex,userbirth,usersign);
					print(out, data, "200", "修改成功");
				}
				else 
				{
					print(out, data, "-2", "非法调用");
				}
			} catch (Exception e2) {
				print(out, data, "-2", "有参数错误不支持修改");
			}
		}
		
		
}
	
	public void print(PrintWriter out, Map<String, Object> data, String coed, String msg) {
		data.put("code", coed);
		data.put("msg", msg);
		out.print(JSON.toJSONString(data));
		System.out.println(JSON.toJSONString(data));
		out.close();
	}
}
