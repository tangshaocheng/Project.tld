package com.energysh.egame.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.util.Constants;

public class DownloadPictureCtrl extends BaseController {

	@Override
	public ModelAndView excute() {
		try {
			Map<String, String> para = this.getAjaxPara();
			String url = para.get("picUrl");
			
			this.getResponse().sendRedirect(url);
			
//			
//			String filePath = (StringUtils.startsWith(url, "/") ? StringUtils.substring(Constants.FILE_ROOT, 0, Constants.FILE_ROOT.length() - 1) : Constants.FILE_ROOT) + url;
//			File downFile = new File(filePath);
//			if (!downFile.exists()) {
//				this.getResponse().reset();
//				this.getResponse().setContentLength(0);
//				this.getResponse().setContentType("image/jpeg");
//				this.getResponse().getOutputStream().write(new byte[0]);
//				return null;
//			}
//			InputStream in = new FileInputStream(downFile);
//			this.getResponse().reset();
//			this.getResponse().setContentType("image/jpeg");
//			byte[] b = new byte[1024];
//			int len;
//			while ((len = in.read(b)) > 0)
//				this.getResponse().getOutputStream().write(b, 0, len);
//			this.getResponse().getOutputStream().flush();
//			in.close();
		} catch (Exception e) {
			return this.errorAjax(e);
		}
		return null;
	}
}