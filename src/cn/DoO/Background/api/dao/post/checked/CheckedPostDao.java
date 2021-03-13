package cn.DoO.Background.api.dao.post.checked;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

public class CheckedPostDao {

	Dao dao = new DaoImpl();

	/**
	 * 根据 页数多表联查
	 * 
	 * @param page
	 * @param size
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findAllByPageAndSize(int page, int size)
			throws ClassNotFoundException, SQLException {

		String sql = "SELECT u.username,u.useravatar,pp.placeid,pp.placename,p.postid,p.posttext,p.postimg,p.postvideo,p.createtime,pd.postlike,pd.postshare,pd.postcomment FROM post p LEFT JOIN `user` u ON p.uid=u.uid LEFT JOIN postplace pp ON pp.placeid =p.placeid LEFT JOIN postdata pd ON pd.postid = p.postid WHERE p.display!=0 AND pd.isrecommend !=1 AND pd.isaudit !=0 LIMIT ?,?";

		int[] types = { Types.INTEGER, Types.INTEGER };

		Object[] values = { (page - 1) * size, size };

		return dao.executeQueryForList(sql, types, values);
	}

	/**
	 * 测试
	 * 
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
		CheckedPostDao CheckedPostDao = new CheckedPostDao();
		JSONObject jsonObject = new JSONObject();

		List<Map<String, Object>> dataList = CheckedPostDao.findAllByPageAndSize(1, 2);

//		SimpleDateFormat formatter;
//		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		List<JSONObject> postinfo = new ArrayList<JSONObject>();
//		Map<String, Object> userinfo = null;
//		Map<String, Object> place = null;
//		Map<String, Object> count = null;
//		JSONObject jsonObject2 = null;
//		Date date;
//		for (Map<String, Object> map : dataList) {
//			jsonObject2 = new JSONObject();
//			userinfo = new HashMap<String, Object>();
//			place = new HashMap<String, Object>();
//			count = new HashMap<String, Object>();
//			
//			userinfo.put("uname",map.get("username"));
//			userinfo.put("useravatar", map.get("useravatar"));
//			jsonObject2.put("userinfo", userinfo);
//			
//			place.put("placeid", map.get("placeid"));
//			place.put("placename", map.get("placename"));
//			jsonObject2.put("place", place);
//			
//			count.put("like", map.get("postlike"));
//			count.put("share", map.get("postshare"));
//			count.put("comment", map.get("postcomment"));
//			jsonObject2.put("count", count);
//			
//			jsonObject2.put("postid", map.get("postid"));
//			jsonObject2.put("posttext", map.get("posttext"));
//			jsonObject2.put("postimg", map.get("postimg"));
//			jsonObject2.put("postvideo", map.get("postvideo"));
//			jsonObject2.put("createtime", formatter.format(new Date(Long.parseLong((String)map.get("createtime")))));
//			
//			postinfo.add(jsonObject2);
//		}
//		
//		JSONObject jsonObject3 = new JSONObject();
//		jsonObject3.put("postinfo", postinfo);
//		
//		jsonObject.put("code", 200);
//		jsonObject.put("msg", "获取成功");
//		jsonObject.put("data", jsonObject3);
//		System.out.println(jsonObject);
		dataList = CheckedPostDao.getCommendByPostId("1", "", 1, 99);
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<JSONObject> comments = new ArrayList<JSONObject>();
		Map<String, Object> userinfo = null;
		JSONObject jsonObject2 = null;
		for (Map<String, Object> map : dataList) {
			jsonObject2 = new JSONObject();
			userinfo = new HashMap<String, Object>();

			userinfo.put("uname", map.get("username"));
			userinfo.put("useravatar", map.get("useravatar"));
			jsonObject2.put("userinfo", userinfo);

			int likecount = CheckedPostDao.getLikeCountByComment((int) map.get("commentid"));
			System.out.println(likecount);
			jsonObject2.put("likecount", likecount);
			jsonObject2.put("commentid", map.get("commentid"));
			jsonObject2.put("commenttext", map.get("commenttext"));
			jsonObject2.put("createtime", formatter.format(new Date(Long.parseLong((String) map.get("createtime")))));

			comments.add(jsonObject2);
		}

		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("comments", comments);

		jsonObject.put("code", 200);
		jsonObject.put("msg", "获取成功");
		jsonObject.put("data", jsonObject3);
		System.out.println(jsonObject);

	}

	/**
	 * 模块查询帖子
	 * 
	 * @param page
	 * @param size
	 * @param word
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findAllByWord(int page, int size, String word)
			throws ClassNotFoundException, SQLException {
		System.out.println(page + "page");
		System.out.println(word + "word");
		System.out.println(size + "size");
		String sql = null;
		Object[] values = null;
		int[] types = null;
		if (word == null || "".equals(word)) {
			sql = "SELECT u.username,u.useravatar,pp.placeid,pp.placename,p.postid,p.posttext,p.postimg,p.postvideo,p.createtime,pd.postlike,pd.postshare,pd.postcomment FROM post p LEFT JOIN `user` u ON p.uid=u.uid LEFT JOIN postplace pp ON pp.placeid =p.placeid LEFT JOIN postdata pd ON pd.postid = p.postid WHERE p.display!=0 AND pd.isrecommend !=1 AND pd.isaudit !=0 AND p.posttext = '' OR ISNULL(p.posttext)  LIMIT ?,?";
			types = new int[] { Types.INTEGER, Types.INTEGER };
			values = new Object[] { (page - 1) * size, size };
		} else {
			sql = "SELECT u.username,u.useravatar,pp.placeid,pp.placename,p.postid,p.posttext,p.postimg,p.postvideo,p.createtime,pd.postlike,pd.postshare,pd.postcomment FROM post p LEFT JOIN `user` u ON p.uid=u.uid LEFT JOIN postplace pp ON pp.placeid =p.placeid LEFT JOIN postdata pd ON pd.postid = p.postid WHERE p.display!=0 AND pd.isrecommend !=1 AND pd.isaudit !=0 AND p.posttext LIKE ? LIMIT ?,?";
			types = new int[] { Types.VARCHAR, Types.INTEGER, Types.INTEGER };
			values = new Object[] { "%" + word + "%", (page - 1) * size, size };
		}

		return dao.executeQueryForList(sql, types, values);
	}

	/**
	 * 设置帖子为推荐帖子
	 * 
	 * @param postid
	 * @throws IOException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	public void commendPost(String postid)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {

		String sql = "UPDATE postdata SET isrecommend = 1 WHERE postid = ?";

		Object[] values = { postid };

		int[] types = { Types.INTEGER };

		dao.executeUpdate(sql, types, values);
	}

	/**
	 * 删除帖子（修改display）
	 * 
	 * @param postid
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void deletePost(String postid)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "UPDATE post SET display = 0 WHERE postid = ?";

		Object[] values = { postid };

		int[] types = { Types.INTEGER };

		dao.executeUpdate(sql, types, values);
	}

	/**
	 * 根据 帖子id 获取该帖子的所有评论
	 * 
	 * @param word
	 * @param postid
	 * @param page
	 * @param size
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getCommendByPostId(String postid, String word, int page, int size)
			throws ClassNotFoundException, SQLException {

		if (word == null) {
			word = "";
		}

		String sql = "SELECT c.commentid,c.createtime,c.commenttext,u.username,u.useravatar,u.uid FROM `comment` c LEFT JOIN `user` u ON u.uid = c.uid WHERE c.postid = ? AND c.display != 0 AND c.commenttext LIKE ? LIMIT ?,?";

		Object[] values = { postid, "%" + word + "%", ((page - 1) * size), size };

		int[] types = { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER };

		return dao.executeQueryForList(sql, types, values);
	}

	/**
	 * 根据 comment id 查询 点赞评论
	 * 
	 * @param i
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getLikeCountByComment(int i) throws ClassNotFoundException, SQLException {
		String sql = "SELECT COUNT(likecomid) likecount FROM comentlike WHERE commentid = ? AND display != 0";

		Object[] values = { i };

		int[] types = { Types.INTEGER };

		return dao.executeQueryForInt(sql, types, values);
	}

	/**
	 * 根据commentid 删除帖子
	 * 
	 * @param commentid
	 * @throws IOException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	public void deleteComment(String commentid)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "UPDATE `comment` SET display = 0 WHERE commentid = ?";

		Object[] values = { commentid };

		int[] types = { Types.INTEGER };

		dao.executeUpdate(sql, types, values);
	}

	public int getCount() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForInt("SELECT count(*) FROM post p LEFT JOIN `user` u ON p.uid=u.uid LEFT JOIN postplace pp ON pp.placeid =p.placeid LEFT JOIN postdata pd ON pd.postid = p.postid WHERE p.display!=0 AND pd.isrecommend !=1 AND pd.isaudit !=0");
	}

}
