package cn.DoO.FrontEnd.api.dao.post;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	
	/**
	 * 根据id查询post
	 * @param postid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryPostById(String postid) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from post where postid=? and display=1", new int[]{Types.INTEGER},new Object[]{Integer.parseInt(postid)});
	}
	
	/**
	 * 根据id查询帖子详细信息
	 * @param postid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryPostInfoById(String postid) throws NumberFormatException, ClassNotFoundException, SQLException {
		String sql = "SELECT *,post.createtime ptime FROM post LEFT JOIN postdata ON post.postid=postdata.postid LEFT JOIN USER ON post.uid=user.uid WHERE post.display=1 and post.postid=?";
		return dao.executeQueryForMap(sql, new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(postid)});
	}
	
	/**
	 * 根据帖子id查询评论
	 * @param postid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public List<Map<String, Object>> queryPingLunByPostId(String postid) throws NumberFormatException, ClassNotFoundException, SQLException {
		String sql = "select * from comment left join user on comment.uid=user.uid where comment.postid=? and comment.display=1 group by comment.postid desc";
		return dao.executeQueryForList(sql, new int[]{Types.INTEGER},new Object[]{Integer.parseInt(postid)});
	}
	
	/**
	 * 根据id查询用户发帖数量
	 * @param string
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */ 	
	public int queryUserPostCount(String id) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForInt("select count(*) from post where postid=? and display=1", new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(id)});
	}
	
	/**
	 * 根据用户id查询所有帖子被点赞数量
	 * @param string
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int queryUserPostZanCount(String id) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForInt("select count(*) from `like` where postid=? and display=1", new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(id)});
	}
	
	/**
	 * 添加历史记录
	 * @param uid
	 * @param postid
	 * @param placeid
	 * @return
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public int addHistory(String uid, String postid, String placeid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("insert into bhistory values(0,?,?,?,?,1,null)", new int[]{Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.VARCHAR}, new Object[]{Integer.parseInt(uid),Integer.parseInt(postid),Integer.parseInt(placeid),System.currentTimeMillis()+""});
	}
}
