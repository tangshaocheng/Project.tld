package com.energysh.egame.util;

import java.util.concurrent.atomic.AtomicInteger;

public class ParamsUtil {

	public static AtomicInteger spLogsId = null;

	public static void initSpLogsId(int maxValue) {
		spLogsId = new AtomicInteger(maxValue);
	}

	public static int nextSpLogsId() {
		return spLogsId.incrementAndGet();
	}
}
