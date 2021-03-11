package cn.DoO.Utils.LogUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.http.HttpServletRequest;

import cn.DoO.Background.api.dao.status.StatusDao;
import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;
import cn.DoO.Utils.Tools.IPUtils;

public class WriterLogUtil {

	static Dao dao = new DaoImpl();
	StatusDao statusDao = new StatusDao();
	static IPUtils ipUtils=new IPUtils();
	public static void writeLog(String token,HttpServletRequest request,String content,int type) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		
		System.out.println(token);
		Integer rootid = (Integer) dao.executeQueryForMap("select * from root where token = ?", new int[]{Types.VARCHAR}, new Object[]{token}).get("rootid");
		
		String ip=ipUtils.getClientIpAddr(request);
		String time =System.currentTimeMillis()/1000+"";
		
		String sql ="insert into log (rootid,time,content,ip,type) values (?,?,?,?,?)";
		dao.executeUpdate(sql, new int[]{Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER}, new Object[]{rootid,time,"管理员"+rootid+content,ip,type});
	}
	
}
