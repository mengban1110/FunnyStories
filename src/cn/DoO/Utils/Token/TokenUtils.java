package cn.DoO.Utils.Token;

import java.util.UUID;

import cn.DoO.Utils.Dao.Token.TokenDao;
import cn.DoO.Utils.Tools.Md5Utils;

/**
 * 1 : 生成token---getToken(String email)
 * <p>
 * 
 * @author 梦伴
 *
 */
public class TokenUtils {

	static TokenDao t = new TokenDao();

	/**
	 * @Desc 生成token
	 * @param email
	 * @return
	 */
	public static String getToken(String email) {
		String data = UUID.randomUUID().toString() + email;
		return Md5Utils.makeMd5(data);
	}

	/**
	 * @Desc 更新 token
	 * @param token
	 */
	public static void updateToken(String token, String email) {
		t.updateToken(token, getToken(email));
	}
}
