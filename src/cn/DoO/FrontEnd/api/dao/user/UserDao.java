package cn.DoO.FrontEnd.api.dao.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

/**
 * user模块dao
 * @author LYP
 *
 */
public class UserDao {
	Dao dao = new DaoImpl();
	
	/**
	 * 根据token查询user
	 * @param token
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> queryUserByToken(String token) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from user where usertoken=?", new int[]{Types.VARCHAR}, new Object[]{token});
	}
	
	/**
	 * 修改密码
	 * @param newpassword
	 * @param token
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public int updatePwd(String newpassword, String token) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update user set password=? where usertoken=?", new int[]{Types.VARCHAR,Types.VARCHAR}, new Object[]{newpassword,token});
	}
	
	/**
	 * 根据邮箱查询用户
	 * @param email
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> queryUserByEmail(String email) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from user where email=?", new int[]{Types.VARCHAR}, new Object[]{email});
	}
	
	/**
	 * 根据id查询code
	 * @param id
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> queryCodeById(int id) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from code where uid=? and display=1", new int[]{Types.INTEGER}, new Object[]{id});
	}
	
	/**
	 * 修改激活码状态 
	 * @param id
	 * @param string
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public int updateCode(int id, String time) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update code set display=0 where uid=? and createtime=?", new int[]{Types.INTEGER,Types.VARCHAR}, new Object[]{id,time});
	}
	
	/**
	 * 根据id修改用户状态
	 * @param id
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public int updateUserZtById(int id) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update user set userstatus=1 where uid=?", new int[]{Types.INTEGER}, new Object[]{id});
	}
	
	/**
	 * 更新token
	 * @param tokenTemp
	 * @param string
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int updateToken(String tokenTemp, String uid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update user set usertoken=? where uid=?", new int[]{Types.VARCHAR,Types.INTEGER}, new Object[]{tokenTemp,Integer.parseInt(uid)});
	}
}
