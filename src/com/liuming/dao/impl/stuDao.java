package com.liuming.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import com.liuming.dao.IStuDao;
import com.liuming.util.ConstantUtil;
import com.liuming.vo.UtStuInfoVo;

/**
 * 学生dao
 * 
 * @author liumi
 * 
 */
@Repository("stuDao")
public class stuDao extends GenericDaoImpl<UtStuInfoVo, Integer> implements IStuDao {
	private static Logger log = Logger.getLogger("logger");

	/**
	 * 保存学生信息表
	 */
	public void addStuInfoVo(UtStuInfoVo vo) {
		log.info("------------------------------------");
		save(vo);
	}

	/**
	 * 取得学生信息表（验证作用）
	 * 
	 * @return
	 */
	public UtStuInfoVo getStuInfoVo(String stuacc, String stustate, String stuidcard) {
		// TODO Auto-generated method stub
		String sql = "from UtStuInfoVo a where a.stu_acc = ? or a.stu_card = ?";

		List<UtStuInfoVo> list = find(sql, new String[] { stuacc, stuidcard });
		log.info(sql.toString() + "查询vo" + list.toString());
		return null == list || list.size() == 0 ? null : list.get(0);
	}

	/**
	 * 通过学生身份证号查询整张表
	 */
	public List<UtStuInfoVo> getStuInfoList(String stucard, int pageNo) {
		StringBuffer sql = new StringBuffer("from UtStuInfoVo where 1=1");
		List<String> list = new ArrayList<String>();
		if (null != stucard || !"".equals(stucard.trim())) {
			stucard = stucard.trim();
			sql.append(" and stu_card like ?");
			list.add("%" + stucard + "%");
		}
		sql.append(" order by stu_id");
		final int size = list.size();
		String[] inpar = list.toArray(new String[size]);
		int from = ((pageNo) - 1) * ConstantUtil.C_SYS_PAGESIZE;
		log.info("分页查询" + sql.toString());
		return find(sql.toString(), inpar, from, ConstantUtil.C_SYS_PAGESIZE);
	}

	/**
	 * 得到页数
	 */
	public Integer getStuInfoCount(String stucard) {
		StringBuffer sql = new StringBuffer("from UtStuInfoVo where 1=1");
		List<String> list = new ArrayList<String>();
		if (null != stucard || !"".equals(stucard.trim())) {
			stucard = stucard.trim();
			sql.append("and stu_card like ?");
			list.add("%" + stucard + "%");
		}
		final int size = list.size();
		String[] inpar = list.toArray(new String[size]);
		return findCount(sql.toString(), inpar);
	}

	/**
	 * 得到并锁定vo表
	 * 
	 */
	public UtStuInfoVo getStuInfoVoAndLock(String stu_id) {
		// TODO Auto-generated method stub
		return findAndLock(new UtStuInfoVo().getClass(), stu_id);
	}

	public void batchDelStu(UtStuInfoVo vo) {
		// TODO Auto-generated method stub
		log.info("删除-----------------------------------------------");
		delete(vo);
	}

	public List<UtStuInfoVo> getStuInfoVoByCard(String stu_card) {
		// TODO Auto-generated method stub
		String sql = "from UtStuInfoVo a where  a.stu_card like ?";

		List<UtStuInfoVo> list = find(sql, "" + stu_card + "%");
		log.info(sql.toString() + "查询vo" + list.toString());
		return list;

	}
}
