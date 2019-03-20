package com.liuming.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 取得城市列表
 * @author liumi
 *
 */
@Entity
@Table(name = "ut_pub_city")
public class UtCityListVo {
	@Id
	@Column(name = "city_id")
	private String city_id;

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getCity_id() {
		return city_id;
	}

	@Column(name = "city_name")
	private String city_name;

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getCity_name() {
		return city_name;
	}

	@Column(name = "prov_id")
	private String prov_id;

	public void setProv_id(String prov_id) {
		this.prov_id = city_id;
	}

	public String getProv_id() {
		return prov_id;
	}

	@Column(name = "isallprov")
	private String isallprov;

	public void setIsallprov(String isallprov) {
		this.isallprov = isallprov;
	}

	public String getIsallprov() {
		return isallprov;
	}
}
