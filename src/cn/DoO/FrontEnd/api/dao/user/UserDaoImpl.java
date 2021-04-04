package cn.DoO.FrontEnd.api.dao.user;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

/**
* @desc 用户信息
* 
* @author TianShuo
* 
* @version 2021年4月1日 下午8:11:09
*/
public class UserDaoImpl {
	Dao dao = new DaoImpl();

	public Map<String, Object> getUserById(String uid) throws ClassNotFoundException, SQLException {
		Map<String, Object> data = null;

		String sql = "SELECT *	 FROM	 `user` WHERE uid=? ";
		data = dao.executeQueryForMap(sql, new int[] { Types.INTEGER }, new Object[] { uid });
		return data;
	}
	
	
	
}
