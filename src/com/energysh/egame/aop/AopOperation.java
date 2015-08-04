package com.energysh.egame.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.ResourceUtils;

import com.energysh.egame.service.MemcachedService;
import com.energysh.egame.util.MyUtil;

@Aspect
@SuppressWarnings("unchecked")
public class AopOperation {

	private final static Log log = LogFactory.getLog(AopOperation.class);
	private static Map<String, Map<String, String>> cacheMethod = new HashMap<String, Map<String, String>>();

	static {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(ResourceUtils
					.getFile("classpath:cache.xml"));
			List<Element> xlist = document.getRootElement().elements();
			for (Element e : xlist) {
				Map<String, String> value = new HashMap<String, String>();
				value.put("cacheKey", e.elementText("cacheKey"));
				value.put("expirtTime", e.elementText("expirtTime"));
				cacheMethod.put(e.elementText("service"), value);
			}
		} catch (Exception e) {
			log.error("load cache.xml error: ", e);
		}
	}

	@AfterThrowing(pointcut = "com.energysh.egame.aop.SystemArchitecture.serviceLayer()", throwing = "ex")
	public void doServiceLog(JoinPoint point, Exception ex) {
		log.info("service exception:[class:"
				+ point.getSignature().getDeclaringTypeName() + ",method:"
				+ point.getSignature().getName() + "] exception:", ex);
	}

	@AfterThrowing(pointcut = "com.energysh.egame.aop.SystemArchitecture.webLayer()", throwing = "ex")
	public void doWebLog(JoinPoint point, Exception ex) {
		log.info("web exception:[class:"
				+ point.getSignature().getDeclaringTypeName() + ",method:"
				+ point.getSignature().getName() + "] exception:", ex);
	}

	@Around("com.energysh.egame.aop.SystemArchitecture.cacheLayer()")
	public Object doCache(ProceedingJoinPoint pjp) {
		String key = pjp.getSignature().getDeclaringTypeName() + "."
				+ pjp.getSignature().getName();
		try {
			if (!cacheMethod.containsKey(key))
				return pjp.proceed();
			// 需要缓冲的字段
			String[] cachePro = cacheMethod.get(key).get("cacheKey")
					.split("[,]");
			String expirtTime = cacheMethod.get(key).get("expirtTime");
			StringBuilder cacheKey = new StringBuilder(key);
			Object args0 = pjp.getArgs()[0];
			if (args0 instanceof Map) {
				Map<String, String> para = (Map<String, String>) args0;
				for (String c : cachePro) {
					cacheKey.append("|").append(para.get(c));
				}
			} else {
				cacheKey.append("|").append(args0);
			}
			Object returnObj = this.getMemProgrammer().get(cacheKey.toString());
			if (null == returnObj) {
				MyUtil mu = MyUtil.getInstance();
				returnObj = pjp.proceed();
				if (null == returnObj)
					return null;
				if (mu.equals(expirtTime, "-1"))
					this.getMemProgrammer().set(cacheKey.toString(), returnObj);
				else
					this.getMemProgrammer().set(cacheKey.toString(),
							mu.toInt(expirtTime), returnObj);
			}
			return returnObj;
		} catch (Throwable e) {
			log.error("programer cache error: ", e);
		}
		return null;
	}

	private MemcachedService memProgrammer = null;

	public MemcachedService getMemProgrammer() {
		return memProgrammer;
	}

	public void setMemProgrammer(MemcachedService memProgrammer) {
		this.memProgrammer = memProgrammer;
	}

	public static Map<String, Map<String, String>> getCacheMethod() {
		return cacheMethod;
	}
}
