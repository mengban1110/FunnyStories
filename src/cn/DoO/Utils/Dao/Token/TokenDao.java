package cn.DoO.Utils.Dao.Token;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

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
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public int updateToken(String oldtoken, String newtoken) {
		String sql = "Update `root` set token=? WHERE token = ?";
		int result = 0;
		try {
			result = dao.executeUpdate(sql, new int[] { Types.VARCHAR,Types.VARCHAR }, new Object[] { newtoken,oldtoken });
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据token查询管理员
	 * 
	 * @param token
	 * @return
	 */
	public Map<String, Object> queryRootByToken(String token) {

		String sql = "SELECT * FROM `root` WHERE token = ?";
		Map<String, Object> data = null;
		try {
			data = dao.executeQueryForMap(sql, new int[] { Types.VARCHAR }, new Object[] { token });
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

}
