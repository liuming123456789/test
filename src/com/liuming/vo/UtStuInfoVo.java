package com.liuming.vo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 学生信息实体表
 * 
 * @author liumi
 * 
 */
@Entity
@Table(name = "ut_stu_info")
public class UtStuInfoVo {
	// 主键学生id
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stu_id")
	private String stu_id;
	// 学生账号
	@Column(name = "stu_acc")
	private String stu_acc;

	// 学生姓名
	@Column(name = "stu_name")
	private String stu_name;

	// 学生身份证号
	@Column(name = "stu_card")
	private String stu_card;

	// 学生联系地址
	@Column(name = "stu_address")
	private String stu_address;

	// 学生登录密码
	@Column(name = "stu_pwd")
	private String stu_pwd;

	// 学生状态
	@Column(name = "stu_state")
	private String stu_state;

	// 学生性别
	@Column(name = "stu_sex")
	private String stu_sex;
	
	// 学生出生日期 
	@Column(name = "stu_birth")
	private String stu_birth;
	// 城市
	@Column(name = "city_id")
	private String city_id;
    
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id", insertable = false, updatable = false)
	private UtCityListVo cityVo;

	// 省份
	@Column(name = "prov_id")
	private String prov_id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "prov_id", insertable = false, updatable = false)
	private UtProvListVo provVo;
	
    public UtProvListVo getProvVo() {
		return provVo;
	}

	public void setProvVo(UtProvListVo provVo) {
		this.provVo = provVo;
	}
	
	public UtCityListVo getCityVo() {
		return cityVo;
	}

	public void setCityVo(UtCityListVo cityVo) {
		this.cityVo = cityVo;
	}

	
 
	public void setStu_id(String stuid) {
		stu_id = stuid;
	}

	public String getStu_id() {
		return stu_id;
	}

	public void setStu_acc(String stuacc) {
		stu_acc = stuacc;
	}

	public String getStu_acc() {
		return stu_acc;
	}

	public void setStu_name(String stuname) {
		stu_name = stuname;
	}

	public String getStu_name() {
		return stu_name;
	}

	public void setStu_card(String stuidcard) {
		stu_card = stuidcard;
	}

	public String getStu_card() {
		return stu_card;
	}

	public void setStu_address(String stuaddress) {
		stu_address = stuaddress;
	}

	public String getStu_address() {
		return stu_address;
	}

	public void setStu_pwd(String stupwd) {
		stu_pwd = stupwd;
	}

	public String getStu_pwd() {
		return stu_pwd;
	}

	public void setStu_state(String stustate) {
		stu_state = stustate;
	}

	public String getStu_state() {
		return stu_state;
	}

	public void setStu_sex(String stusex) {
		stu_sex = stusex;
	}

	public String getStu_sex() {
		return stu_sex;
	}

	public void setCity_id(String cityid) {
		city_id = cityid;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setProv_id(String provid) {
		prov_id = provid;
	}

	public String getProv_id() {
		return prov_id;
	}

	public void setStu_birth(String stubirth) {
		stu_birth = stubirth;
	}

	public String getStu_birth() {
		return stu_birth;
	}

}
