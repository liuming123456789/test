package com.liuming.vo;
 
import javax.persistence.Column;
import javax.persistence.Entity;
 
import javax.persistence.Id;
import javax.persistence.Table;
 


@Entity
@Table(name = "ut_pub_prov")
public class UtProvListVo {
	@Id
	@Column(name = "prov_id")
	private String prov_id;
	@Column(name = "province_name")
	private String province_name;

	public void setProv_id(String prov_id) {
		this.prov_id = prov_id;
	}

	public String getProv_id() {
		return prov_id;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	public String getProvince_name() {
		return province_name;
	}

}
