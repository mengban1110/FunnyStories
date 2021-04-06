package cn.DoO.FrontEnd.api.dao.post;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;

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
		
		String sql = "SELECT *,post.createtime ptime FROM post LEFT JOIN postdata ON post.postid=postdata.postid LEFT JOIN USER ON post.uid=user.uid WHERE post.display=1 and postdata.isaudit=1 ORDER BY RAND() LIMIT ?,?";
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
		String sql = "SELECT *,post.createtime ptime FROM post LEFT JOIN postdata ON post.postid=postdata.postid LEFT JOIN USER ON post.uid=user.uid WHERE postdata.isaudit=1 and post.display=1 AND post.placeid=3 GROUP BY post.postid DESC LIMIT ?,?";
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
		String sql = "SELECT *,post.createtime ptime FROM post LEFT JOIN postdata ON post.postid=postdata.postid LEFT JOIN USER ON post.uid=user.uid WHERE post.display=1 and postdata.isaudit=1 AND post.placeid=2 GROUP BY post.postid DESC LIMIT ?,?";
		return dao.executeQueryForList(sql, new int[]{Types.INTEGER,Types.INTEGER}, new Object[]{pageTemp,sizeTemp});
	}
	
	/**
	 * 获取文字帖子
	 * @param page
	 * @param size
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> queryTextPost(String page, String size) throws ClassNotFoundException, SQLException {
		int pageTemp = (Integer.parseInt(page)-1)*Integer.parseInt(size);
		int sizeTemp = Integer.parseInt(size);
		String sql = "SELECT *,post.createtime ptime FROM post LEFT JOIN postdata ON post.postid=postdata.postid LEFT JOIN USER ON post.uid=user.uid WHERE post.display=1 AND post.placeid=1  and postdata.isaudit=1 GROUP BY post.postid DESC LIMIT ?,?";
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
		String sql = "SELECT *,post.createtime ptime FROM post LEFT JOIN postdata ON post.postid=postdata.postid LEFT JOIN USER ON post.uid=user.uid WHERE post.display=1 and post.postid=? and postdata.isaudit=1";
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
		String sql = "select * from comment left join user on comment.uid=user.uid where comment.postid=? and comment.display=1";
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
	
	/**
	 * 根据id获取用户评论数量
	 * @param string
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int queryUserCommentCount(String string) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForInt("select count(*) from comment where uid=? and display=1", new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(string)});
	}
	
	/**
	 * 获取历史记录
	 * @param userid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public List<Map<String, Object>> queryHistoryPostById(String userid) throws NumberFormatException, ClassNotFoundException, SQLException {
		String sql = "SELECT *,bhistory.createtime btime from bhistory LEFT JOIN post on bhistory.postid=post.postid LEFT JOIN postdata on bhistory.postid=postdata.postid where bhistory.uid=? and bhistory.display=1 and post.display=1  and postdata.isaudit=1";
		return dao.executeQueryForList(sql, new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(userid)});
	}
	
	/**
	 * 获取自己的发帖
	 * @param userid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public List<Map<String, Object>> queryUserPostById(String userid) throws NumberFormatException, ClassNotFoundException, SQLException {
		String sql= "select *,post.createtime ptime from post LEFT JOIN postdata on post.postid=postdata.postid where post.uid=? and post.display=1 and postdata.isaudit=1 group by post.postid desc";
		return dao.executeQueryForList(sql, new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(userid)});
	}
	
	/**
	 * 查询帖子赞
	 * @param string
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int queryPostLike(String string) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForInt("select count(*) from `like` where postid=? and display=1", new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(string)});
	}
	
	/**
	 * 查询帖子评论数
	 * @param string
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int queryPostComment(String string) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForInt("select count(*) from comment where postid=? and display=1", new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(string)});
	}
	
	/**
	 * 查询用户评论
	 * @param userid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public List<Map<String, Object>> queryCommentById(String userid) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from comment where uid=? and display=1 group by commentid desc", new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(userid)});
	}
	
	/**
	 * 发帖
	 * @param uid
	 * @param content
	 * @param video
	 * @param string
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> addPost(String uid, String content, String video, String image) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		int placeid = 0;
		String createtime = System.currentTimeMillis()+"";
		System.out.println(image);
		System.out.println(video);
		System.out.println(content);
		if(image != null && !image.equals("[]")){
			placeid = 2;
		}
		else if(video != null && !video.equals("")){
			placeid = 3;
		}
		else if(content != null && !content.equals("")){
			placeid = 1;
		}
		System.out.println("所属专区:"+placeid);
		// 添加帖子
			// 有文本有图片
			if (content != null && !image.equals("[]") && video == null) {
				dao.executeUpdate("insert into post values(0,?,?,?,1,?,?,null,null)", new int[]{Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}, new Object[]{Integer.parseInt(uid),placeid,createtime,content,image});
			}
			// 有文本有视频
			else if (content != null && video != null && image.equals("[]")) {
				dao.executeUpdate("insert into post values(0,?,?,?,1,?,null,?,null)", new int[]{Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}, new Object[]{Integer.parseInt(uid),placeid,createtime,content,video});
			}
			// 只有视频
			else if ((content == null || content.equals("")) && video != null && image.equals("[]")) {
				dao.executeUpdate("insert into post values(0,?,?,?,1,null,null,?,null)", new int[]{Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.VARCHAR}, new Object[]{Integer.parseInt(uid),placeid,createtime,video});
			}
			// 只有图片
			else if ((content == null || content.equals("")) && video == null && !image.equals("[]")) {
				dao.executeUpdate("insert into post values(0,?,?,?,1,null,?,null,null)", new int[]{Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.VARCHAR}, new Object[]{Integer.parseInt(uid),placeid,createtime,image});
			}
			// 只有文本
			else if (content != null && video == null && image.equals("[]")) {
				dao.executeUpdate("insert into post values(0,?,?,?,1,?,null,null,null)", new int[]{Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.VARCHAR}, new Object[]{Integer.parseInt(uid),placeid,createtime,content});
			}
	
		return dao.executeQueryForMap("select * from post where uid=? and createtime=?", new int[]{Types.INTEGER,Types.VARCHAR}, new Object[]{Integer.parseInt(uid),createtime});
	
	}
	
	/**
	 * 添加帖子信息
	 * @param postid
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int addPostInfo(String postid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("insert into postdata values(0,?,0,0,0,0,0,0,null)", new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(postid)});
	}
}
