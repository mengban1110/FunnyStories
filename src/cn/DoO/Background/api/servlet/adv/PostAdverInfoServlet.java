package cn.DoO.Background.api.servlet.adv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Background.api.dao.adv.AdvertDao;
import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.NetCode.NetCodeUtils;
import cn.DoO.Utils.QiNiu.PutFile;

/**
 * 发布广告
 * 
 * @author 孙雨桐
 * 
 *         2021年3月7日11点52分
 */
public class PostAdverInfoServlet {

	TokenDao tokenDao = new TokenDao();
	AdvertDao advertDao = new AdvertDao();

	@SuppressWarnings({ "null", "unchecked" })
	public void postAdverInfo(HttpServletRequest request, HttpServletResponse response) {

		// json对象
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			System.out.println("printwriter获取异常");
		}

		if ("GET".equals(request.getMethod())) {
			writer.write(NetCodeUtils.otherErrMsg("-3", "请求方式有误"));// 未登录
			return;
		}
		// 接值
		String token = null;// token
		String acontext = null;// 广告文本内容

		FileItem aimgItem = null;// 广告图片流
		String aimg = null;// 广告图片地址
		String prifixTemp = null;// 后缀

		String fileName = null;// 文件名
		String prifix = null;// 文件后缀
		try {

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
						fileName = Item.getName();

						// 获取后缀
						prifix = fileName.substring(fileName.lastIndexOf(".") + 1);
						// 后缀全部转小写 防止后缀大小写不统一
						prifix = prifix.toLowerCase();

						// bmp,png,tif,gif和JPEG
						if (prifix.equals("png") || prifix.equals("jpg") || prifix.equals("bmp") || prifix.equals("tif")
								|| prifix.equals("gif") || prifix.equals("jpeg")) {

							// 如果已经获取过
							if (aimgItem != null) {
								jsonObject = new JSONObject();
								jsonObject.put("code", "-1");
								jsonObject.put("msg", "只能上传一张头像");
								writer.write(jsonObject.toJSONString());
								return;
							}
							// 获取图片流
							aimgItem = Item;
							// 获取图片后缀
							prifixTemp = prifix;

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
						if (Item.getFieldName().equals("acontext")) {
							acontext = Item.getString("utf-8");
						}
					}
				}
			}

			if (token == null || "".equals(token)) {
				writer.write(NetCodeUtils.ErrorParam());// 非法调用
				return;
			}
			if (acontext == null || "".equals(acontext)) {
				writer.write(NetCodeUtils.ErrorParam());// 非法调用
				return;
			}
			if (tokenDao.queryRootByToken(token) == null) {
				writer.write(NetCodeUtils.ErrorParam());// 非法调用
				return;
			}

			System.out.println("上传文件的名字:" + aimgItem.getName());
			System.out.println("上传文件的后缀:" + prifixTemp);
			// 所有判断完毕再上传头像--优化
			System.out.println("所有判断完成 --- 开始上传");
			aimg = PutFile.Putimgs(aimgItem.getInputStream(), prifixTemp);
			System.out.println("上传成功");
			System.out.println(aimg);

			advertDao.saveAdverInfo(acontext, aimg);
			jsonObject.put("code", "200");
			jsonObject.put("msg", "发布成功");
			writer.write(jsonObject.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
			writer.write(NetCodeUtils.ErrorParam());// 非法调用
			return;
		}

	}
}
