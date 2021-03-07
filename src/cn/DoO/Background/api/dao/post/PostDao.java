package cn.DoO.Background.api.dao.post;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;


public class PostDao {

	Dao dao = new DaoImpl();
	
	
	/**
	 * 根据 页数多表联查
	 * @param page
	 * @param size
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findAllByPageAndSize(int page, int size) throws ClassNotFoundException, SQLException {
		
		String sql = "SELECT u.username,u.useravatar,pp.placeid,pp.placename,p.postid,p.posttext,p.postimg,p.postvideo,p.createtime,pd.postlike,pd.postshare,pd.postcomment FROM post p LEFT JOIN `user` u ON p.uid=u.uid LEFT JOIN postplace pp ON  p.placeid =pp.placeid LEFT JOIN postdata pd ON pd.postid = p.postid WHERE p.display!=0 AND pd.isrecommend !=1 LIMIT ?,?";
		
		int [] types = {Types.INTEGER,Types.INTEGER};
		
		Object[] values = {(page-1)*size, size};
		
		return dao.executeQueryForList(sql, types, values);
	}

	
	/**
	 * 测试
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
//	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
//		PostDao CheckedPostDao = new PostDao();
//		JSONObject jsonObject = new JSONObject();
//		
//		List<Map<String, Object>> dataList = CheckedPostDao.findAllByPageAndSize(1, 2);
//		
//		
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
//	}






	/***
	 * @desc    获取推荐的全部帖子
	 * @param page
	 * @param size
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */

	public List<Map<String, Object>> findAllByIsrecommend(int page, int size) throws ClassNotFoundException, SQLException {
		
		String sql = "SELECT u.username,u.useravatar,pp.placeid,pp.placename,p.postid,p.posttext,p.postimg,p.postvideo,p.createtime,pd.postlike,pd.postshare,pd.postcomment FROM post p LEFT JOIN `user` u ON p.uid=u.uid LEFT JOIN postplace pp ON  p.placeid =pp.placeid LEFT JOIN postdata pd ON pd.postid = p.postid WHERE p.display!=0 AND pd.isrecommend =1 LIMIT ?,?";
		
		int [] types = {Types.INTEGER,Types.INTEGER};
		
		Object[] values = {(page-1)*size, size};
		
		return dao.executeQueryForList(sql, types, values);
	
	}


	/**
	 * @desc    获取部分帖子
	 * @param word
	 * @param page
	 * @param size
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> findAllBypart(String word, int page, int size) throws ClassNotFoundException, SQLException {
	
		String sql = null;
		Object [] values=null;
		int[] types = null;
		if (word ==null || "".equals(word)) {
			sql = "SELECT u.username,u.useravatar,pp.placeid,pp.placename,p.postid,p.posttext,p.postimg,p.postvideo,p.createtime,pd.postlike,pd.postshare,pd.postcomment FROM post p LEFT JOIN `user` u ON p.uid=u.uid LEFT JOIN postplace pp ON pp.placeid =p.placeid LEFT JOIN postdata pd ON pd.postid = p.postid WHERE p.display!=0 AND pd.isrecommend =1 AND p.posttext = '' OR ISNULL(p.posttext)  LIMIT ?,?";
			types =new int[]{Types.INTEGER,Types.INTEGER};
			values = new Object[]{(page-1)*size, size};
		}else{
			sql = "SELECT u.username,u.useravatar,pp.placeid,pp.placename,p.postid,p.posttext,p.postimg,p.postvideo,p.createtime,pd.postlike,pd.postshare,pd.postcomment FROM post p LEFT JOIN `user` u ON p.uid=u.uid LEFT JOIN postplace pp ON pp.placeid =p.placeid LEFT JOIN postdata pd ON pd.postid = p.postid WHERE p.display!=0 AND pd.isrecommend =1 AND p.posttext LIKE ? LIMIT ?,?";
			types =new int[]{Types.VARCHAR,Types.INTEGER,Types.INTEGER};
			values = new Object[]{"%"+word+"%",(page-1)*size, size};
		}
		
		return dao.executeQueryForList(sql, types, values);
	}


	/**
	 * @desc    取消推荐
	 * @param postid
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public void cancel(int postid) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao.executeUpdate("update postdata set isrecommend=0 where postid=?", new int[]{Types.INTEGER}, new Object[]{postid});
	
	}

	/**
	 * @desc    查询是否存在该帖子
	 * @param postid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> query(int postid) throws ClassNotFoundException, SQLException {
		String sql="select * from post where postid=?";
		
		return dao.executeQueryForMap(sql, new int[]{Types.INTEGER}, new Object[]{postid});
	}
	
}
