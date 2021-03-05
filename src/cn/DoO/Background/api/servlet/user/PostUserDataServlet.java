package cn.DoO.Background.api.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.user.UserDao;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;
import cn.DoO.Utils.QiNiu.PutFile;

/**
 * 修改用户指定数据
 * 
 * @author 孙雨桐
 * 
 * @Time 2021年3月4日19点37分
 */
public class PostUserDataServlet {
	
	TokenDao tokenDao = new TokenDao();
	UserDao userDao = new UserDao();
	
	public void postUserData(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		
		// json对象
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}
		
		try{
			// 接值
			String token = null;//token
			String uid = null; 
			String username = null;
			
			String useravatar = null;// 头像地址
			FileItem useravatarItem = null;// 头像流
			String prifixTemp = null;// 后缀
			
			String usersex = null;
			String userbir = null;
			String usersign = null;
			String userstatus = null;
			String password = null;
			
			// 将本次请求的request封装成DiskFileItemFactory对象
			DiskFileItemFactory factory = new DiskFileItemFactory();
			
			// 使用ServletFileUpload解析器上传数据，解析结果返回一个List<FileItem>集合，
			// 每一个FileItem对应一个Form表单
			List<FileItem> formItemList;
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			// 设定中文处理
			upload.setHeaderEncoding("utf-8");
			
			formItemList = upload.parseRequest(request);
			if ((formItemList != null) || (formItemList.size() > 0)) {

				for (FileItem Item : formItemList) {
					System.out.println(Item);
					if (!Item.isFormField()) {// 如果不是表单（筛选出文件）
						// 获取文件名字
						String fileName = Item.getName();

						// 获取后缀
						String prifix = fileName.substring(fileName.lastIndexOf(".") + 1);
						// 后缀全部转小写 防止后缀大小写不统一
						prifix = prifix.toLowerCase();

						// bmp,png,tif,gif和JPEG
						if (prifix.equals("png") || prifix.equals("jpg") || prifix.equals("bmp") || prifix.equals("tif")
								|| prifix.equals("gif") || prifix.equals("jpeg")) {

							// 如果已经获取过
							if (useravatarItem != null) {
								jsonObject = new JSONObject();
								jsonObject.put("code", "-1");
								jsonObject.put("msg", "只能上传一张头像");
								writer.write(jsonObject.toJSONString());
								return;
							}
							// 获取图片流
							useravatarItem = Item;
							// 获取图片后缀
							prifixTemp = prifix;
							
							System.out.println("上传文件的名字:" + useravatarItem.getName());
							System.out.println("上传文件的后缀:" + prifixTemp);
							
							// 判断完毕再上传头像--优化
							useravatar = PutFile.Putimgs(useravatarItem.getInputStream(), prifixTemp);
							System.out.println(useravatar);
						}

						else {
							jsonObject = new JSONObject();
							jsonObject.put("code", "-1");
							jsonObject.put("msg", "文件类型不支持");
							writer.write(jsonObject.toJSONString());
							return;
						}
					} else {// 获取其他信息
						if (Item.getFieldName().equals("token")) {
							token = Item.getString("utf-8");
						}
						if (Item.getFieldName().equals("uid")) {
							uid = Item.getString("utf-8");
						}
						if (Item.getFieldName().equals("username")) {
							username = Item.getString("utf-8");
						}
						if (Item.getFieldName().equals("usersex")) {
							usersex = Item.getString("utf-8");
						}
						if (Item.getFieldName().equals("userbir")) {
							userbir = Item.getString("utf-8");
						}
						if (Item.getFieldName().equals("usersign")) {
							usersign = Item.getString("utf-8");
						}
						if (Item.getFieldName().equals("userstatus")) {
							userstatus = Item.getString("utf-8");
						}
						if (Item.getFieldName().equals("password")) {
							password = Item.getString("utf-8");
						}
					}
				}
			}
			
			// 获取完信息后
			// 判空
			Map<String, Object> user = null;
			
			if (token == null || "".equals(token)) {
				writer.write(NetCodeUtils.isToken());//未登录
				return;
			}
			if (tokenDao.queryRootByToken(token)==null) {
				writer.write(NetCodeUtils.ErrorParam());//非法调用
				return;
			}
			try {
				Integer.parseInt(uid);
			} catch (Exception e) {
				writer.write(NetCodeUtils.ErrorParam());//非法调用
				return;
			}
			//判断值是否为空
			if (uid == null) {
				writer.write(NetCodeUtils.ErrorParam());//非法调用
				return;
			}else if(userDao.findUserById(uid) == null){
				writer.write(NetCodeUtils.userIsNo());
				return;
			}else {
				user = userDao.findUserById(uid);
			}

			

			
			if (username == null || username.equals("")) {
				username = user.get("username").toString();
			}
			if (useravatar == null || useravatar.equals("")) {
				useravatar = user.get("useravatar").toString();
			}
			if (usersex == null || usersex.equals("")) {
				usersex = user.get("usersex").toString();
			}
			if (userbir == null || userbir.equals("")) {
				userbir = user.get("userbirth").toString();
			}
			if (usersign == null || username.equals("")) {
				usersign = user.get("usersign").toString();
			}
			if (userstatus == null || username.equals("")) {
				userstatus = user.get("userstatus").toString();
			}
			if (password == null || username.equals("")) {
				password = user.get("password").toString();
			}
			userDao.update(uid,username,useravatar,usersex,userbir,usersign,userstatus,password);
			
			jsonObject.put("code", "200");
			jsonObject.put("msg", "修改成功调用");
			writer.write(jsonObject.toJSONString());

		}
		catch (Exception e) {
			e.printStackTrace();
			writer.write(NetCodeUtils.ErrorParam());//非法调用
			return;
		}
		
		
		
	}

}
