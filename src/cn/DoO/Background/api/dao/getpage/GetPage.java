package cn.DoO.Background.api.dao.getpage;

import java.sql.SQLException;
import java.sql.Types;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

/**
 * @desc    获取页数
 * @author 云尧
 *
 */
public class GetPage {
	
	static Dao dao=new DaoImpl();
	/**
	 * @desc    获取推荐帖子的总页数
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int getInfoPartPage() throws ClassNotFoundException, SQLException{
		int page =dao.executeQueryForInt("SELECT  count(*) FROM post p LEFT JOIN `user` u ON p.uid=u.uid LEFT JOIN postplace pp ON  p.placeid =pp.placeid LEFT JOIN postdata pd ON pd.postid = p.postid WHERE p.display!=0 AND pd.isrecommend =1 ");
		return page;
		
	}
	
	
	/**
	 * @desc    获取部分推荐的帖子总页数
	 * @param word
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int getInfoPage(String word) throws ClassNotFoundException, SQLException{
		int page = 0;
		String sql = null;
		Object [] values=null;
		int[] types = null;
		if (word ==null || "".equals(word)) {
			page=dao.executeQueryForInt("SELECT count(*) FROM post p LEFT JOIN `user` u ON p.uid=u.uid LEFT JOIN postplace pp ON pp.placeid =p.placeid LEFT JOIN postdata pd ON pd.postid = p.postid WHERE p.display!=0 AND pd.isrecommend =1 AND p.posttext = '' OR ISNULL(p.posttext)");
		
			
		}else{
			sql = "SELECT count(*) FROM post p LEFT JOIN `user` u ON p.uid=u.uid LEFT JOIN postplace pp ON pp.placeid =p.placeid LEFT JOIN postdata pd ON pd.postid = p.postid WHERE p.display!=0 AND pd.isrecommend =1 AND p.posttext LIKE ?";
			types =new int[]{Types.VARCHAR};
			values = new Object[]{"%"+word+"%"};
			page = dao.executeQueryForInt(sql, types, values);
		}
		
		return page;
	
		
	}
	
	
	
	/**
	 * @desc    获取黑名单总页数
	 * @param word
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int getBlackListPage(String word) throws ClassNotFoundException, SQLException{
		
		if (word == null) {
			word = "";
		}

		String sql = "select count(*) from blacklist b left join user u on u.uid=b.uid where u.username like ? and display!=0";
		int page =dao.executeQueryForInt(sql, new int[]{ Types.VARCHAR},new Object[]{ "%" + word + "%"});
		return page;
		
	}
	
	/**
	 * @desc     获取后台日志的总数页
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int getHistory() throws ClassNotFoundException, SQLException{
		
		int page = dao.executeQueryForInt("select count(*)  from log l left join root r on l.rootid=r.rootid ");
		return page;
	}
	
	
	
	
}
