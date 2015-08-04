package com.energysh.egame.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

public class MyConfigurer extends PropertyPlaceholderConfigurer {

	private final static Log log = LogFactory.getLog(MyConfigurer.class);
	private static Map<String, String> cmap = new HashMap<String, String>();

	public void setMyLocations(Resource[] myLocations) {
		Properties[] ppa = new Properties[myLocations.length];
		MyUtil mu = MyUtil.getInstance();
		int ppaIndex = 0;
		for (Resource rs : myLocations) {
			try {
				Properties pp = new Properties();
				pp.load(rs.getInputStream());
				List<String> removeKey = new ArrayList<String>();
				String key = "";
				for (Iterator<Object> it = pp.keySet().iterator(); it.hasNext();) {
					key = it.next().toString();
					if (null != pp.get(key + "/")
							&& mu.equals(File.separator, "/")) {
						cmap.put(key, pp.get(key + "/").toString());
						removeKey.add(key);
					} else {
						cmap.put(key, pp.get(key).toString());
					}
				}
				if (removeKey.size() > 0) {
					for (String rk : removeKey) {
						if (mu.equals(File.separator, "/")) {// linux
							pp.setProperty(rk, pp.getProperty(rk + "/"));
						}
					}
					OutputStream out = new FileOutputStream(rs.getFile());
					pp.store(out, "xxx");
					out.close();
				}
				ppa[ppaIndex] = pp;
				ppaIndex++;
			} catch (IOException e) {
				log.error("load Evn error: ", e);
			}
		}
		this.setPropertiesArray(ppa);
		this.setLocalOverride(true);// 覆盖以前的配�? this.setLocations(myLocations);
	}

	public static String getEvn(String key) {
		return cmap.get(key);
	}
}
