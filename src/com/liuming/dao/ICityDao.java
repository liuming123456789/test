package com.liuming.dao;

import java.util.List;

import com.liuming.vo.UtCityListVo;

/**
 * 城市字典接口
 * 
 * @author liumi
 * 
 */
public interface ICityDao { 
	List<UtCityListVo> getCityList(String provId);

}
