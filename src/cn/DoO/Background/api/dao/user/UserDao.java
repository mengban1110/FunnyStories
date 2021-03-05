package cn.DoO.Background.api.dao.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

public class UserDao {
	Dao dao = new DaoImpl();

	
	
	/**
	 * 分页查询user表 
	 * @param page
	 * @param size
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findUserByPageAndSize(int page, int size) throws ClassNotFoundException, SQLException {
		
		String sql = "SELECT * FROM `user` LIMIT ?, ?;";
		
		Object[] values = {(page-1)*size, size};
		
		int [] types = {Types.INTEGER,Types.INTEGER};
		return dao.executeQueryForList(sql, types, values);
	}
	

	/**
	 * 跟据 关键字分页查询user表
	 * @param page
	 * @param size
	 * @param world
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> findUserBypageAndSizeAndLike(int page, int size, String world) throws ClassNotFoundException, SQLException {
		
		String sql = "SELECT * FROM `user` WHERE username LIKE ? LIMIT ?,?;";
		
		Object[] values = {"%"+world.trim()+"%",(page-1)*size, size};
		
		int [] types = {Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		
		return dao.executeQueryForList(sql, types, values);
	}

	/**
	 * 根据 用户id 查询用户表
	 * @param uid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> findUserById(String uid) throws ClassNotFoundException, SQLException {
		
		String sql = "SELECT * FROM `user` WHERE uid = ?";
		
		Object[] values = {uid};
		
		int [] types = {Types.VARCHAR};
		
		
		return dao.executeQueryForMap(sql, types, values);
	}
	
	/**
	 * 根据用户id 修改用户 表参数
	 * @param uid
	 * @param username
	 * @param useravatar
	 * @param usersex
	 * @param userbir
	 * @param usersign
	 * @param userstatus
	 * @param password
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public void update(String uid, String username, String useravatar, String usersex, String userbir, String usersign,
			String userstatus, String password) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "UPDATE `user` SET username = ?,useravatar = ?,usersex = ?,userbirth = ?,usersign = ?,userstatus = ?,`password` = ? WHERE uid = ?";
		
		Object[] values = {username,useravatar,usersex,userbir,usersign,userstatus,password,uid};
		
		int [] types = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		
		dao.executeUpdate(sql, types, values);
	}
	
	
	/**
	 * 测试
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		UserDao userDao = new UserDao();
		List<Map<String, Object>> userList = userDao .findUserBypageAndSizeAndLike(1,2,"");
		JSONObject jsonObject = new JSONObject();
		System.out.println(userList);
//		userDao.update("1", "2","2", "2", "2", "2","2", "2");
		
		
		//返回数据
//		List<Map<String, Object>> users = new ArrayList<Map<String,Object>>();
		/**
		for (Map<String, Object> map : userList) {
			Map<String, Object> user = new HashMap<String,Object>();
			user.put("userid", map.get("uid"));
			user.put("username", map.get("username"));
			user.put("useravatar", map.get("useravatar"));
			user.put("userbir", map.get("userbirth"));
			user.put("email", map.get("email"));
			user.put("usersign", map.get("usersign"));
			users.add(user);
		}
		
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("users", users);
		
		
		jsonObject.put("code", 200);
		jsonObject.put("msg", "获取成功");
		jsonObject.put("data", jsonObject2);
		
		System.out.println(jsonObject);
		**/
		
		/**
		//存值
		Map<String, Object> map = userDao.findUserById("1");
		Map<String, Object>user = new HashMap<String, Object>();
		
		user.put("userid", map.get("uid"));
		user.put("username", map.get("username"));
		user.put("useravatar", map.get("useravatar"));
		user.put("userbir", map.get("userbirth"));
		user.put("email", map.get("email"));
		user.put("usersign", map.get("usersign"));
		
		
		jsonObject.put("code", 200);
		jsonObject.put("msg", "获取成功");
		jsonObject.put("data", user);
		**/
	}






}
