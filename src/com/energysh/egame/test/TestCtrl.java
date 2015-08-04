package com.energysh.egame.test;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.service.InterfaceService;
import com.energysh.egame.service.TaskService;
import com.energysh.egame.web.BaseController;
import com.energysh.egame.web.rs.InterfaceController;

public class TestCtrl extends BaseController {

	private InterfaceService interfaceService;

	public void setInterfaceService(InterfaceService interfaceService) {
		this.interfaceService = interfaceService;
	}
	
	
	private TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	private static final Logger LOGGER = Logger.getLogger(InterfaceController.class);

	@Override
	public ModelAndView excute() {
		taskService.uuQihooApp();
		return null;
	}

	
}