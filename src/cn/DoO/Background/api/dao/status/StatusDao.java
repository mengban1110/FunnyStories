package cn.DoO.Background.api.dao.status;

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
 * @desc     网站后台状态
 * @author 云尧
 *
 */
public class StatusDao {
	Dao dao=new DaoImpl();
	IPUtils ipUtils=new IPUtils();
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


	/**
	 * @desc    写入后台日志中
	 * @param rid
	 * @param request 
	 * @param i 
	 * @param string 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("static-access")
	public void writerLog(int rid, HttpServletRequest request, String content, int type) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		String timestamp =System.currentTimeMillis() / 1000+"";
	 
		String ip=ipUtils.getClientIpAddr(request);
		String sql ="insert into log (rootid,time,content,ip,type) values (?,?,?,?,?)";
		dao.executeUpdate(sql, new int[]{Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER}, new Object[]{rid,timestamp,content,ip,type});
		
	}
public static void main(String[] args) {
	
}
	

}
