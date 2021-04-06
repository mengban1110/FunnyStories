package cn.DoO.FrontEnd.api.dao.post;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Map;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;
import cn.DoO.Utils.Tools.DateUtils;

/**
* @desc 点赞指定帖子
* 
* @author TianShuo
* 
* @version 2021年4月1日 下午7:25:11
*/
public class PostDaolmpl {
	
	Dao dao = new DaoImpl();
	
	
	/**
	 * @desc 通过Token查询用户信息
	 * @param token
	 * @return
	 */
	public Map<String, Object> getUserByToken(String token) throws ClassNotFoundException, SQLException {
		Map<String, Object> data = null;

		String sql = "SELECT *	 FROM	 `user` WHERE usertoken=? ";
		data = dao.executeQueryForMap(sql, new int[] { Types.VARCHAR }, new Object[] { token });
		return data;
	}


	/**
	 * @desc 点赞指定帖子
	 * @param uid
	 * @param postid
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void likethis(int uid, String postid) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "INSERT INTO `like` VALUES(0,?,?,1,NULL)";
		dao.executeUpdate(sql, new int[] { Types.INTEGER,Types.INTEGER }, new Object[] { postid,uid });
	}

	/**
	 * @desc 评论指定帖子
	 * @param uid
	 * @param postid
	 * @param commenttext
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void comment(int uid, String postid, String commenttext) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
			//获取时间戳
			int time  = DateUtils.getSecondTimestamp(new Date());
			
			String sql = "INSERT INTO `comment` VALUES(0,?,?,?,?,1,NULL)";
		dao.executeUpdate(sql, new int[] { Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.VARCHAR}, new Object[] { postid,uid,time,commenttext });
	}
	
	/**
	 * @desc 判断此帖子是否存在
	 * @param postid
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
		public boolean getPostById(String postid) throws ClassNotFoundException, SQLException {
			Map<String, Object>  data = null;
	
			String sql = "SELECT *	 FROM	 `post` WHERE postid=? ";
			data = dao.executeQueryForMap(sql, new int[] { Types.INTEGER }, new Object[] { postid });
			if (data!=null) {
				return true;
			}
			else {
				return false;
			}
		}


	public void addlikethis(String postid) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		String sql = "SELECT postlike	 FROM	 `postdata` WHERE postid=? ";
		int postlike =  (int) dao.executeQueryForMap(sql, new int[] { Types.INTEGER }, new Object[] { postid }).get("postlike");
		postlike =postlike +1;
		System.out.println(postlike);
		sql = "UPDATE `postdata` SET postlike = ?  WHERE postid = ?";
		dao.executeUpdate(sql, new int[] { Types.INTEGER,Types.INTEGER }, new Object[]{postlike,postid});
	}
	public void addComment(String postid) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		String sql = "SELECT postcomment	 FROM	 `postdata` WHERE postid=? ";
		int postcomment =  (int) dao.executeQueryForMap(sql, new int[] { Types.INTEGER }, new Object[] { postid }).get("postcomment");
		postcomment =postcomment +1;
		System.out.println(postcomment);
		sql = "UPDATE `postdata` SET postcomment = ?  WHERE postid = ?";
		dao.executeUpdate(sql, new int[] { Types.INTEGER,Types.INTEGER }, new Object[]{postcomment,postid});
	}
	/**
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @desc 当前是否能评论帖子
	 */
	public boolean isNotComment() throws ClassNotFoundException, SQLException{
		
		  Map<String, Object> map = dao.executeQueryForMap("SELECT `comment` FROM serviceinfo WHERE serviceinfoid = 1 ");
		  int comment = (int)map.get("comment");
		  if (comment==0) 
		  {
			  return false;
		  }
		  else 
		  {
			  return true;
		  }
		
	}
}
