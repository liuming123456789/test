package com.liuming.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;


public interface IGenericDao<T, ID extends Serializable>{

	public List<T> find(String queryString, Object value)throws DataAccessException;

	public List<T> find(String queryString, Object[] values)throws DataAccessException;

	public List<T> find(String queryString) throws DataAccessException;

    public String[] executeProcedure(String procExpression, String[] inParam,int outParamCount);
        
    public String[] execQuerySQL(String SQL, Object[] param);

}