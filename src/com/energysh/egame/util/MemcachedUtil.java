package com.energysh.egame.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import net.sf.json.JSONObject;
import net.spy.memcached.MemcachedClient;

public class MemcachedUtil {

	private static MemcachedClient memClient;
	private static MemcachedUtil memUtil = new MemcachedUtil();

	static {
		String[] server = EnvConfigurer.getEvn("memcache.ttserver").split("[:]");
		InetSocketAddress ica = new InetSocketAddress(server[0], Integer.parseInt(server[1]));
		try {
			memClient = new MemcachedClient(ica);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static MemcachedUtil getIntance() {
		return memUtil;
	}

	public Future<Boolean> add(String key, int timeout, Object value) throws TimeoutException, InterruptedException {

		Future<Boolean> future = memClient.add(key, timeout, value);
		return future;
	}

	public Future<Boolean> add(String key, Object value) throws TimeoutException, InterruptedException {
		return add(key, 0, value);
	}

	public Future<Boolean> delete(String key) throws TimeoutException, InterruptedException {
		Future<Boolean> future = memClient.delete(key);
		return future;
	}

	public Object get(String key) throws TimeoutException, InterruptedException {
		return memClient.get(key);
	}

	public Future<Boolean> replace(String key, int timeout, Object value) throws TimeoutException, InterruptedException {
		Future<Boolean> future = memClient.replace(key, timeout, value);
		return future;
	}

	public Future<Boolean> replace(String key, Object value) throws TimeoutException, InterruptedException {
		return replace(key, 0, value);
	}

	public Future<Boolean> set(String key, int timeout, Object value) throws TimeoutException, InterruptedException {
		Future<Boolean> future = memClient.set(key, timeout, value);
		return future;
	}

	public Future<Boolean> set(String key, Object value) throws TimeoutException, InterruptedException {
		Future<Boolean> future = memClient.set(key, 0, value);
		return future;
	}

	public long incr(String key, int step, long initValue) throws TimeoutException, InterruptedException {
		return memClient.incr(key, step, initValue);
	}

	public long incr(String key, int step) throws TimeoutException, InterruptedException {
		return memClient.incr(key, step);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getMapByKey(String key) throws TimeoutException, InterruptedException {
		Object obj = this.get(key);
		if (null == obj)
			return null;
		return (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(obj), HashMap.class);
	}
}
