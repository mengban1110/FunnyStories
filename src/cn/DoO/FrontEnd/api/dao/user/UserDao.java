package cn.DoO.FrontEnd.api.dao.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.sun.org.apache.bcel.internal.generic.Select;

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
	public int updatePwd(String newpassword, String uid) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update user set password=? where uid=?", new int[]{Types.VARCHAR,Types.VARCHAR}, new Object[]{newpassword,uid});
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
	 * @param code 
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> queryCodeById(int id, String code) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from code where uid=? and display=1 and code=?", new int[]{Types.INTEGER,Types.VARCHAR}, new Object[]{id,code});
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
	public int updateCode(int id, String code) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update code set display=0 where uid=? and code=?", new int[]{Types.INTEGER,Types.VARCHAR}, new Object[]{id,code});
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
	
	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> queryUserByName(String username) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from user where username=?", new int[]{Types.VARCHAR}, new Object[]{username});
	}
	
	/**
	 * 添加用户
	 * @param username
	 * @param pwd
	 * @param email
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public int addUser(String username, String pwd, String email) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("insert into user values(0,?,?,'外星人',null,'暂无签名',?,null,?,0,null)", new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}, new Object[]{username,"https://static.qiushibaike.com/images/web/v4/textDefault.png?v=12eaf94cfd4d3ae0423a3925bb5bbf9c",email,pwd});
		
	}
	
	/**
	 * 新建验证码
	 * @param code
	 * @param string
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int codeInDataBase(String code, String uid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("insert into code values(0,?,?,?,1,null)", new int[]{Types.INTEGER,Types.VARCHAR,Types.VARCHAR}, new Object[]{Integer.parseInt(uid),code,System.currentTimeMillis()+"",1});
				
	}
	
	/**
	 * 根据id和token获取user
	 * @param token
	 * @param userid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryUserByTokenAndId(String token, String userid) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from user where uid=? and usertoken=?", new int[]{Types.INTEGER,Types.VARCHAR}, new Object[]{Integer.parseInt(userid),token});
	}
	
	/**
	 * 查询未注册用户
	 * @param username
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> queryUserByNameZero(String username) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from user where username=? and userstatus!=0", new int[]{Types.VARCHAR}, new Object[]{username});
	}
	
	/**
	 * 查询未注册用户
	 * @param email
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> queryUserByEmailZero(String email) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from user where email=? and userstatus!=0", new int[]{Types.VARCHAR}, new Object[]{email});
	}
	/**
	 * @desc 修改用户信息
	 * @param uid
	 * @param username
	 * @param useravatar
	 * @param usersex
	 * @param userbirth
	 * @param usersign
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public void updateUser(String uid, String username, String useravatar, String usersex, String userbirth,
			String usersign) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		String sql = "UPDATE `user` SET username = ? ,usersex = ?,userbirth = ?,useravatar = ?,usersign = ? WHERE uid = ?";
		
		int [] types = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		
		Object[] values = {username,useravatar,usersex,userbirth,usersign,uid};
		
		dao.executeUpdate(sql, types, values);
	}
}
