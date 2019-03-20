package com.liuming.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.liuming.dao.IGenericDao;
import com.hispeed.conf.WebServerIni;

@SuppressWarnings("unchecked")
public class GenericDaoImpl<T, ID extends Serializable> extends HibernateDaoSupport implements IGenericDao<T, ID> {
	private static final Character UNDERLINE = '_';
	private static final Logger logger = Logger.getLogger("logger");
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected Class<T> entityClass;

	public GenericDaoImpl() {

	}

	public List<T> find(String queryString, Object value) throws DataAccessException {
		List<T> find = (List<T>) getHibernateTemplate().find(queryString, value);
		return find;
	}

	public Integer findCount(String queryString, String[] params) {
		StringBuilder hql = new StringBuilder("select count(*) ");
		hql.append(queryString);
		List<Long> find = (List<Long>) getHibernateTemplate().find(hql.toString(), params);
		if (null == find || find.size() == 0) {
			return 0;
		}
		return Integer.valueOf(find.get(0) + "");
	}

	public void save(Object obj) {
		getHibernateTemplate().save(obj);
	}

	public void update(Object obj) {
		getHibernateTemplate().update(obj);
	}

	public void delete(Object obj) {
		getHibernateTemplate().delete(obj);
	}

	public void deleteAll(List lt) {
		getHibernateTemplate().deleteAll(lt);
	}

	public void execUpdate(Object obj) {
		getHibernateTemplate().update(obj);
	}

	public String findSum(String queryString, String[] params, String sumField) {
		StringBuilder hql = new StringBuilder("select sum(" + sumField + ") ");
		hql.append(queryString);
		List<String> find = (List<String>) getHibernateTemplate().find(hql.toString(), params);
		return find.get(0);

	}

	public List<T> find(String queryString, Object[] values) throws DataAccessException {
		List<T> find = (List<T>) getHibernateTemplate().find(queryString, values);
		return find;
	}

	public List<String[]> findToArray(String queryString, Object[] values) throws DataAccessException {
		List<String[]> find = (List<String[]>) getHibernateTemplate().find(queryString, values);
		return find;
	}

