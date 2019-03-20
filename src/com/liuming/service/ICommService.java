package com.liuming.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liuming.vo.UtAdmInfoVo;
import com.liuming.vo.UtCityListVo;
import com.liuming.vo.UtProvListVo;
import com.liuming.vo.UtStuInfoVo;

/**
 * 用户servise接口
 * 
 * @author liumi
 * 
 */
public interface ICommService {
	/**
	 * 通过用户账号得到实体表
	 * 
	 * @param admin_acc
	 * 
	 */
	public UtAdmInfoVo getAdmVo(String admin_acc);

	/**
	 * 通过老用户密码和新用户密码来改用户密码
	 * 
	 * @param pwdNew
	 * @param password
	 * @return
	 */
	public boolean getAdmPwdChange(String adm_acc, String pwdNew);

	/**
	 * 省份字典接口
	 * 
	 * @return
	 */

	List<UtProvListVo> getProvList();

	/**
	 * 城市字典接口
	 * 
	 * @return
	 */
	public List<UtCityListVo> getCityList(String provId);

	/**
	 * 通过以下信息添加学生信息
	 * 
	 * @param stuacc
	 * @param stuname
	 * @param stupwdMd5
	 * @param stuaddress
	 * @param stuidcard
	 * @param stusex
	 * @param provId
	 * @param cityId
	 * @param stubirth
	 * @return
	 */
	public boolean addStuInfo(String stuacc, String stuname, String stupwd, String stuaddress, String stuidcard, String stusex, String prov_id,
			String city_id, String stubirth);

	/**
	 * 查询数据库中是否含有下列信息的vo
	 * 
	 * @param stuacc
	 *            学生账号
	 * @param stustate
	 *            学生状态
	 * @param stuidcard
	 *            学生身份证号
	 * @return
	 */
	public UtStuInfoVo getStuInfoVo(String stuacc, String stustate, String stuidcard);

	/**
	 * 得到学生表
	 * 
	 * @param stucard 学生省份证号
	 * @param i 页数
	 * @return
	 */
	public List<UtStuInfoVo> getStuInfoList(String stucard, int pageNo);
	
    /**
     * 查询记录
     * @param stucard 学生身份证号
     * @param pageNo  页数
     * @return
     */
	public Integer getStuInfoCount(String stucard);
    /**
     * 批量删除学生信息
     * @param stuId 学生id
     * @return
     */
	public void batchDelStu(String stu_id);
    /**
     * 批量修改学生信息
     * @param stu_id 学生id
     */
	public void batchChangeStuPwd(String stu_id);
/**
 * 批量下载
 * @param stuId
 */ 

public File downStu(String[] titles, String stu_card,HttpServletRequest request);

public File downStuTxt(String[] titles, String stu_card, HttpServletRequest request);

public File downStuCsv(String[] titles, String stu_card, HttpServletRequest request);
	
    /**
     * 删除单个学生信息
     * @param stu_id 学生id
     */
	//public void delStu(String stu_id);

}
