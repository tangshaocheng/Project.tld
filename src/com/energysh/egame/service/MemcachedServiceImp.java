package com.energysh.egame.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;

import net.sf.json.JSONObject;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MemcachedServiceImp implements MemcachedService {

	private final static Log log = LogFactory.getLog(MemcachedServiceImp.class);
	private MemcachedClient memcachedClient;
	private String memIp;

	public void init() {
		try {
			memcachedClient = new MemcachedClient(AddrUtil.getAddresses(memIp));
		} catch (IOException e) {
			log.error("memcacheClient初始化错误：" + e);
		}
	}

	public Future<Boolean> add(String key, int timeout, Object value) {
		try {
			return memcachedClient.add(key, timeout, value);
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return null;
	}

	public Future<Boolean> add(String key, Object value) {
		try {
			return add(key, 0, value);
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return null;
	}

	public Future<Boolean> delete(String key) {
		try {
			return memcachedClient.delete(key);
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return null;
	}

	public Object get(String key) {
		try {
			return memcachedClient.get(key);
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getMapByKey(String key) {
		try {
			Object obj = memcachedClient.get(key);
			String value = obj == null ? null : obj.toString();
			if (null == value)
				return null;
			Map<String, String> rMap = new HashMap<String, String>();
			value = value.replaceAll("NaN", "''");
			JSONObject sobj = JSONObject.fromObject(value);
			for (Iterator<String> iterator = sobj.keys(); iterator.hasNext();) {
				String tkey = iterator.next();
				rMap.put(tkey, sobj.get(tkey).toString());
			}
			return rMap;
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getMapsByKey(String key) {
		try {
			Object obj = memcachedClient.get(key);
			String value = obj == null ? null : obj.toString();
			if (null == value)
				return null;
			Map<String, Object> rMap = new HashMap<String, Object>();
			value = value.replaceAll("NaN", "''");
			JSONObject sobj = JSONObject.fromObject(value);
			for (Iterator<String> iterator = sobj.keys(); iterator.hasNext();) {
				String tkey = iterator.next();
				rMap.put(tkey, sobj.get(tkey));
			}
			return rMap;
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return null;
	}

	public Future<Boolean> replace(String key, int timeout, Object value) {
		try {
			return memcachedClient.replace(key, timeout, value);
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return null;
	}

	public Future<Boolean> replace(String key, Object value) {
		try {
			return replace(key, 0, value);
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return null;
	}

	public Future<Boolean> set(String key, int timeout, Object value) {
		try {
			return memcachedClient.set(key, timeout, value);
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return null;
	}

	public Future<Boolean> set(String key, Object value) {
		try {
			return memcachedClient.set(key, 0, value);
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return null;
	}

	public void shutdown() {
		try {
			memcachedClient.shutdown();
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
	}

	public long incr(String key, int step, long initValue) {
		try {
			if (null == memcachedClient.get(key)) {
				for (int index = 0; index < step; index++)
					memcachedClient.incr(key, 1, initValue);
				return NumberUtils.toLong(memcachedClient.get(key).toString());
			} else {
				return memcachedClient.incr(key, step, initValue);
			}
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return 0L;
	}

	public long incr(String key, int step) {
		try {
			return memcachedClient.incr(key, step);
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return 0L;
	}

	public long incrOne(String key, int step, long initValue, int expired) {
		try {
			return memcachedClient.incr(key, step, initValue, expired);
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return 0L;
	}

	public Future<Boolean> flush() {
		try {
			return memcachedClient.flush();
		} catch (Exception e) {
			log.error("memcache error: ", e);
		}
		return null;
	}

	public String getMemIp() {
		return memIp;
	}

	public void setMemIp(String memIp) {
		this.memIp = memIp;
	}
}
