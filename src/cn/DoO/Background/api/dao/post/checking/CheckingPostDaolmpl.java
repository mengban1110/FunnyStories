package cn.DoO.Background.api.dao.post.checking;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

/**
* @desc 待审核的帖子的dao
* 
* @author TianShuo
* 
* @version 2021年3月6日 下午3:33:49
*/
public class CheckingPostDaolmpl {
	Dao dao = new DaoImpl();
	/**
	 * @desc 获取待审核的所有帖子
	 * @param page
	 * @param size
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> getAllCheckingPost(int page, int size) throws ClassNotFoundException, SQLException{	

		String sql = "SELECT 	post.* ,postplace.placeid,postplace.placename,postplace.placesign,postdata.*,`user`.*  FROM post LEFT JOIN `user` ON(post.uid = user.uid) LEFT JOIN postplace ON(post.placeid = postplace.placeid) LEFT JOIN postdata ON(post.postid = postdata.postid) where postdata.isaudit=0 LIMIT ?,?" ;
		List<Map<String,Object>> data = dao.executeQueryForList
				(
				sql, 
					new int[] { Types.INTEGER ,Types.INTEGER}, 
					new Object[] { (page-1)*size , size }
				);
		return data;
	}
	/**
	 * @desc 获取待审核的部分帖子的【非文本帖子】
	 * @param page
	 * @param size
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> getNotTextPost(int page, int size) throws ClassNotFoundException, SQLException{	

		String sql = "SELECT 	post.* ,postplace.placeid,postplace.placename,postplace.placesign,postdata.*,`user`.*  FROM post LEFT JOIN `user` ON(post.uid = user.uid) LEFT JOIN postplace ON(post.placeid = postplace.placeid) LEFT JOIN postdata ON(post.postid = postdata.postid) where postdata.isaudit=0 AND LENGTH(post.posttext) > 0 LIMIT ?,?" ;
		List<Map<String,Object>> data = dao.executeQueryForList
				(
				sql, 
					new int[] { Types.INTEGER ,Types.INTEGER}, 
					new Object[] { (page-1)*size , size }
				);
		return data;
	}
	/**
	 * @desc 获取待审核的部分帖子的【非文本帖子】的数量
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getNotTextPostNum() throws ClassNotFoundException, SQLException{	

		
		return dao.executeQueryForInt("SELECT COUNT(*) FROM post LEFT JOIN `user` ON(post.uid = user.uid) LEFT JOIN postplace ON(post.placeid = postplace.placeid) LEFT JOIN postdata ON(post.postid = postdata.postid) where postdata.isaudit=0 AND LENGTH(post.posttext) > 0");
	}
	/**
	 * @desc 获取待审核的【带关键字文本帖子】
	 * @param page
	 * @param size
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> getInfopartTextPost(int page, int size,String word) throws ClassNotFoundException, SQLException{	

		String sql = "SELECT 	post.* ,postplace.placeid,postplace.placename,postplace.placesign,postdata.*,`user`.*   FROM post LEFT JOIN `user` ON(post.uid = user.uid) LEFT JOIN postplace ON(post.placeid = postplace.placeid) LEFT JOIN postdata ON(post.postid = postdata.postid) where postdata.isaudit=0 AND  post.posttext like \"%" +word +"%\"  LIMIT ?,?" ;
		List<Map<String,Object>> data = dao.executeQueryForList
				(
						sql, 
						new int[] { Types.INTEGER ,Types.INTEGER}, 
						new Object[] { (page-1)*size , size }
					);
		return data;
	}
	public int getInfopartTextPostNum(String word) throws ClassNotFoundException, SQLException{	
		String sql = "SELECT COUNT(*) FROM post LEFT JOIN `user` ON(post.uid = user.uid) LEFT JOIN postplace ON(post.placeid = postplace.placeid) LEFT JOIN postdata ON(post.postid = postdata.postid) where postdata.isaudit=0 AND  post.posttext like \"%" +word +"%\"" ;
		return dao.executeQueryForInt(sql);
	}
	

	
	/**
	 * @desc 根据帖子id查询这个贴子信息
	 * @param postid
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String,Object> getOnePost(String postid) throws ClassNotFoundException, SQLException{	

		String sql = "SELECT * FROM post LEFT JOIN postdata ON ( post.postid = postdata.postid ) where  postdata.isaudit=0 AND post.postid= ? " ;
		Map<String,Object> data = dao.executeQueryForMap 
				(
						sql, 
						new int[] { Types.INTEGER }, 
						new Object[] { postid}
					);
		return data;
	}
	/**
	 * @desc 通过审核
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void upateAudit(String postid) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "UPDATE `postdata` SET isaudit = ? WHERE postid = ?";
		
		Object[] values = {"1",postid};
		
		int [] types = {Types.INTEGER,Types.INTEGER};
		
		dao.executeUpdate(sql, types, values);
	}
	/**
	 * @desc 删除未通过的帖子
	 * @param postid
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void delPost(String postid) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "delete from `postdata` WHERE postid = ?";
		String sql1 = "delete from `post` WHERE postid = ?";
		
		Object[] values = {postid};
		
		int [] types = {Types.INTEGER};
		
		dao.executeUpdate(sql, types, values);
		dao.executeUpdate(sql1, types, values);

	}
	/**
	 * @desc 添加后台日志
	 * @param rootid
	 * @param time
	 * @param content
	 * @param ip
	 * @param type
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void addLog(int rootid,int time ,String content , String ip ,String type ) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException
	{

		String sql  = "insert into log values(0,?,?,?,?,?,?)";
		Object[] values = {rootid,time,content,ip,type,""};
		
		int [] types = {Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR};
		
		dao.executeUpdate(sql, types, values);
	}
	/**
	 * @desc 获取待审核帖子的数量
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public  int getCount() throws ClassNotFoundException, SQLException {
		
		return dao.executeQueryForInt("SELECT count(*) FROM post LEFT JOIN `user` ON(post.uid = user.uid) LEFT JOIN postplace ON(post.placeid = postplace.placeid) LEFT JOIN postdata ON(post.postid = postdata.postid) where postdata.isaudit=0");
		
	}
	

}
