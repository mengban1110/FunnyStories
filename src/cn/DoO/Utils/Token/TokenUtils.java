package cn.DoO.Utils.Token;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
	 * @return 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public static String updateToken(String token, String email) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String newtoken = getToken(email);
		t.updateToken(token, newtoken);
		return newtoken;
	}

}
