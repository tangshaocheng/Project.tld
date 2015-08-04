package com.energysh.egame.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.PageBar;
import com.energysh.egame.util.Reflection;

@SuppressWarnings("unchecked")
public class MutilBaseDaoImp extends HibernateDaoSupport implements BaseDao {

	private final static Log log = LogFactory.getLog(MutilBaseDaoImp.class);

	public void save(Object obj) {
		if (checkSqlPara(obj))
			return;
		this.getHibernateTemplate().save(obj);
	}

	public Object get(Serializable id, Class clazz) {
		return this.getHibernateTemplate().get(clazz, id);
	}

	public void update(Object obj) {
		if (checkSqlPara(obj))
			return;
		this.getHibernateTemplate().update(obj);
	}

	public void bulkUpdate(String sql) {
		this.getHibernateTemplate().bulkUpdate(sql);
	}

	public void bulkUpdate(String sql, Object[] para) {
		this.getHibernateTemplate().bulkUpdate(sql, para);
	}

	public void saveOrUpdate(Object obj) {
		if (checkSqlPara(obj))
			return;
		this.getHibernateTemplate().saveOrUpdate(obj);
	}

	public void del(Object obj) {
		if (checkSqlPara(obj))
			return;
		this.getHibernateTemplate().delete(obj);

	}

	public void delAll(Collection<Object> objs) {
		this.getHibernateTemplate().deleteAll(objs);
	}

