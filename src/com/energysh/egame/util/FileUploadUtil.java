package com.energysh.egame.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 文件上传
 * 
 * @author LHJ
 * 
 */
public class FileUploadUtil {

	private final static FileItemFactory factory = new DiskFileItemFactory();
	private final static ServletFileUpload upload = new ServletFileUpload(factory);
	HttpServletRequest request = null;
	List<FileItem> items = null;
	private String filePath = MyConfigurer.getEvn("fileuploadpath");
	private String filename;
	private String fileType;
	private Long filebyte = 1024 * 1024 * 100L;

	public FileUploadUtil(HttpServletRequest request) throws Exception {
		this.setRequest(request);
		this.setFilePath(MyConfigurer.getEvn("fileuploadpath"));
	}

	@SuppressWarnings("unchecked")
	public void setRequest(HttpServletRequest request) throws FileUploadException {
		this.request = request;
		items = upload.parseRequest(request);
	}

	public Map<String, String> getPara() throws IOException {
		if (null == request)
			throw new IOException("please init request");
		Map<String, String> para = new HashMap<String, String>();
		char a = 6;
		String value = "";
		for (FileItem item : items) {
			if (!item.isFormField())
				continue;
			value = new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
			// value = cu.htmEncode(value);// 特殊字符转换
			if (!para.containsKey(item.getFieldName())) {
				para.put(item.getFieldName(), value);
			} else {
				para.put(item.getFieldName(), para.get(item.getFieldName()) + a + value);
			}
		}
		return para;
	}

	public void setFilePath(String filePath) throws IOException {
		if (null == request)
			throw new IOException("please init request");
		File file = new File(this.request.getSession().getServletContext().getRealPath("/") + filePath);
		int i = 0;
		while (!file.exists() && i < 5) {
			file.mkdir();
			i++;
		}
		this.filePath = file.getPath();
	}

	public void setFileName(String filename) {
		this.filename = filename;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setMaxSize(long filebyte) {
		this.filebyte = filebyte;
	}

	public Map<String, String> upload(String fieldname) throws Exception {
		if (null == request)
			throw new IOException("please init request");
		MyUtil util = MyUtil.getInstance();
		Map<String, String> rmap = new HashMap<String, String>();
		for (FileItem item : items) {
			if (item.isFormField() || !util.equals(item.getFieldName(), fieldname))
				continue;
			if (util.isNotBlank(item.getName())) {
				String fileSuffix = getFileSuffix(item.getName());
				if (!util.isBlank(this.fileType) && this.fileType.indexOf(fileSuffix.substring(1, fileSuffix.length())) == -1) {
					rmap.put("info", "errorFile");
					return rmap;
				}
				if (null != this.filebyte && item.getSize() > this.filebyte) {
					rmap.put("info", "bigSize");
					return rmap;
				}
				File file = new File(filePath + File.separatorChar + filename + fileSuffix);
				item.write(file);
				rmap.put(fieldname, MyConfigurer.getEvn("fileuploadpath") + file.getName());
			}
		}
		rmap.putAll(this.getPara());
		return rmap;
	}

	/**
	 * 上传文件，并返回相对路径
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> upload() throws Exception {
		if (null == request)
			throw new IOException("please init request");
		Map<String, String> rmap = valibale();
		if (rmap.containsKey("info"))
			return rmap;
		MyUtil util = MyUtil.getInstance();
		for (FileItem item : items) {
			if (item.isFormField())
				continue;
			if (!util.isBlank(item.getName())) {
				String fn = filePath + File.separatorChar + MyUtil.getInstance().getRandom(10) + getFileSuffix(item.getName());
				File file = new File(fn);
				item.write(file);
				rmap.put(item.getFieldName(), MyConfigurer.getEvn("fileuploadpath") + file.getName());
			}
		}
		rmap.putAll(this.getPara());
		return rmap;
	}

	/**
	 * 上传文件，并返回绝对路径
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> uploadWithAbsolutePath() throws Exception {
		if (null == request)
			throw new IOException("please init request");
		Map<String, String> rmap = valibale();
		if (rmap.containsKey("info"))
			return rmap;
		MyUtil util = MyUtil.getInstance();
		for (FileItem item : items) {
			if (item.isFormField())
				continue;
			if (!util.isBlank(item.getName())) {
				String fn = filePath + File.separatorChar + item.getName().substring(item.getName().lastIndexOf("\\") + 1, item.getName().length());
				rmap.put("format", fn.substring(fn.indexOf(".") + 1));
				File file = new File(fn);
				item.write(file);
				rmap.put(item.getFieldName() + "Size", Long.toString(item.getSize()));
				rmap.put(item.getFieldName(), file.getAbsolutePath());
			}
		}
		rmap.putAll(this.getPara());
		return rmap;
	}

	/**
	 * 文件后缀�? * @param filename
	 * 
	 * @return
	 */
	private String getFileSuffix(String filename) {
		return filename.substring(filename.lastIndexOf("."), filename.length()).toLowerCase();
	}

	private Map<String, String> valibale() {
		Map<String, String> rmap = new HashMap<String, String>();
		MyUtil util = MyUtil.getInstance();
		for (FileItem item : items) {// 文件判断
			if (item.isFormField())
				continue;
			if (!util.isBlank(item.getName())) {
				String fileSuffix = getFileSuffix(item.getName());
				if (!util.isBlank(this.fileType) && this.fileType.indexOf(fileSuffix.substring(1, fileSuffix.length())) == -1) {
					rmap.put("info", "errorFile");
					return rmap;
				}
				if (null != this.filebyte && item.getSize() > this.filebyte) {
					rmap.put("info", "bigSize");
					return rmap;
				}
			}
		}
		return rmap;
	}
}
