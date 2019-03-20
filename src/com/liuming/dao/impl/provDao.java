 
package com.liuming.dao.impl;

 import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository; 

import com.liuming.dao.impl.GenericDaoImpl;
import com.liuming.dao.IProvDao;
import com.liuming.vo.UtProvListVo;


 /**
 * 查询省份字典
 * 
 * @author liumi
 * @return 按prov_id排序查询的字典表
 * 
 */
@Repository("provDao")
public class provDao extends GenericDaoImpl<UtProvListVo, Integer> implements IProvDao {
	 
	private static Logger log = Logger.getLogger("logger");
 
	public List<UtProvListVo> getProvList() {
		// TODO Auto-generated method stub
		String qSql = "from UtProvListVo order by prov_id";
		log.info("[查询省份Dao],qSql:" + qSql);
		return find(qSql);
	}

}
