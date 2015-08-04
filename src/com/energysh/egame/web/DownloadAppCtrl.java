package com.energysh.egame.web;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.service.InterfaceService;
import com.energysh.egame.util.Constants;
import com.energysh.egame.util.Servlets;
import com.energysh.egame.web.rs.InterfaceController;

public class DownloadAppCtrl extends BaseController {

	private InterfaceService interfaceService;

	public void setInterfaceService(InterfaceService interfaceService) {
		this.interfaceService = interfaceService;
	}

	private static final Logger LOGGER = Logger.getLogger(InterfaceController.class);

	@Override
	public ModelAndView excute() {
		try {
			StringBuilder loggerStr = new StringBuilder("download request is :" + this.getRequest().getQueryString());

			String fileName = this.getRequest().getParameter("appUrl");
			if (interfaceService.downloadApp(Servlets.getHttpGetMethodPara(this.getRequest()), this.getRequest(), this.getResponse())) {
				loggerStr.append(" ==>>Step1 Down Log: download insert Exception");
				LOGGER.info(loggerStr);
				return null;
			}
//			String filePath = Constants.FILE_ROOT + Constants.FILE_SEPARATOR + fileName;
//			File downFile = new File(filePath);
//			if (!downFile.exists()) {
//				this.getResponse().setContentLength(0);
//				this.getResponse().setHeader("Content-Disposition", "attachment;filename=fileNotExit");// 处理默认文件名的中文问题
//				this.getResponse().getOutputStream().write(new byte[0]);
//				loggerStr.append(" ==>>Step2 Down file not exsit Log: fileNotExit Exception");
//				LOGGER.info(loggerStr);
//				return null;
//			}
//
//			downFile(downFile);
//			loggerStr.append(" ==>>Step3 Down file Log: down Success");
			LOGGER.info(loggerStr);
		} catch (Exception e) {
			return this.errorAjax(e);
		}
		return null;
	}

	/**
	 * 获取文件 支持断点传输
	 * 
	 * @param downFile
	 */
	private void downFile(File downFile) {
		try {
			String range = null;
			// 特殊头处理
			if (null != this.getRequest().getHeader("RANGE")) {// 断点续传的头
				range = this.getRequest().getHeader("RANGE");
			}
			if (null != this.getRequest().getHeader("Range")) {
				range = this.getRequest().getHeader("Range");
			}
			this.getResponse().setContentType("application/x-msdownload");
			int fileLength = Integer.parseInt(Long.toString(downFile.length()));
			this.getResponse().setContentLength(fileLength);
			this.getResponse().setHeader("Content-Disposition", "attachment;filename=" + new String(downFile.getName().getBytes("gb2312"), "ISO8859-1"));// 处理默认文件名的中文问题
			int startPos = 0;
			if (null != range) {// 断点续传
				startPos = Integer.parseInt(range.replaceAll("bytes=", "").replaceAll("-$|-\\d+$", ""));
			}
			if (startPos == 0) {
				FileCopyUtils.copy(new FileInputStream(downFile), this.getResponse().getOutputStream());
			} else {// 断点续传
				this.getResponse().setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				if (startPos != 0) {
					/** 设置Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小] **/
					StringBuffer sb = new StringBuffer("bytes ");
					sb.append(Long.toString(startPos));
					sb.append("-");
					sb.append(Long.toString(fileLength - 1));
					sb.append("/");
					sb.append(Long.toString(fileLength));
					this.getResponse().setHeader("Content-Range", sb.toString());
				}
				if (startPos < fileLength) {
					fileLength = fileLength - startPos;
					this.getResponse().getOutputStream().write(FileUtils.readFileToByteArray(downFile), (int) startPos, (int) fileLength);
				}
			}
			this.getResponse().getOutputStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}