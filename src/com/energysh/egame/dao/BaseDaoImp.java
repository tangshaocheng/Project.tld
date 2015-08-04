package com.energysh.egame.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.energysh.egame.util.PageBar;
import com.energysh.egame.util.Reflection;

@SuppressWarnings("unchecked")
public class BaseDaoImp implements BaseDao {

	private MutilBaseDaoImp queryBaseDao;
	private MutilBaseDaoImp writeBaseDao;
	
	
	public MutilBaseDaoImp getQueryBaseDao() {
		return queryBaseDao;
	}

	public void setQueryBaseDao(MutilBaseDaoImp queryBaseDao) {
		this.queryBaseDao = queryBaseDao;
	}

	public MutilBaseDaoImp getWriteBaseDao() {
		return writeBaseDao;
	}

	public void setWriteBaseDao(MutilBaseDaoImp writeBaseDao) {
		this.writeBaseDao = writeBaseDao;
	}

	private final static Log log = LogFactory.getLog(BaseDaoImp.class);

	public void save(Object obj) {
		
		writeBaseDao.save(obj);
	}

	public Object get(Serializable id, Class clazz) {
		return queryBaseDao.get( id,clazz);
	}

	public void update(Object obj) {
		writeBaseDao.update(obj);
	}

	public void bulkUpdate(String sql) {
		writeBaseDao.bulkUpdate(sql);
	}

	public void bulkUpdate(String sql, Object[] para) {
		writeBaseDao.bulkUpdate(sql, para);
	}

	public void saveOrUpdate(Object obj) {
		
		writeBaseDao.saveOrUpdate(obj);
	}

	public void del(Object obj) {
	
		writeBaseDao.del(obj);

	}

	public void delAll(Collection<Object> objs) {
		writeBaseDao.delAll(objs);
	}

	public Object get(Class entityClass, Integer id) {
		return queryBaseDao.get(entityClass, id);
	}

	/**
	 * 首先在session缓存中查找，然后在二级缓存中查找，还没有就查数据库，数据库中没有就返回null。 推荐使用
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Object get(Class entityClass, Long id) {
		return queryBaseDao.get(entityClass, id);
	}

	/**
	 * 会先查一下session缓存看看该id对应的对象是否存在，不存在则创建代理,实际使用时再加载实体对象到代理对象,可以实现延迟加载 必须确定load的对象在数据库中确实存在，不然不要适应 不推荐使用
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Object load(Class entityClass, Long id) {
		return queryBaseDao.load(entityClass, id);
	}

	/**
	 * 指定清楚某对象缓(慎用)
	 * 
	 * @param obj
	 */
	public void evict(Object obj) {
		writeBaseDao.evict(obj);
	}

	/**
	 * 把缓冲区内的全部对象清除，但不包括操作中的对�?慎用
	 */
	public void clear() {
		writeBaseDao.clear();
	}

	public List<Object> findByHql(String queryString) throws DataAccessException {
		return queryBaseDao.findByHql(queryString);
	}

	public List findListBySql(final String sql, final Object[] para, final int pageNo, final int pageSize, final Class entity) {
		return queryBaseDao.findListBySql(sql, para, pageNo, pageSize, entity);
	}

	/**
	 * 使用本地SQL全量查询
	 * 
	 * @param sql
	 * @param para
	 * @param entity
	 * @return
	 */
	public List findListBySql(final String sql, final Object[] para, final Class entity) {
		return queryBaseDao.findListBySql(sql, para, entity);
	}

	/**
	 * 查询结果集 ,一个po的collection
	 * 
	 * @param hsql
	 * @param para
	 * @return List
	 */
	public List findList(final String hsql, final Object[] para) {
		return queryBaseDao.findList(hsql, para);
	}

	public List findList(final String hsql, final Object[] para, final int maxResults) {
		return queryBaseDao.findList(hsql, para, maxResults);
	}

	/**
	 * 查询结果 最大结果集 ,防止结果集太大造成内存溢出
	 * 
	 * @param hsql
	 * @param para
	 * @return
	 */
	public List findListMax(final String hsql, final Object[] para, final int max) {
		return queryBaseDao.findListMax(hsql, para, max);
	}

