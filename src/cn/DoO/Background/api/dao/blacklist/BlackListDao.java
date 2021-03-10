package cn.DoO.Background.api.dao.blacklist;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

public class BlackListDao {
	Dao dao = new DaoImpl();

	/**
	 * @desc 取消黑名单
	 * @param uid
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void cancel(int uid) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		dao.executeUpdate("update  blacklist set display=0");
		dao.executeUpdate("update user set userstatus=1 where uid = ?", new int[] { Types.INTEGER },
				new Object[] { uid });

	}

	public Map<String, Object> query(int uid) throws ClassNotFoundException, SQLException {

		return dao.executeQueryForMap("select * from blacklist where uid =?", new int[] { Types.INTEGER },
				new Object[] { uid });
	}

	/**
	 * @desc 获取部分黑名单
	 * @param word
	 * @param page
	 * @param size
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Map<String, Object>> findBypart(String word, int page, int size)
			throws ClassNotFoundException, SQLException {
		if (word == null) {
			word = "";
		}

		String sql = "select u.uid,u.username,u.useravatar,b.bid,b.createtime from blacklist b left join user u on u.uid=b.uid where u.username like ? and display!=0 LIMIT ?,?";

		return dao.executeQueryForList(sql, new int[] { Types.VARCHAR, Types.INTEGER, Types.INTEGER },
				new Object[] { "%" + word + "%", (page - 1) * size, size });
	}
	
	/**
	 * 添加指定用户到黑名单
	 * @param uid
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void addBlackUser(Integer uid) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		String sql = "INSERT INTO blacklist VALUES(0,?,?,1,NULL)";
		
		int []types = {Types.VARCHAR,Types.VARCHAR};
		
		Object[]values = {uid,System.currentTimeMillis()/1000};
		
		dao.executeUpdate(sql, types, values);
	}

}
