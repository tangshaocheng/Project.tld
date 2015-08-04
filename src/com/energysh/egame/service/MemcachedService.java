package com.energysh.egame.service;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * 根据memcache协议，写个公共接口，屏蔽每种memcache客户端的差异
 * 
 * @author xiaofeng.ma
 * 
 */
public interface MemcachedService {

	/*
	 * 存储命令（有3项：’set’、’add’、’repalce’）指示服务器储存一些由键值标识的数据。 客户端发送一行命令，后面跟着数据区块； 然后，客户端等待接收服务器回传的命令行，指示成功与否。
	 */
	Future<Boolean> add(String key, Object value);

	/**
	 * 
	 * @param key
	 *            键，对象的唯一标识符
	 * @param timeout
	 *            过期时间，单位为秒，默认为不限时间
	 * @param value
	 *            值，写入的对象数据
	 * @return
	 */
	public Future<Boolean> add(String key, int timeout, Object value);

	public Future<Boolean> replace(String key, Object value);

	public Future<Boolean> replace(String key, int timeout, Object value);

	public Future<Boolean> set(String key, Object value);

	public Future<Boolean> set(String key, int timeout, Object value);

	public long incr(String key, int step, long initValue);

	public long incrOne(String key, int step, long initValue, int expired);

	public long incr(String key, int step);

	/**
	 *取回命令（只有一项：’get’）指示服务器返回与所给键值相符合的数据（一个请求中右一个或多个键值）
	 */
	public Object get(String key);

	public Map<String, String> getMapByKey(String key);

	public Map<String, Object> getMapsByKey(String key);

	/**
	 * 删除
	 */
	public Future<Boolean> delete(String key);

	public void shutdown();

	public Future<Boolean> flush();
}
