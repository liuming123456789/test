package com.liuming.dao.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.liuming.dao.ICityDao;
import com.liuming.dao.impl.GenericDaoImpl;

import com.liuming.vo.UtCityListVo;

/**
 * 查询省份字典
 * 
 * @author liumi
 * @return 按prov_id排序查询的字典表
 * 
 */
@Repository("cityDao")
public class cityDao extends GenericDaoImpl<UtCityListVo, Integer> implements ICityDao {

	private static Logger log = Logger.getLogger("logger");

	public List<UtCityListVo> getCityList(String prov_id) {
		// TODO Auto-generated method stub
		String qSql = "from UtCityListVo where prov_id=?";
		log.info("[查询城市Dao],qSql:" + qSql);
		return find(qSql,prov_id);
	}
}
