package cn.DoO.Background.api.dao.adv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

public class AdvertDao {

	Dao dao = new DaoImpl();

	/**
	 * 模糊分页查询广告表
	 * 
	 * @param word
	 * @param page
	 * @param size
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Map<String, Object>> findAll(String word, int page, int size)
			throws ClassNotFoundException, SQLException {

		if (word == null) {
			word = "";
		}

		String sql = "SELECT * FROM advert WHERE display !=0 AND acontext LIKE ? LIMIT ?,? ";

		Object[] values = { "%" + word + "%", ((page - 1) * size), size };

		int[] types = { Types.VARCHAR, Types.INTEGER, Types.INTEGER };

		return dao.executeQueryForList(sql, types, values);
	}

	/**
	 * 测试
	 * 
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		AdvertDao advertDao = new AdvertDao();
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> dataList = advertDao.findAll("", 1, 2);

		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<JSONObject> advert = new ArrayList<JSONObject>();
		JSONObject jsonObject2 = null;
		for (Map<String, Object> map : dataList) {
			jsonObject2 = new JSONObject();

			jsonObject2.put("aid", map.get("aid"));
			jsonObject2.put("acontext", map.get("acontext"));
			jsonObject2.put("aimg", map.get("aimg"));
			jsonObject2.put("createtime", formatter.format(new Date(Long.parseLong((String) map.get("createtime")))));

			advert.add(jsonObject2);
		}

		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("advert", advert);

		jsonObject.put("code", 200);
		jsonObject.put("msg", "获取成功");
		jsonObject.put("data", jsonObject3);
		System.out.println(jsonObject);
	}

	/**
	 * 删除广告的方法
	 * 
	 * @param aid
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void delAdvertById(String aid)
			throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {

		String sql = "UPDATE advert SET display = 0 WHERE aid = ?";

		Object[] values = { aid };

		int[] types = { Types.INTEGER };

		dao.executeUpdate(sql, types, values);
	}

	/**
	 * 保存图片的方法
	 * 
	 * @param acontext
	 * @param aimg
	 * @throws IOException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	public void saveAdverInfo(String acontext, String aimg)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {

		String sql = "INSERT INTO advert VALUES(0,?,?,?,1,NULL)";

		Object[] values = { acontext, aimg, (System.currentTimeMillis()/1000) };

		int[] types = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };

		dao.executeUpdate(sql, types, values);
	}

}
