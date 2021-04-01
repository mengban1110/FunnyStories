package cn.DoO.FrontEnd.api.dao.post;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

/**
 * 帖子模块Dao
 * @author LYP
 *
 */
public class PostDao {
	Dao dao = new DaoImpl();
	
	/**
	 * 获取推荐帖子
	 * @param page
	 * @param size
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> queryRandPost(String page, String size) throws ClassNotFoundException, SQLException {
		
		int pageTemp = (Integer.parseInt(page)-1)*Integer.parseInt(size);
		int sizeTemp = Integer.parseInt(size);
		
		String sql = "SELECT * FROM post LEFT JOIN postdata ON post.postid=postdata.postid LEFT JOIN USER ON post.uid=user.uid WHERE post.display=1 ORDER BY RAND() LIMIT ?,?";
		return dao.executeQueryForList(sql, new int[]{Types.INTEGER,Types.INTEGER}, new Object[]{pageTemp,sizeTemp});
	
	}
	
	/**
	 * 获取视频贴
	 * @param page
	 * @param size
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryVideoPost(String page, String size) throws ClassNotFoundException, SQLException {
		int pageTemp = (Integer.parseInt(page)-1)*Integer.parseInt(size);
		int sizeTemp = Integer.parseInt(size);
		String sql = "SELECT * FROM post LEFT JOIN postdata ON post.postid=postdata.postid LEFT JOIN USER ON post.uid=user.uid WHERE post.display=1 AND post.placeid=3 GROUP BY post.postid DESC LIMIT ?,?";
		return dao.executeQueryForList(sql, new int[]{Types.INTEGER,Types.INTEGER}, new Object[]{pageTemp,sizeTemp});
	}	

	/**
	 * 获取图片贴
	 * @param page
	 * @param size
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> queryPhotoPost(String page, String size) throws ClassNotFoundException, SQLException {
		int pageTemp = (Integer.parseInt(page)-1)*Integer.parseInt(size);
		int sizeTemp = Integer.parseInt(size);
		String sql = "SELECT * FROM post LEFT JOIN postdata ON post.postid=postdata.postid LEFT JOIN USER ON post.uid=user.uid WHERE post.display=1 AND post.placeid=2 GROUP BY post.postid DESC LIMIT ?,?";
		return dao.executeQueryForList(sql, new int[]{Types.INTEGER,Types.INTEGER}, new Object[]{pageTemp,sizeTemp});
	}
	
	/**
	 * 获取图片帖子
	 * @param page
	 * @param size
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> queryTextPost(String page, String size) throws ClassNotFoundException, SQLException {
		int pageTemp = (Integer.parseInt(page)-1)*Integer.parseInt(size);
		int sizeTemp = Integer.parseInt(size);
		String sql = "SELECT * FROM post LEFT JOIN postdata ON post.postid=postdata.postid LEFT JOIN USER ON post.uid=user.uid WHERE post.display=1 AND post.placeid=1 GROUP BY post.postid DESC LIMIT ?,?";
		return dao.executeQueryForList(sql, new int[]{Types.INTEGER,Types.INTEGER}, new Object[]{pageTemp,sizeTemp});
	}
}
