package cn.DoO.Background.api.dao.history;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

public class HistoryDao {
	Dao dao = new DaoImpl();

	public List<Map<String, Object>> findAll(int page, int size) throws ClassNotFoundException, SQLException {
		String sql = "select l.rootid, r.rootname, r.rootavatar, l.content, l.ip,l.time  from log l left join root r on l.rootid=r.rootid LIMIT ?,?";

		int[] types = { Types.INTEGER, Types.INTEGER };

		Object[] values = { (page - 1) * size, size };

		return dao.executeQueryForList(sql, types, values);

	}

}
