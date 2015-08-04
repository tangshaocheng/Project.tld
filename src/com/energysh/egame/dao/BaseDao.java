package com.energysh.egame.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.energysh.egame.util.PageBar;

@SuppressWarnings("unchecked")
public interface BaseDao {

	public void save(Object obj);

	public void update(Object obj);

	public void bulkUpdate(String sql);

	public void bulkUpdate(String sql, Object[] para);

	public void saveOrUpdate(Object obj);

	public void del(Object obj);

	public void delAll(Collection<Object> objs);

	public Object get(Class entityClass, Integer id);

	/**
	 * 首先在session缓存中查找，然后在二级缓存中查找，还没有就查数据库，数据库中没有就返回null。 推荐使用
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Object get(Class entityClass, Long id);

	/**
	 * 会先查一下session缓存看看该id对应的对象是否存在，不存在则创建代理,实际使用时再加载实体对象到代理对象,可以实现延迟加载 必须确定load的对象在数据库中确实存在，不然不要适应 不推荐使用
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Object load(Class entityClass, Long id);

	/**
	 * 指定清除某对象缓存(慎用)
	 * 
	 * @param obj
	 */
	public void evict(Object obj);

	/**
	 * 把缓冲区内的全部对象清除，但不包括操作中的对(慎用)
	 */
	public void clear();

	/**
	 * 使用本地SQL语句分页查询
	 */
	public List findListBySql(final String sql, final Object[] para, final int pageNo, final int pageSize, Class entity);

	/**
	 * 使用本地SQL全量查询
	 * 
	 * @param sql
	 * @param para
	 * @param entity
	 * @return
	 */
	public List findListBySql(final String sql, final Object[] para, Class entity);

	/**
	 * 查询结果集 ,一个po的collection
	 * 
	 * @param hsql
	 * @param para
	 * @return
	 */
	public List findList(final String hsql, final Object[] para);

	public List findList(final String hsql, final Object[] para, final int maxResults);

	/**
	 * 查询结果 最大结果集 ,防止结果集太大造成内存溢出
	 * 
	 * @param hsql
	 * @param para
	 * @return
	 */
	public List findListMax(final String hsql, final Object[] para, final int max);

	/**
	 * 查询结果集 ,一个以上po的交叉collection
	 * 
	 * @param hsql
	 * @param para
	 * @return
	 */
	public List<Map<String, Object>> findListMap(final String hsql, final Object[] para);

	/**
	 * 查询结果 最大结果集 ,防止结果集太大造成内存溢出
	 * 
	 * @param hsql
	 * @param para
	 * @return
	 */
	public List<Map<String, Object>> findListMapMax(final String hsql, final Object[] para, final int max);

	/**
	 * 查询Map值,单条结果
	 * 
	 * @param hsql
	 * @param para
	 * @return
	 */
	public Map<String, Object> findMap(final String hsql, final Object[] para);

	/**
	 * 使用HSQL语句,用于修改，删除
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public Object excute(final String hsql, final Object[] para);

	/**
	 * 使用本地SQL语句,用于修改，删除
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public Object excuteBySql(final String sql, final Object[] para);

	/**
	 * 使用本地SQL语句查询 此方法使用情况为当hsql语句不支持oracle的sql语句时调用
	 * 
	 * @param sql
	 */
	public List<Map<String, Object>> findListMapBySql(final String sql, final Object[] para);

	/**
	 * 使用本地SQL语句查询单个以上po的交叉，单条结果 此方法使用情况为当hsql语句不支持oracle的sql语句时调用
	 * 
	 * @param sql
	 */
	public Map<String, Object> findMapBySql(final String sql, final Object[] para);

	public int findInt(String hsql, Object[] para);

	public String findString(String hsql, Object[] para);

	public long findLong(String hsql, Object[] para);

	public float findFloat(String hsql, Object[] para);

	public Object findObject(String hsql, Object[] para);

	public Object findObjectBySql(final String sql, final Object[] para, final Class entity);

	/**
	 * 本地SQL
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public int findIntBySql(final String sql, final Object[] para);

	/**
	 * 本地SQL
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public String findStringBySql(final String sql, final Object[] para);

	/**
	 * 本地SQL
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public long findLongBySql(final String sql, final Object[] para);

	/**
	 * 本地SQL
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public float findFloatBySql(final String sql, final Object[] para);

	/**
	 * hsql分页查询 ,一个po的collection
	 * 
	 * @param hsql
	 * @param para
	 * @param currentPage
	 * @param everyPageNum
	 * @return
	 */
	public List findListPage(final String hsql, final Object[] para, final PageBar pb);

	/**
	 * hsql分页查询，一个以上的结果集
	 * 
	 * @param hsql
	 * @param para
	 * @param currentPage
	 * @param everyPageNum
	 * @return
	 */
	public List<Map<String, Object>> findListMapPage(final String hsql, final Object[] para, final PageBar pb);

	/**
	 * 本地sql分页查询，一个以上的结果集
	 * 
	 * @param sql
	 * @param para
	 * @param currentPage
	 * @param everyPageNum
	 * @return
	 */
	public List<Map<String, Object>> findListMapPageBySql(final String sql, final Object[] para, final PageBar pb);

	/**
	 * 使用本地SQL语句,用于修改，删除
	 * 
	 * @param sql
	 * @param para
	 * @return
	 */
	public Object excuteBySql(final String sql);

	public List<Map<String, Object>> findListMapWithEntityBySql(final String sql, final Object[] para, final Map<String, Class> entities);

	public void initRegSql();

	public List findListBySql(final String sql, final Object[] para);
}
