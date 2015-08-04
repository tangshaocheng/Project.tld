package com.energysh.egame.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

public class EnvConfigurer extends PropertyPlaceholderConfigurer {

	private static Map<String, String> cmap = new HashMap<String, String>();

	@SuppressWarnings("unchecked")
	public void setMyLocations(Resource[] myLocations) {
		java.util.Properties[] ppa = new java.util.Properties[myLocations.length];
		for (Resource rs : myLocations) {
			try {
				java.util.Properties pp = new java.util.Properties();
				pp.load(rs.getInputStream());
				String key = "";
				for (Iterator it = pp.keySet().iterator(); it.hasNext();) {
					key = it.next().toString();
					cmap.put(key, pp.get(key).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.setPropertiesArray(ppa);
		this.setLocalOverride(true);// 覆盖以前的配置
		this.setLocations(myLocations);
	}

	public static String getEvn(String key) {
		return cmap.get(key);
	}

}
