package cn.DoO.Background.api.dao.homepage;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

/**
* @desc 管理员查询
* 
* @author TianShuo
* 
* @version 2021年3月4日 下午5:40:57
*/
public class RootDaoImpl {
	
	Dao dao = new DaoImpl();
	
	/**
	 * @desc  通过Token查询用户信息 
	 * @param token
	 * @return
	 */
	public Map<String, Object> getUserByToken(String token) throws ClassNotFoundException , SQLException {
			Map<String, Object> data = null;

			String sql = "SELECT *	 FROM	 `root` WHERE token=? ";
				data = dao.executeQueryForMap
						(
						sql, 
						new int[] { Types.VARCHAR }, 
						new Object[] { token }
						);
			return data;
	}
	

	/**
	 * @Desc 获取用户数
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getUserCount() throws ClassNotFoundException, SQLException {
		Map<String, Object> data = dao.executeQueryForMap(" SELECT count(*) count FROM user");
		return data;
	}
	
	/**
	 * @Desc 未审核帖子数
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getAuditsCount() throws ClassNotFoundException, SQLException{
		Map<String, Object> data = dao.executeQueryForMap("SELECT count(*) count FROM postdata WHERE isaudit=0");
		return data;
	}
	
	/**
	 * @Desc 帖子数
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getPostsCount() throws ClassNotFoundException, SQLException{
		Map<String, Object> data = dao.executeQueryForMap("SELECT count(*) count FROM post");
		return data;
	}
	/**
	 * @Desc 时间排序 获取最后五个 log表中的数据
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> getAllShop() throws ClassNotFoundException, SQLException{	
		List<Map<String, Object>> data = dao.executeQueryForList("SELECT * FROM log ORDER BY logid  DESC LIMIT 5");
		return data;
	}

	/**
	 * @desc 根据 管理员用户ID查询此管理员用户信息
	 * @param rootid
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getRootById(String rootid) throws ClassNotFoundException, SQLException {
		Map<String, Object> data = null;
		String sql = "SELECT *	 FROM	 `root` WHERE rootid=? ";
		data = dao.executeQueryForMap
				(
				sql, 
				new int[] { Types.INTEGER }, 
				new Object[] { rootid }
				);
		return data;
	}

	/**
	 * @desc 查询用户登录记录 前5条
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getAllUserlogs() throws ClassNotFoundException, SQLException {
		List<Map<String, Object>> data = dao.executeQueryForList("SELECT * FROM userlogin ORDER BY userloginid  DESC LIMIT 5");
		return data;
	}

	/**
	 * @desc 根据 用户ID查询此用户信息
	 * @param uid
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getUserById(String uid) throws ClassNotFoundException, SQLException {
		Map<String, Object> data = null;
		String sql = "SELECT *	 FROM	 `user` WHERE uid=? ";
		data = dao.executeQueryForMap
				(
				sql, 
				new int[] { Types.INTEGER }, 
				new Object[] { uid }
				);
		return data;
	}

	/**
	 * @desc 获取五个热词
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getAllSearchFrom5() throws ClassNotFoundException, SQLException {
		List<Map<String, Object>> data = dao.executeQueryForList("select * ,count(*) as c from shistory group by word ORDER BY c DESC LIMIT 5");
		return data;
	}
	/**
	 * @desc 获取视频帖数量
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getPostVideoCount() throws ClassNotFoundException, SQLException {
		Map<String, Object> data = dao.executeQueryForMap("SELECT\n" +
				"	Count(*) \n" +
				"FROM\n" +
				"	post \n" +
				"WHERE\n" +
				"	postvideo IS NOT NULL");
		return data;
	}
	/**
	 * @desc 获取文本贴子数量
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getPostTextCount() throws ClassNotFoundException, SQLException {
		Map<String, Object> data = dao.executeQueryForMap("SELECT\n" +
				"	Count(*) \n" +
				"FROM\n" +
				"	post \n" +
				"WHERE\n" +
				"	postvideo IS  NULL and\n" +
				"	postimg is NULL\n" +
				"	");
				
		return data;
	}
	/**
	 * @desc 获取图片贴子数量
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getPostImgCount() throws ClassNotFoundException, SQLException {
		Map<String, Object> data = dao.executeQueryForMap("SELECT\n" +
				"	Count(*) \n" +
				"FROM\n" +
				"	post \n" +
				"WHERE\n" +
				"	postvideo IS  NULL and\n" +
				"	postimg is not NULL");
		return data;
	}
	
}
