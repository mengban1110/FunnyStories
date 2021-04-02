package cn.DoO.FrontEnd.api.servlet.post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.DoO.FrontEnd.api.dao.post.PostDao;
import cn.DoO.Utils.QiNiu.PutFile;
import sun.net.www.content.text.plain;

/**
 * @desc   上传线程
 * @author JZH
 * @time   2021年2月1日
 */  
public class SendPostThread implements Runnable{
	//视频地址
	String video;
	//视频流
	FileItem videoItem;
	//视频后缀
	String videoPrifix;
	//图片地址
	List image;
	//图片流
	List<FileItem> imageItems;
	//图片后缀
	List<String> prifixList;
	//uid
	String uid;
	//文字信息
	String content;
	
	PutFile putfile = new PutFile();
	//postDao
	PostDao postdao;

	
	
	
	public SendPostThread(String video, FileItem videoItem, String videoPrifix, List image, List<FileItem> imageItems,
			List<String> prifixList, String uid, String content,  PostDao postdao) {
		super();
		this.video = video;
		this.videoItem = videoItem;
		this.videoPrifix = videoPrifix;
		this.image = image;
		this.imageItems = imageItems;
		this.prifixList = prifixList;
		this.uid = uid;
		this.content = content;
		this.postdao = postdao;
	}




	@Override
	public void run() {
		try {
			System.out.println("！！！！！！！！！！！！！！！！！！！！！！！开始上传");
			//上传图片
			for (int i = 0; i < imageItems.size(); i++) {
				image.add(putfile.Putimgs(imageItems.get(i).getInputStream(), prifixList.get(i)));
			}
			//上传视频
			if (videoItem != null) {
				video = putfile.Putimgs(videoItem.getInputStream(), videoPrifix);
			}
			// 图片数组转json串
			JSONArray jsonArray = new JSONArray(image);
			
			// post表+数据
			Map<String, Object> PostMessage = postdao.addPost(uid,  content, video, jsonArray.toString());
			System.out.println(PostMessage);
			
			// 根据返回的帖子信息添加postinfo
			if (PostMessage != null) {
				String postid = PostMessage.get("postid").toString();
				System.out.println(postid);
				//添加帖子信息
				int count = postdao.addPostInfo(postid);
				System.out.println(count);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
