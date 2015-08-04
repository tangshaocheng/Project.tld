package com.energysh.egame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpUtil {

	private final static Log log = LogFactory.getLog(FtpUtil.class);
	private String hostname, username, password;
	private int port = 21;
	private FTPClient ftp = null;

	public FtpUtil(String hostname, String username, String password, int port) {
		this.hostname = hostname;
		this.username = username;
		this.password = password;
		this.port = port;
		ftp = new FTPClient();
		login();
	}

	public FtpUtil(String hostname, String username, String password) {
		this.hostname = hostname;
		this.username = username;
		this.password = password;
		ftp = new FTPClient();
		login();
	}

	private void login() {
		try {
			ftp.connect(this.hostname, this.port);
			if (!ftp.login(username, password)) {
				ftp.logout();
			}
		} catch (Exception e) {
			log.error("ftp login error: ", e);
		}
	}

	public FTPClient getFTPClient() {
		return ftp;
	}

	/**
	 * 改变远程工作
	 * 
	 * @param pathname
	 * @return
	 */
	public boolean changeRemotDir(String pathname) {
		try {
			return ftp.changeWorkingDirectory(pathname);

		} catch (Exception e) {
			return false;
		}
	}

	public FTPFile[] getRomoteFiles() throws IOException {
		return this.ftp.listFiles();
	}

	public boolean delRomoteFile(FTPFile file) throws IOException {
		return this.ftp.deleteFile(file.getName());
	}

	public void delRomoteFiles(List<FTPFile> files) throws IOException {
		for (FTPFile file : files) {
			this.ftp.deleteFile(file.getName());
		}
	}

	/**
	 * 关闭连接
	 */
	public void close() {
		try {
			ftp.disconnect();
		} catch (IOException e) {
			log.error("close ftp error: ", e);
		}
	}

	/**
	 * 
	 * @param remoteFileName
	 *            远程文件名称，空则为本地文件名
	 * @param localFilePath
	 *            本地被上传的文件名
	 * @return uploadError 上传失败 ok上传成功
	 * @throws FileNotFoundException
	 */
	public String uploadFile(String remoteFileName, String localFilePath) {
		try {
			File file = new File(localFilePath);
			MyUtil mu = MyUtil.getInstance();
			// Date date = new Date();
			// String da = cUtil.formateDate(date, "yyyyMMddHHmmss");
			// String flast="."+da+".mavkftpfile";
			InputStream input = new FileInputStream(file);
			if (mu.isBlank(remoteFileName)) {
				remoteFileName = file.getName();// +flast;
			}
			ftp.storeFile(remoteFileName, input);
			input.close();
		} catch (Exception e) {
			log.error("upload file error: ", e);
			return "uploadError";
		}
		return "ok";
	}

	/**
	 * 
	 * @param remoteFileName
	 *            远程文件名称，空则为本地文件名
	 * @param localFilePath
	 *            本地被上传的文件名
	 * @return uploadError 上传失败 ok上传成功
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String uploadBossFile(String remoteFileName, String localFilePath) throws IOException {
		this.ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		File file = new File(localFilePath);
		MyUtil mu = MyUtil.getInstance();
		InputStream input = new FileInputStream(file);
		if (mu.isBlank(remoteFileName)) {
			remoteFileName = file.getName();
		}
		ftp.storeFile(remoteFileName, input);
		input.close();
		return "ok";
	}

	/**
	 * 
	 * @return
	 */
	public String downloadFile(String remoteFileName, String localFilePath) {
		try {
			FileOutputStream fos = new FileOutputStream(localFilePath);
			ftp.setBufferSize(1024);
			// 设置文件类型（二进制）
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.retrieveFile(remoteFileName, fos);
			fos.close();
		} catch (Exception e) {
			log.error("download file error: ", e);
			return "downloadError";
		}
		return "ok";
	}

	/**
	 * 下载当前目录下指定日期文件
	 * 
	 * @return
	 */
	public List<String> downloadAllFile(String doFtpDate) {
		List<String> inList = new ArrayList<String>();
		MyUtil mu = MyUtil.getInstance();
		String[] doFtpDateArray = doFtpDate.split(",");
		try {
			// 设置文件类型（二进制）
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			FTPFile[] ffs = ftp.listFiles();
			for (FTPFile ff : ffs) {
				if (ff.getSize() == 0)
					continue;
				boolean isNeedHandleFile = false;
				for (String dfd : doFtpDateArray) {
					if (ff.getName().contains(dfd)) {
						isNeedHandleFile = true;
						break;
					}
				}
				if (!isNeedHandleFile)
					continue;
				FileOutputStream fos = new FileOutputStream(mu.getRealPath() + "/downloadFile/" + ff.getName());
				ftp.retrieveFile(ff.getName(), fos);
				fos.close();
				inList.add(mu.getRealPath() + "/downloadFile/" + ff.getName());
			}
		} catch (Exception e) {

		}
		return inList;
	}

	// public static void main(String[] args) throws IOException {
	// FtpUtil fu = new FtpUtil("192.168.1.247","tuhua","tuhua",21);
	// List<String> inList=fu.downloadAllFile();
	// String line;
	// for(String in:inList){
	// BufferedReader read = new BufferedReader(new
	// InputStreamReader(new FileInputStream(in)));
	// while(null!=(line=read.readLine())){
	// System.out.println(line);
	// }
	// read.close();
	// }
	// }
}