	public Object get(Class entityClass, Integer id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 首先在session缓存中查找，然后在二级缓存中查找，还没有就查数据库，数据库中没有就返回null。 推荐使用
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Object get(Class entityClass, Long id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 会先查一下session缓存看看该id对应的对象是否存在，不存在则创建代理,实际使用时再加载实体对象到代理对象,可以实现延迟加载
	 * 必须确定load的对象在数据库中确实存在，不然不要适应 不推荐使用
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Object load(Class entityClass, Long id) {
		return this.getHibernateTemplate().load(entityClass, id);
	}

	/**
	 * 指定清楚某对象缓(慎用)
	 * 
	 * @param obj
	 */
	public void evict(Object obj) {
		this.getHibernateTemplate().evict(obj);
	}

	/**
	 * 把缓冲区内的全部对象清除，但不包括操作中的对�?慎用
	 */
	public void clear() {
		this.getHibernateTemplate().clear();
	}

	public List<Object> findByHql(String queryString)
			throws DataAccessException {
		return this.getHibernateTemplate().find(queryString);
	}

	public List findListBySql(final String sql, final Object[] para,
			final int pageNo, final int pageSize, final Class entity) {
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						sqlQuery.addEntity(entity);
						sqlQuery.setFirstResult(pageNo);
						sqlQuery.setMaxResults(pageSize);
						if (null != para && para.length != 0)
							bindProperties(sqlQuery, para);
						List<Object> rlist = sqlQuery.list();
						return rlist;
					}
				});
	}

	/**
	 * 使用本地SQL全量查询
	 * 
	 * @param sql
	 * @param para
	 * @param entity
	 * @return
	 */
	public List findListBySql(final String sql, final Object[] para,
			final Class entity) {
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						sqlQuery.addEntity(entity);
						if (null != para && para.length != 0)
							bindProperties(sqlQuery, para);
						List<Object> rlist = sqlQuery.list();
						return rlist;
					}
				});
	}

	/**
	 * 查询结果集 ,一个po的collection
	 * 
	 * @param hsql
	 * @param para
	 * @return List
	 */
	public List findList(final String hsql, final Object[] para) {
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						Query query = session.createQuery(hsql);
						if (null != para && para.length != 0)
							bindProperties(query, para);
						return query.list();
					}
				});
	}

	public List findList(final String hsql, final Object[] para,
			final int maxResults) {
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public List doInHibernate(Session session) {
						Query query = session.createQuery(hsql).setMaxResults(
								maxResults);
						if (null != para && para.length != 0)
							bindProperties(query, para);
						return query.list();
					}
				});
	}

	/**
	 * 查询结果 最大结果集 ,防止结果集太大造成内存溢出
	 * 
	 * @param hsql
	 * @param para
	 * @return
	 */
	public List findListMax(final String hsql, final Object[] para,
			final int max) {
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						Query query = session.createQuery(hsql).setMaxResults(
								max);
						if (null != para && para.length != 0)
							bindProperties(query, para);
						return query.list();
					}
				});
	}

	/**
	 * 查询结果集 ,一个以上po的交叉collection
	 * 
	 * @param hsql
	 * @param para
	 * @return
	 */
	public List<Map<String, Object>> findListMap(final String hsql,
			final Object[] para) {
		return (List<Map<String, Object>>) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						Query query = session.createQuery(hsql);
						query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
						if (null != para && para.length != 0)
							bindProperties(query, para);
						return query.list();
					}
				});
	}

	/**
	 * 查询结果 最大结果集 ,防止结果集太大造成内存溢出
	 * 
	 * @param hsql
	 * @param para
	 * @return
	 */
	public List<Map<String, Object>> findListMapMax(final String hsql,
			final Object[] para, final int max) {
		return (List<Map<String, Object>>) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						Query query = session.createQuery(hsql).setMaxResults(
								max);
						query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
						if (null != para && para.length != 0)
							bindProperties(query, para);
						return query.list();
					}
				});
	}

	/**
	 * 查询Map值,单条结果
	 * 
	 * @param hsql
	 * @param para
	 * @return
	 */
	public Map<String, Object> findMap(final String hsql, final Object[] para) {
		return (Map<String, Object>) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Map<String, Object> doInHibernate(Session session) {
						Query query = session.createQuery(hsql);
						query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
						if (null != para && para.length != 0)
							bindProperties(query, para);
						List<Map<String, Object>> rlist = query.list();
						if (null != rlist && rlist.size() > 0)
							return rlist.get(0);
						else
							return new HashMap<String, Object>();
					}
				});
	}

	/**
	 * 使用HSQL语句,用于修改，删除
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public Object excute(final String hsql, final Object[] para) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(hsql);
				if (null != para && para.length != 0)
					bindProperties(query, para);
				return query.executeUpdate();
			}
		});
	}

	/**
	 * 使用本地SQL语句,用于修改，删除
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public Object excuteBySql(final String sql, final Object[] para) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				if (null != para && para.length != 0)
					bindProperties(sqlQuery, para);
				return sqlQuery.executeUpdate();
			}
		});
	}

	/**
	 * 使用本地SQL语句查询 此方法使用情况为当hsql语句不支持oracle的sql语句时调用
	 * 
	 * @param sql
	 */
	public List<Map<String, Object>> findListMapBySql(final String sql,
			final Object[] para) {
		return (List<Map<String, Object>>) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
						if (null != para && para.length != 0)
							bindProperties(sqlQuery, para);
						return sqlQuery.list();
					}
				});
	}

	/**
	 * 使用本地SQL语句查询单个以上po的交叉，单条结果 此方法使用情况为当hsql语句不支持oracle的sql语句时调用
	 * 
	 * @param sql
	 */
	public Map<String, Object> findMapBySql(final String sql,
			final Object[] para) {
		return (Map<String, Object>) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Map<String, Object> doInHibernate(Session session) {
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
						if (null != para && para.length != 0)
							bindProperties(sqlQuery, para);
						List<Map<String, Object>> rlist = sqlQuery.list();
						if (null != rlist && rlist.size() > 0)
							return rlist.get(0);
						else
							return new HashMap<String, Object>();
					}
				});
	}

	public List<Map<String, Object>> findListMapWithEntityBySql(
			final String sql, final Object[] para,
			final Map<String, Class> entities) {
		return (List<Map<String, Object>>) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public List<Map<String, Object>> doInHibernate(
							Session session) {
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						if (null != entities && entities.size() > 0) {
							String key = "";
							for (Iterator<String> it = entities.keySet()
									.iterator(); it.hasNext();) {
								key = it.next();
								sqlQuery.addEntity(key, entities.get(key));
							}
						}
						sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
						if (null != para && para.length != 0)
							bindProperties(sqlQuery, para);
						return sqlQuery.list();
					}
				});
	}

	public int findInt(String hsql, Object[] para) {
		List rlist = this.getHibernateTemplate().find(hsql, para);
		if (null != rlist && rlist.size() > 0) {
			Object obj = rlist.get(0);
			if (obj instanceof Long)
				return ((Long) obj).intValue();
			return (Integer) obj;
		}
		return 0;
	}

	public String findString(String hsql, Object[] para) {
		List rlist = this.getHibernateTemplate().find(hsql, para);
		if (null != rlist && rlist.size() > 0) {
			return (String) rlist.get(0);
		}
		return null;
	}

	public long findLong(String hsql, Object[] para) {
		List rlist = this.getHibernateTemplate().find(hsql, para);
		if (null != rlist && rlist.size() > 0) {
			return (Long) rlist.get(0);
		}
		return 0;
	}

	public float findFloat(String hsql, Object[] para) {
		List rlist = this.getHibernateTemplate().find(hsql, para);
		if (null != rlist && rlist.size() > 0) {
			return (Float) rlist.get(0);
		}
		return 0.0F;
	}

	public Object findObject(String hsql, Object[] para) {
		List rlist = this.getHibernateTemplate().find(hsql, para);
		if (rlist.size() > 0)
			return rlist.get(0);
		return null;
	}

	public Object findObjectBySql(final String sql, final Object[] para,
			final Class entity) {
		return (Object) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						sqlQuery.addEntity(entity);
						if (null != para && para.length != 0)
							bindProperties(sqlQuery, para);
						List<Object> rlist = sqlQuery.list();
						if (null != rlist && rlist.size() > 0) {
							return rlist.get(0);
						} else
							return null;
					}
				});
	}

	/**
	 * 本地SQL
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public int findIntBySql(final String sql, final Object[] para) {
		return (Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Integer doInHibernate(Session session) {
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						if (null != para && para.length != 0)
							bindProperties(sqlQuery, para);
						List rlist = sqlQuery.list();
						if (null != rlist && rlist.size() > 0)
							return rlist.get(0) == null ? 0 : NumberUtils
									.toInt(rlist.get(0).toString());
						else
							return 0;
					}
				});
	}

	/**
	 * 本地SQL
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public String findStringBySql(final String sql, final Object[] para) {
		return (String) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public String doInHibernate(Session session) {
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						if (null != para && para.length != 0)
							bindProperties(sqlQuery, para);
						List rlist = sqlQuery.list();
						if (null != rlist && rlist.size() > 0)
							return (String) rlist.get(0);
						else
							return null;
					}
				});
	}

	/**
	 * 本地SQL
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public long findLongBySql(final String sql, final Object[] para) {
		return (Long) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Long doInHibernate(Session session) {
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						if (null != para && para.length != 0)
							bindProperties(sqlQuery, para);
						List rlist = sqlQuery.list();
						if (null != rlist && rlist.size() > 0
								&& null != rlist.get(0)) {
							if (MyUtil.getInstance().equals(
									rlist.get(0).getClass().getName(),
									"java.math.BigInteger")) {
								BigInteger bd = (BigInteger) rlist.get(0);
								return bd.longValue();
							} else {
								return (Long) rlist.get(0);
							}
						} else
							return 0L;
					}
				});
	}

	/**
	 * 本地SQL
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public float findFloatBySql(final String sql, final Object[] para) {
		return (Float) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Float doInHibernate(Session session) {
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						if (null != para && para.length != 0)
							bindProperties(sqlQuery, para);
						List rlist = sqlQuery.list();
						if (null != rlist && rlist.size() > 0
								&& null != rlist.get(0))
							return NumberUtils.toFloat(rlist.get(0).toString());
						else
							return 0.0F;
					}
				});
	}

	/**
	 * hsql分页查询 ,一个po的collection
	 * 
	 * @param hsql
	 * @param para
	 * @param currentPage
	 * @param everyPageNum
	 * @return
	 */
	public List findListPage(final String hsql, final Object[] para,
			final PageBar pb) {
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public List doInHibernate(Session session) {
						Query query = session.createQuery(hsql);
						query.setCacheable(true);
						int firstResult = (pb.getCurrentPageNum() - 1)
								* pb.getEveryPageNum();
						int maxResult = pb.getEveryPageNum();
						query.setFirstResult(firstResult).setMaxResults(
								maxResult);
						if (null != para && para.length != 0)
							bindProperties(query, para);
						return query.list();
					}
				});
	}

	/**
	 * hsql分页查询，一个以上的结果集
	 * 
	 * @param hsql
	 * @param para
	 * @param currentPage
	 * @param everyPageNum
	 * @return
	 */
	public List<Map<String, Object>> findListMapPage(final String hsql,
			final Object[] para, final PageBar pb) {
		return (List<Map<String, Object>>) this.getHibernateTemplate().execute(
				new HibernateCallback<Object>() {
					public List<Map<String, Object>> doInHibernate(
							Session session) {
						Query query = session.createQuery(hsql);
						int firstResult = (pb.getCurrentPageNum() - 1)
								* pb.getEveryPageNum();
						int maxResult = pb.getEveryPageNum();
						query.setFirstResult(firstResult)
								.setResultTransformer(
										Criteria.ALIAS_TO_ENTITY_MAP)
								.setMaxResults(maxResult);
						if (null != para && para.length != 0)
							bindProperties(query, para);
						return query.list();
					}
				});
	}

	/**
	 * 本地sql分页查询，一个以上的结果集
	 * 
	 * @param sql
	 * @param para
	 * @param currentPage
	 * @param everyPageNum
	 * @return
	 */
	public List<Map<String, Object>> findListMapPageBySql(final String sql,
			final Object[] para, final PageBar pb) {
		return (List<Map<String, Object>>) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public List<Map<String, Object>> doInHibernate(
							Session session) {
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						int firstResult = (pb.getCurrentPageNum() - 1)
								* pb.getEveryPageNum();
						int maxResult = pb.getEveryPageNum();
						sqlQuery.setResultTransformer(
								Criteria.ALIAS_TO_ENTITY_MAP)
								.setFirstResult(firstResult)
								.setMaxResults(maxResult);
						if (null != para && para.length != 0)
							bindProperties(sqlQuery, para);
						return sqlQuery.list();
					}
				});
	}

	/**
	 * 使用本地SQL语句,用于修改，删除
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public Object excuteBySql(final String sql) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				return sqlQuery.executeUpdate();
			}
		});
	}

	public List findListBySql(final String sql, final Object[] para) {
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						Query query = session.createSQLQuery(sql);
						if (null != para && para.length != 0)
							bindProperties(query, para);
						return query.list();
					}
				});
	}

	private Query bindProperties(Query query, Object[] para) {
		String ptype;
		if (checkSqlPara(para))
			return null;
		for (int i = 0; i < para.length; i++) {
			if (para[i] == null) {
				query.setString(i, null);
				continue;
			}
			ptype = para[i].getClass().getName().toLowerCase();
			if (ptype.indexOf("string") != -1) {
				query.setString(i, para[i].toString());
				continue;
			}
			if (ptype.indexOf("long") != -1) {
				query.setLong(i, (Long) para[i]);
				continue;
			}
			if (ptype.indexOf("integer") != -1) {
				query.setInteger(i, (Integer) para[i]);
				continue;
			}
			if (ptype.indexOf("double") != -1) {
				query.setDouble(i, (Double) para[i]);
				continue;
			}
			if (ptype.indexOf("date") != -1) {
				query.setDate(i, (Date) para[i]);
				continue;
			}
			if (ptype.indexOf("float") != -1) {
				query.setFloat(i, (Float) para[i]);
				continue;
			}
			if (ptype.indexOf("boolean") != -1) {
				query.setBoolean(i, (Boolean) para[i]);
				continue;
			}
			if (ptype.indexOf("byte") != -1) {
				query.setByte(i, (Byte) para[i]);
				continue;
			}
			if (ptype.indexOf("character") != -1) {
				query.setCharacter(i, (Character) para[i]);
				continue;
			}
			query.setParameter(i, para[i]);
		}
		return query;
	}

	private final static StringBuilder regSql = new StringBuilder("");
	private static Pattern pattern = null;

	public void initRegSql() {
		if (regSql.length() == 0) {
			regSql.append(".*(?i)((insert|select|union|update|count|delete)){1}.");
			Map poNameMap = getHibernateTemplate().getSessionFactory()
					.getAllClassMetadata();
			if (poNameMap.size() > 0) {
				regSql.append("(");
			}
			for (Iterator i = poNameMap.values().iterator(); i.hasNext();) {
				SingleTableEntityPersister step = (SingleTableEntityPersister) i
						.next();
				regSql.append(step.getTableName())
						.append("|")
						.append(step.getEntityName().substring(
								step.getEntityName().lastIndexOf(".") + 1,
								step.getEntityName().length())).append("|");
			}
			if (poNameMap.size() > 0) {
				regSql.delete(regSql.length() - 1, regSql.length());
				regSql.append("){1}?");
			}
			pattern = Pattern.compile(regSql.toString());
		}
	}

	private boolean checkSqlPara(Object[] para) {
		for (Object t : para) {
			if (null == t)
				continue;
			Matcher m = pattern.matcher(t.toString());
			return m.matches();
		}
		return false;
	}

	private boolean checkSqlPara(Object obj) {
		boolean flag = false;
		try {
			Field[] fs = obj.getClass().getDeclaredFields();
			String ftype = "";
			String getMethod = "";
			// String setMethod="";
			Object value;
			Reflection re = Reflection.getInstance();
			for (Field f : fs) {
				ftype = f.getType().toString().toLowerCase();
				if (ftype.indexOf("string") == -1)
					continue;
				getMethod = "get" + f.getName().substring(0, 1).toUpperCase()
						+ f.getName().substring(1, f.getName().length());
				value = re.invokeMethod(obj, getMethod, new Object[] {});
				if (null == value)
					continue;
				// value=mu.htmEncode(value.toString());
				Matcher m = pattern.matcher(value.toString());
				// setMethod="set"+f.getName().substring(0,
				// 1).toUpperCase()+f.getName().substring(1,f.getName().length());
				// re.invokeMethod(obj,setMethod,new Object[]{value});
				if (m.matches()) {
					flag = true;
					break;
				}
			}
			return flag;
		} catch (Exception e) {
			log.info("checkSqlPara error", e);
			return flag;
		}
	}

}