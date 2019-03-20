package com.liuming.dao;

import com.liuming.vo.UtAdmInfoVo;
/**
 * 用户dao接口
 * @author liumi
 * 
 */
public interface IAdmDao {
    /**
     * 根据用户账号查询用户实体接口
     * @param admin_acc 用户名
     *  
     */
	public UtAdmInfoVo getAdmVo(String admin_acc);

}
