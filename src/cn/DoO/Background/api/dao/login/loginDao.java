package cn.DoO.Background.api.dao.login;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

/**
 * @desc 登陆的查询
 * @author 齐云尧
 *
 */
public class loginDao {

	Dao dao = new DaoImpl();

	/**
	 * @desc 查询管理员名称
	 * @param username
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getUserByU(String rootname) throws ClassNotFoundException, SQLException {
		String sql = "select count(*) from root where rootname=?";
		return dao.executeQueryForInt(sql, new int[] { Types.VARCHAR }, new Object[] { rootname });
	}

	/**
	 * @desc 查询该管理员的密码
	 * @param userName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getpasswordByU(String rootName) throws ClassNotFoundException, SQLException {
		String sql = "select * from root where rootname=?";
		return dao.executeQueryForMap(sql, new int[] { Types.VARCHAR }, new Object[] { rootName });
	}

}
