package com.liuming.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.liuming.dao.IAdmDao;
import com.liuming.vo.UtAdmInfoVo;


@Repository("admDao")
public class AdmDao extends GenericDaoImpl<UtAdmInfoVo, Integer> implements IAdmDao {

	// 日志
	public static Logger log = Logger.getLogger("logger");

	/**
	 * 根据用户名查询用户实体
	 * 
	 * @param admin_acc用户名
	 * 
	 * @return 用户实体
	 */
	public UtAdmInfoVo getAdmVo(String admin_acc) {

		String sql = "from UtAdmInfoVo a where admin_acc=?";
		List<UtAdmInfoVo> list = find(sql, admin_acc);
		log.info(sql.toString() + admin_acc + "-----------------------------------------");
		return null == list || list.size() == 0 ? null : list.get(0);
	}

}
