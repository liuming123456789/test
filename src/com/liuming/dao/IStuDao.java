package com.liuming.dao;

import java.util.List;

import com.liuming.vo.UtStuInfoVo;

/**
 * 学生dao接口
 * 
 * @author liumi
 * 
 */
public interface IStuDao {
	/**
	 * 添加学生信息表
	 * 
	 * @param vo
	 *            学生vo
	 */
	public void addStuInfoVo(UtStuInfoVo vo);

	/**
	 * 取得含有下列信息的学生vo表
	 * 
	 * @param stuacc
	 *            学生账号
	 * @param stustate
	 *            学生状态
	 * @param stuidcard
	 *            学生证号
	 */
	public UtStuInfoVo getStuInfoVo(String stuacc, String stustate, String stuidcard);

	/**
	 * stuDao接口
	 * 
	 * @param stucard
	 * @param pageNo
	 * @return
	 */
	public List<UtStuInfoVo> getStuInfoList(String stucard, int pageNo);
    /**
     * stuDao接口
     * @param stucard
     * @param pageNo
     * @return
     */
	public Integer getStuInfoCount(String stucard);
    /**
     * 得到学生表并锁定表
     * @param stuId 学生id
     * @return
     */
	public UtStuInfoVo getStuInfoVoAndLock(String stu_id);
    /**
     * 删除学生表
     * @param vo
     */
	public void batchDelStu(UtStuInfoVo vo);

	public  List<UtStuInfoVo>  getStuInfoVoByCard(String stu_card);

}
