package com.energysh.egame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

/**
 * 文件上传
 * 
 */
public class UploadHepler {

	/**
	 * 上传文件，并返回绝对路径
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> saveFileAndGetPara(HttpServletRequest request, String type) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		if (rmap.containsKey("info"))
			return rmap;
		char a = 6;
		String tempRootPath = Constants.TEMP_FILE_ROOT + Constants.FILE_SEPARATOR + System.currentTimeMillis() + Constants.FILE_SEPARATOR;
		new File(tempRootPath).mkdirs();
		List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		for (FileItem item : items) {
			if (item.isFormField()) {
				String value = new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
				if (!rmap.containsKey(item.getFieldName())) {
					rmap.put(item.getFieldName(), value);
				} else {
					rmap.put(item.getFieldName(), rmap.get(item.getFieldName()) + a + value);
				}
			} else {
				if (StringUtils.isEmpty(item.getName()))
					continue;
				String filePath = "";
				if (type.equals(Constants.FUN_APP)) {
					filePath += tempRootPath;
					if (item.getFieldName().toLowerCase().contains(Constants.FILE_TYPE_PIC)) {
						filePath += Constants.FILE_SEPARATOR + Constants.FILE_TYPE_PIC;
					} else if (item.getFieldName().equals(Constants.FILE_TYPE_APP)) {
						filePath += Constants.FILE_SEPARATOR + Constants.FILE_TYPE_APP;
						rmap.put("appSize", item.getSize() + "");
					} else if (item.getFieldName().contains(Constants.FILE_TYPE_APP_BATCH)) {
						filePath += Constants.FILE_SEPARATOR + Constants.FILE_TYPE_APP_BATCH;
						String index = StringUtils.substringAfterLast(item.getFieldName(), "_");
						rmap.put("appBatchSize_" + index, item.getSize() + "");
					} else if (item.getFieldName().equals(Constants.FILE_TYPE_EMBEDED_APP)) {
						filePath += Constants.FILE_SEPARATOR + Constants.FILE_TYPE_EMBEDED_APP;
						rmap.put("embededAppSize", item.getSize() + "");
					} else if (item.getFieldName().contains(Constants.FILE_TYPE_ICON)) {
						filePath += Constants.FILE_SEPARATOR + Constants.FILE_TYPE_ICON;
					} else {
						continue;
					}
				} else if (type.equals(Constants.FUN_PLATFROM)) {
					filePath += tempRootPath;
					filePath += Constants.FILE_SEPARATOR + Constants.FILE_TYPE_APP;
					rmap.put("size", item.getSize() + "");
				} else if (type.equals(Constants.FUN_CATEGORY)) {
					filePath += tempRootPath;
					filePath += Constants.FILE_SEPARATOR + Constants.FILE_TYPE_ICON;
				} else if (type.equals(Constants.FUN_RANK_CATEGORY)) {
					filePath += tempRootPath;
					filePath += Constants.FILE_SEPARATOR + Constants.FILE_TYPE_ICON;
				} else if (type.equals(Constants.FUN_SUBJECT)) {
					filePath += tempRootPath;
					filePath += Constants.FILE_SEPARATOR + Constants.FILE_TYPE_ICON;
				} else if (type.equals(Constants.FUN_RANK)) {
					filePath += tempRootPath;
					filePath += Constants.FILE_SEPARATOR + Constants.FILE_TYPE_ICON;
				} else if (type.equals(Constants.FUN_ARTICLE)) {
					filePath += tempRootPath;
					filePath += Constants.FILE_SEPARATOR + Constants.FILE_TYPE_ICON;
				} else if (type.equals(Constants.FUN_ACTIVITY)) {
					filePath += tempRootPath;
					filePath += Constants.FILE_SEPARATOR + Constants.FILE_TYPE_ICON;
				} else if (type.equals(Constants.FUN_THEME)) {
					filePath += tempRootPath;
					filePath += Constants.FILE_SEPARATOR + Constants.FILE_TYPE_PIC;
				}
				File dir = new File(filePath);
				if (!dir.exists()) {
					if (!dir.mkdirs()) {
						System.err.println("create file fail....");
					}
				}
				// 用当前时间毫秒来作为文件名，防止文件名重复
				String fileName = System.nanoTime() + item.getFieldName() + getFileSuffix(item.getName());
				filePath = filePath + Constants.FILE_SEPARATOR + fileName;
				rmap.put(item.getFieldName(), fileName);
				System.out.println(filePath);
				File file = new File(filePath);
				item.write(file);
			}
		}
		rmap.put("tempDirPath", tempRootPath);
		return rmap;
	}

	public static void download(HttpServletRequest request, HttpServletResponse response) {
		String fileName = request.getParameter("fileName");
		String filePath = Constants.FILE_ROOT + Constants.FILE_SEPARATOR + fileName;
		File file = new File(filePath);
		if (!file.exists()) {
			System.err.println("file download file:" + filePath + "   not exists.......");
			return;
		}
		try {
			InputStream in = new FileInputStream(file);
			fileName = StringUtils.substringAfterLast(fileName, "/");
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
			response.reset();
			byte[] b = new byte[1024];
			int len;
			while ((len = in.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
			response.getOutputStream().flush();
			in.close();
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("资源路径不存在：" + request.getQueryString());
		}
	}

	/**
	 * 文件后缀名
	 * 
	 * @param filename
	 * @return
	 */
	private static String getFileSuffix(String filename) {
		return filename.substring(filename.lastIndexOf("."), filename.length()).toLowerCase();
	}

}