	public List<T> find(final String queryString, final Object[] values, final int from, final int max) throws DataAccessException {
		List<T> list = getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session) {
				Query query = session.createQuery(queryString);
				query.setFirstResult(from);
				query.setMaxResults(max);
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
				return (List<T>) query.list();
			}
		});
		return list;
	}

	public List<T> findDistinct(final String queryString, final Object[] values, final String field, final int from, final int max)
			throws DataAccessException {
		List<T> list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				StringBuilder hql = new StringBuilder("select distinct " + field + " as " + field + " ");
				hql.append(queryString);
				Query query = session.createQuery(hql.toString());
				query.setFirstResult(from);
				query.setMaxResults(max);
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
				return (List<T>) query.list();
			}
		});
		return list;
	}

	public Integer findDistinctCount(String queryString, String[] params, final String field) {
		StringBuilder hql = new StringBuilder("select count(distinct " + field + ") ");
		hql.append(queryString);
		List<Long> find = (List<Long>) getHibernateTemplate().find(hql.toString(), params);
		return Integer.valueOf(find.get(0) + "");
	}

	public List<T> find(String queryString) throws DataAccessException {
		return (List<T>) getHibernateTemplate().find(queryString);
	}

	public T findAndLock(Class cls, String table_pkid) throws DataAccessException {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Object object = session.get(cls, table_pkid, LockMode.UPGRADE);
		return (T) object;
	}

	public String[] executeProcedure(String procExpression, String[] inParam, int outParamCount) {
		Session session = null;
		Connection conn = null;
		CallableStatement cs = null;
		try {
			long startime = System.currentTimeMillis();
			int inParamCount = inParam == null ? 0 : inParam.length;
			session = getHibernateTemplate().getSessionFactory().openSession();
			conn = session.connection();
			long getConnTime = System.currentTimeMillis();
			String[] result = new String[outParamCount];
			conn.setAutoCommit(false);
			StringTokenizer tok = new StringTokenizer(procExpression, "?");
			int paramCount = tok.countTokens() - 1;
			if (paramCount != inParamCount + outParamCount) {
				logger.error("Error in DBBean.execProcess(" + procExpression + ") is wrong in proc param.");
				return null;
			}

			cs = conn.prepareCall(procExpression);
			if (inParamCount > 0) {
				for (int i = 0; i < inParamCount; i++) {
					cs.setString(i + 1, inParam[i]);
				}
			}
			if (outParamCount > 0) {
				for (int i = 0; i < outParamCount; i++) {
					cs.registerOutParameter(inParamCount + i + 1, Types.VARCHAR);
				}
			}
			cs.execute();

			if (outParamCount > 0) {

				for (int i = 0; i < outParamCount; i++) {
					result[i] = (String) cs.getString(inParamCount + i + 1);
				}
			}
			conn.commit();
			this.log(procExpression, inParam, result, startime, getConnTime);
			return result;
		} catch (Exception e) {
			logger.error("", e);
			return null;
		} finally {
			// 2 ### private CallableStatement cs
			if (cs != null) {
				try {
					cs.close();
				} catch (Exception e) {
					;
				}
				cs = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.error("", e);
				}
				conn = null;
			}
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					;
				}
				session = null;
			}
		}

	}

	public String[] execQuerySQL(String SQL, Object[] param) {
		Session session = null;
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		long startime = System.currentTimeMillis();
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			conn = session.connection();

			long getConnTime = System.currentTimeMillis();

			if (null == conn)
				return null;

			int inParamCount = 0;
			if (param != null) {
				inParamCount = param.length;
			}
			String result[] = null;

			conn.setAutoCommit(false);
			cs = conn.prepareCall(SQL);
			if (inParamCount > 0) {
				for (int i = 0; i < inParamCount; i++) {
					if (param[i] == null) {
						cs.setObject(i + 1, "");
					} else {
						cs.setObject(i + 1, param[i]);
					}
				}
			}
			cs.execute();
			rs = cs.getResultSet();
			rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			ArrayList ay = new ArrayList();
			while (rs.next()) {
				for (int i = 1; i <= colCount; i++) {
					ay.add(rs.getString(i));
				}
			}
			conn.commit();
			result = new String[ay.size()];
			result = (String[]) ay.toArray(result);
			ay.clear();
			ay = null;
			this.log(SQL, (String[]) param, result, startime, getConnTime);
			return result;
		} catch (Exception e) {
			logger.error("Error in DBBean.execQuerySQL (" + SQL + ",param,inParamCount): " + e, e);

		} finally {
			// 2 ### private CallableStatement cs
			if (cs != null) {
				try {
					cs.close();
				} catch (Exception e) {
					;
				}
				cs = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.error("", e);
				}
				conn = null;
			}
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					;
				}
				session = null;
			}

		}
		return null;
	}

	private void log(String procExpression, String[] inParam, String[] results, long startTime, long getConnTime) {
		try {
			long endTime = System.currentTimeMillis();

			long executeTime = endTime - startTime;

			long connTime = endTime - getConnTime;

			StringBuffer strb = new StringBuffer();
			strb.append(formatter.format(new java.util.Date(startTime)));
			strb.append("[");
			strb.append(procExpression);
			strb.append("]==����|num:");
			if (inParam != null)
				strb.append(inParam.length);
			strb.append("|in params");
			if (inParam != null) {
				for (int i = 0; i < inParam.length; i++) {
					strb.append(">" + inParam[i]);
				}
			}

			strb.append("|" + formatter.format(new java.util.Date(endTime)) + "|exe time:" + executeTime + "|getConnTime:" + connTime
					+ "|ret num:");
			if (results != null)
				strb.append(results.length);
			strb.append("|out params");
			if (results != null) {
				for (String result : results) {
					strb.append(">" + result);
				}
			}
			long times = 60000;
			if (executeTime >= times) {
				logger.info(new StringBuilder().append("errorcode=DB11112 mess=\"new dbbean execute time too big.time=").append(executeTime)
						.append(" getConnTime=").append(connTime).append("\"").toString());
			}
			logger.info(strb.toString());
		} catch (Exception e) {
			logger.error("DbBean log e=" + e, e);
		}
	}

	public List execQuerySQLgetObjects(String SQL, String classname, String[] param) {
		Session session = null;
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		long startTime = System.currentTimeMillis();

		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			conn = session.connection();

			long getConnTime = System.currentTimeMillis();

			List list = null;
			int inParamCount = 0;

			if (param != null) {
				inParamCount = param.length;
			}

			if (conn == null)
				return null;

			conn.setAutoCommit(false);
			cs = conn.prepareCall(SQL);
			if (inParamCount > 0) {
				for (int i = 0; i < inParamCount; i++) {
					cs.setObject(i + 1, param[i]);
				}
			}
			cs.execute();
			rs = cs.getResultSet();
			list = this.rs2List(rs, classname);
			conn.commit();
			this.log(SQL, param, null, startTime, getConnTime);
			return list;

		} catch (Exception e) {
			logger.error("Error in DBBean.execQuerySQLgetObjects(" + SQL + "): " + e, e);
			return null;
		} finally {
			// 2 ### private CallableStatement cs
			if (cs != null) {
				try {
					cs.close();
				} catch (Exception e) {
					;
				}
				cs = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.error("", e);
				}
				conn = null;
			}
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					;
				}
				session = null;
			}

		}
	}

	private List rs2List(ResultSet rs, String classname) {
		ArrayList<Object> result = new ArrayList<Object>();
		try {
			while (rs != null && rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				Object c1 = null;
				String fieldName;
				String feildValue;
				c1 = Class.forName(classname).newInstance();
				for (int i = 1; i <= columnCount; i++) {
					fieldName = rsmd.getColumnName(i);
					if ("RN".equals(fieldName.toUpperCase()))
						continue;
					if (rs.getString(fieldName) != null) {
						feildValue = rs.getString(fieldName);
					} else {
						feildValue = "";
					}
					try {
						Method m = c1.getClass().getMethod("set" + convertName(fieldName), new Class[] { feildValue.getClass() });
						m.invoke(c1, new Object[] { feildValue });
					} catch (Exception e) {
						// log.info("��rs2List�����棺" + e);
					}

				}
				result.add(c1);
			}
			result.trimToSize();
			return result;
		} catch (Exception ex) {
			logger.error("rs2List error:  " + ex, ex);
			return new ArrayList();
		}
	}

	private static String convertName(String _s) {
		char[] c = _s.toCharArray();
		StringBuffer sb = new StringBuffer("");
		sb.append(Character.toUpperCase(c[0]));
		for (int i = 1; i < c.length; i++) {
			if (UNDERLINE.equals(c[i])) {
				if (i + 1 < c.length) {
					if (!UNDERLINE.equals(c[i + 1])) {
						sb.append(Character.toUpperCase(c[i + 1]));
						i++;
					}
				}
			} else {
				sb.append(Character.toLowerCase(c[i]));
			}
		}
		return sb.toString();
	}

	public List<Object[]> findArray(String queryString, String[] params) {
		List<Object[]> find = getHibernateTemplate().find(queryString, params);
		return find;
	}

	public List<Object[]> findArray(final String queryString, final Object[] values, final int from, final int max) throws DataAccessException {
		List<Object[]> list = getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session) {
				Query query = session.createQuery(queryString);
				query.setFirstResult(from);
				query.setMaxResults(max);
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
				return (List<Object[]>) query.list();
			}
		});
		return list;
	}

}
