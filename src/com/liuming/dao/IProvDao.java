package com.liuming.dao;

import java.util.List;

import com.liuming.vo.UtProvListVo;

 /**
  * 省份字典接口
  * @author liumi
  *
  */
public interface IProvDao {
	 List<UtProvListVo> getProvList();
}