	/**
	 * 查询结果集 ,一个以上po的交叉collection
	 * 
	 * @param hsql
	 * @param para
	 * @return
	 */
	public List<Map<String, Object>> findListMap(final String hsql, final Object[] para) {
		return queryBaseDao.findListMap(hsql, para);
	}

	/**
	 * 查询结果 最大结果集 ,防止结果集太大造成内存溢出
	 * 
	 * @param hsql
	 * @param para
	 * @return
	 */
	public List<Map<String, Object>> findListMapMax(final String hsql, final Object[] para, final int max) {
		return queryBaseDao.findListMapMax(hsql, para, max);
	}

	/**
	 * 查询Map值,单条结果
	 * 
	 * @param hsql
	 * @param para
	 * @return
	 */
	public Map<String, Object> findMap(final String hsql, final Object[] para) {
		return queryBaseDao.findMap(hsql, para);
	}

	/**
	 * 使用HSQL语句,用于修改，删除
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public Object excute(final String hsql, final Object[] para) {
		return writeBaseDao.excute(hsql, para);
	}

	/**
	 * 使用本地SQL语句,用于修改，删除
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public Object excuteBySql(final String sql, final Object[] para) {
		return writeBaseDao.excuteBySql(sql, para);
	}

	/**
	 * 使用本地SQL语句查询 此方法使用情况为当hsql语句不支持oracle的sql语句时调用
	 * 
	 * @param sql
	 */
	public List<Map<String, Object>> findListMapBySql(final String sql, final Object[] para) {
		return queryBaseDao.findListMapBySql(sql, para);
	}

	/**
	 * 使用本地SQL语句查询单个以上po的交叉，单条结果 此方法使用情况为当hsql语句不支持oracle的sql语句时调用
	 * 
	 * @param sql
	 */
	public Map<String, Object> findMapBySql(final String sql, final Object[] para) {
		return queryBaseDao.findMapBySql(sql, para);
	}

	public List<Map<String, Object>> findListMapWithEntityBySql(final String sql, final Object[] para, final Map<String, Class> entities) {
		return queryBaseDao.findListMapWithEntityBySql(sql, para, entities);
	}

	public int findInt(String hsql, Object[] para) {
		return queryBaseDao.findInt(hsql, para);
	}

	public String findString(String hsql, Object[] para) {
		return queryBaseDao.findString(hsql, para);
	}

	public long findLong(String hsql, Object[] para) {
		return queryBaseDao.findLong(hsql, para);
	}

	public float findFloat(String hsql, Object[] para) {
		return queryBaseDao.findFloat(hsql, para);
	}

	public Object findObject(String hsql, Object[] para) {
		return queryBaseDao.findObject(hsql, para);
	}

	public Object findObjectBySql(final String sql, final Object[] para, final Class entity) {
		return queryBaseDao.findObjectBySql(sql, para, entity);
	}

	/**
	 * 本地SQL
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public int findIntBySql(final String sql, final Object[] para) {
		return queryBaseDao.findIntBySql(sql, para);
	}

	/**
	 * 本地SQL
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public String findStringBySql(final String sql, final Object[] para) {
		return queryBaseDao.findStringBySql(sql, para);
	}

	/**
	 * 本地SQL
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public long findLongBySql(final String sql, final Object[] para) {
		return queryBaseDao.findLongBySql(sql, para);
	}

	/**
	 * 本地SQL
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public float findFloatBySql(final String sql, final Object[] para) {
		return queryBaseDao.findFloatBySql(sql, para);
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
	public List findListPage(final String hsql, final Object[] para, final PageBar pb) {
		return queryBaseDao.findListPage(hsql, para, pb);
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
	public List<Map<String, Object>> findListMapPage(final String hsql, final Object[] para, final PageBar pb) {
		return queryBaseDao.findListMapPage(hsql, para, pb);
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
	public List<Map<String, Object>> findListMapPageBySql(final String sql, final Object[] para, final PageBar pb) {
		return queryBaseDao.findListMapPageBySql(sql, para, pb);
	}

	/**
	 * 使用本地SQL语句,用于修改，删除
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public Object excuteBySql(final String sql) {
		return writeBaseDao.excuteBySql(sql);
	}

	public List findListBySql(final String sql, final Object[] para) {
		return queryBaseDao.findListBySql(sql, para);
	}

	@Override
	public void initRegSql() {
		queryBaseDao.initRegSql();
		
	}



	

}