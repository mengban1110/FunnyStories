package cn.DoO.Utils.Dao.Token;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import cn.DoO.Utils.Dao.Dao;
import cn.DoO.Utils.Dao.DaoImpl;

/**
 * @Desc token验证、修改
 * @author 梦伴
 *
 */
public class TokenDao {

	Dao dao = new DaoImpl();

	/**
	 * @Desc 根据token 查询usermap
	 * @param token
	 * @return
	 */
	public Map<String, Object> queryUserByToken(String token) {

		String sql = "SELECT  * FROM user WHERE usertoken=?";
		Map<String, Object> data = null;
		try {
			data = dao.executeQueryForMap(sql, new int[] { Types.VARCHAR }, new Object[] { token });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * @Desc 增加token
	 * @return
	 */
	public int addToken(String uid, String utoken, String logintime, String userip) {
		return 0;
	};

	/**
	 * @Desc 更新token
	 * @return
	 */
	public int updateToken(String utoken, String nuoken) {
		return 0;
	}

}
