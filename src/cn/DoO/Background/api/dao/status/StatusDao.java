package cn.DoO.Background.api.dao.status;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;
/**
 * @desc     网站后台状态
 * @author 云尧
 *
 */
public class StatusDao {
	Dao dao=new DaoImpl();
	
	//查询出网站后台的状态
	public Map<String, Object> query() throws ClassNotFoundException, SQLException {
		
		return dao.executeQueryForMap("select * from serviceinfo");
	}
	
	
	//修改注册状态
	public void editRegisterPoststatus(int status) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "update serviceinfo set register=?";
		dao.executeUpdate(sql, new int[]{Types.INTEGER}, new Object[]{status});
	}

	//修改登陆状态
	public void editLoginPoststatus(int status) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "update serviceinfo set login=?";
		dao.executeUpdate(sql, new int[]{Types.INTEGER}, new Object[]{status});
		
	}

	//修改开/关站状态
	public void editWebPoststatus(int status) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		String sql = "update serviceinfo set open=?";
		dao.executeUpdate(sql, new int[]{Types.INTEGER}, new Object[]{status});
	}
	
	
	//修改发帖状态
	public void editPostPoststatus(int status) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "update serviceinfo set post=?";
		dao.executeUpdate(sql, new int[]{Types.INTEGER}, new Object[]{status});
	}

	//修改评论状态
	public void editCommentPoststatus(int status) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "update serviceinfo set comment=?";
		dao.executeUpdate(sql, new int[]{Types.INTEGER}, new Object[]{status});
		
	}

	

}
