package cn.DoO.FrontEnd.api.dao.adv;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.DoO.Utils.Dao.DataConnect.Dao;
import cn.DoO.Utils.Dao.DataConnect.DaoImpl;

/**
* @desc 获取广告数据的dao
* 
* @author TianShuo
* 
* @version 2021年4月2日 下午5:18:32
*/
public class AdvDaolmpl {
	Dao dao = new DaoImpl();
	public List<Map<String, Object>> getAllAdv() throws ClassNotFoundException, SQLException {
		
		/**
		 * 只随机返回三条广告
		 */
		return dao.executeQueryForList("select acontext,aimg from advert where display=1 order by rand() limit 0,3");
	}
	
}
