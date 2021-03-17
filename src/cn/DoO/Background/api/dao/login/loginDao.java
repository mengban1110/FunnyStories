package cn.DoO.Background.api.dao.login;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;
import cn.DoO.Utils.Tools.IPUtils;

/**
 * @desc 登陆的查询
 * @author 齐云尧
 *
 */
public class loginDao {
	IPUtils ipUtils =new IPUtils();
	Dao dao = new DaoImpl();

	/**
	 * @desc 查询该管理员的信息
	 * @param userName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getpasswordByU(String rootName) throws ClassNotFoundException, SQLException {
		String sql = "select * from root where rootname=?";
		return dao.executeQueryForMap(sql, new int[] { Types.VARCHAR }, new Object[] { rootName });
	}

	
	
	public void addRootLogin(String rootid, HttpServletRequest request) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {

		
		String timestamp =System.currentTimeMillis() / 1000+"";
	 
		String ip=ipUtils.getClientIpAddr(request);
		String sql = "insert into rootlogin (rootid,logintime,rootip) values(?,?,?) ";
		dao.executeUpdate(sql,new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR} , new Object[]{rootid,timestamp,ip});
	}

	
}
