package com.energysh.egame.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 
 * TODO SystemArchitecture
 * 
 * @author tuhua
 * @version 1.0
 *          <p>
 *          <b>功能说明：系统切面控制</b>
 *          </p>
 * <br>
 *          TODO 填写该类功能
 *          <p>
 *          <b>创建时间：</b>2010-4-14
 *          </p>
 *          <p>
 *          <b>修改时间：</b>2010-4-14
 *          </p>
 */
@Aspect
public class SystemArchitecture {

	@Pointcut("within(com.energysh.egame.web.excute*)")
	public void webLayer() {
	}

	@Pointcut("within(com.energysh.egame.service..*)")
	public void serviceLayer() {
	}

	@Pointcut("within(com.energysh.egame.service..*)")
	public void cacheLayer() {
	}
}
