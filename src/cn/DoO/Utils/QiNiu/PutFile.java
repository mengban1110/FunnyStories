package cn.DoO.Utils.QiNiu;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * 
 * @desc 上传文件测试类
 * @author 
 * @time  
 */ 
public class PutFile {

	private static String accessKey = null;
	private static String secretKey = null;
	private static String bucket = null;
	
	static {
		try {
			Properties prop = new Properties();
			ResourceBundle myResources = ResourceBundle.getBundle("cn.DoO.Config.qiniu");
			accessKey = myResources.getString("accessKey");
			secretKey = myResources.getString("secretKey");
			bucket = myResources.getString("bucket");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * @desc 上传文件
	 * @param ImgIO  InputStream
	 * @param suffix 文件后缀
	 * @return
	 */
	public static String Putimgs(InputStream ImgIO, String suffix) {
		Configuration cfg = new Configuration();
		UploadManager uploadManager = new UploadManager(cfg);
		
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = SendInfo.GetUUID() + System.currentTimeMillis() + "." + suffix;
		try {
			// 设置key认证
			Auth auth = Auth.create(accessKey, secretKey);
			
			// 获取仓库名
			String upToken = auth.uploadToken(bucket);

			try {
				//
				// 上传
				Response response = uploadManager.put(ImgIO, key, upToken, null, null);
				// 解析上传成功的结果
				DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
				System.out.println(putRet.key);

				// System.out.println(putRet.hash);
				// 返回文件名
				return "http://file.dreamoforiginal.cn/" + putRet.key;
			} catch (QiniuException ex) {
				Response r = ex.response;
				System.err.println(r.toString());
				try {
					System.err.println(r.bodyString());
				} catch (QiniuException ex2) {
					// ignore
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

//		byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
//		System.out.println(Putimgs(byteInputStream, "txt"));
//		System.out.println();
		System.out.println(accessKey);
		System.out.println(secretKey);
		System.out.println(bucket);

	}

}
