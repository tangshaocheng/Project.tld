package com.energysh.egame.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

public class MyUtil {

	private final static Log log = LogFactory.getLog(MyUtil.class);
	private static transient int gregorianCutoverYear = 1582;
	/** 闰年中每月天数 */
	private static final int[] DAYS_P_MONTH_LY = { 31, 29, 31, 30, 31, 30, 31,
			31, 30, 31, 30, 31 };
	/** 非闰年中每月天数 */
	private static final int[] DAYS_P_MONTH_CY = { 31, 28, 31, 30, 31, 30, 31,
			31, 30, 31, 30, 31 };
	/** 代表数组里的年、月、日 */
	private static final int Y = 0, M = 1, D = 2;
	private static MyUtil huu = new MyUtil();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf_yyyyMMddHHmmss = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	private final static String[] parsePatterns = new String[] {
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy/MM/dd HH:mm:ss",
			"yyyy/MM/dd", "yyyyMMddHHmmss", "yyyyMMdd" };

	public static MyUtil getInstance() {
		return huu;
	}

	private MyUtil() {
	}

	public String getRandom(int length) {
		String seed = "abcd2wefg3da45678hijklmnopqrstuvwxyzABcdhnCDsxqaEFGHIJKLMNOPQRSTUVWXYZ"
				+ System.currentTimeMillis();
		String orderNo = "";
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			int randnumber = random.nextInt(seed.length());
			String strRand = seed.substring(randnumber, randnumber + 1);
			orderNo += strRand;
		}
		return orderNo;
	}

	/**
	 * 补零
	 * 
	 * @param n
	 * @param length
	 * @return
	 */
	public String addZore(Integer n, int length) {
		StringBuffer t = new StringBuffer("");
		for (int i = 0; i < length - n.toString().length(); i++) {
			t.append("0");
		}
		return t.toString() + n.toString();
	}

	public Object invokeMethod(Object obj, String methodName, Object[] args)
			throws Exception {
		Reflection rf = Reflection.getInstance();
		return rf.invokeMethod(obj, methodName, args);
	}

	public Object invokeMethodValuNull(Object owner, String methodName,
			Class arg) throws Exception {
		Reflection rf = Reflection.getInstance();
		return rf.invokeMethodValuNull(owner, methodName, arg);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public boolean isBlank(String str) {
		return StringUtils.isBlank(str) || "null".equalsIgnoreCase(str);
	}

	/**
	 * 判断字符串是否不为空
	 * 
	 * @param str
	 * @return
	 */
	public boolean isNotBlank(String str) {
		return StringUtils.isNotBlank(str) && (!"null".equalsIgnoreCase(str));
	}

	/**
	 * 截取字符串-开始位置和结束位置
	 * 
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public String substring(String str, int start, int end) {
		return StringUtils.substring(str, start, end);
	}

	/**
	 * 判断两个字符串是否相等
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public boolean equals(String str1, String str2) {
		return StringUtils.equals(str1, str2);
	}

	/**
	 * 判断两个字符串是否相等(忽略大小写)
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public boolean equalsIgnoreCase(String str1, String str2) {
		return StringUtils.equalsIgnoreCase(str1, str2);
	}

	/**
	 * 判断字符串是否包含某字符串(忽略大小写)
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public boolean containsIgnoreCase(String arg0, String arg1) {
		return StringUtils.containsIgnoreCase(arg0, arg1);
	}

	/**
	 * 字符串去除空格
	 * 
	 * @param str
	 * @return
	 */
	public String trim(String str) {
		return StringUtils.trim(str);
	}

	/**
	 * 字符串替换
	 * 
	 * @param text
	 * @param searchString
	 * @param replacement
	 * @return
	 */
	public String replace(String text, String searchString, String replacement) {
		return StringUtils.replace(text, searchString, replacement);
	}

	/**
	 * 字符串转换为Integer
	 * 
	 * @param str
	 * @return
	 */
	public Integer toInt(String str) {
		return Integer.valueOf(NumberUtils.toInt(str));
	}

	/**
	 * 字符串转换为Double
	 * 
	 * @param str
	 * @return
	 */
	public Double toDouble(String str) {
		return Double.valueOf(NumberUtils.toDouble(str));
	}

	/**
	 * 字符串转换为Long
	 * 
	 * @param str
	 * @return
	 */
	public Long toLong(String str) {
		return Long.valueOf(NumberUtils.toLong(str));
	}

	/**
	 * 字符串转换为Float
	 * 
	 * @param str
	 * @return
	 */
	public Float toFloat(String str) {
		return Float.valueOf(NumberUtils.toFloat(str));
	}

	/**
	 * 字符串转换为Byte
	 * 
	 * @param str
	 * @return
	 */
	public Byte toByte(String str) {
		return Byte.valueOf(NumberUtils.toByte(str));
	}

	/**
	 * 字符串转换为Short
	 * 
	 * @param str
	 * @return
	 */
	public Short toShort(String str) {
		return Short.valueOf(NumberUtils.toShort(str));
	}

	/**
	 * 判断字符串是否包含子字符串
	 * 
	 * @param str
	 * @param searchChar
	 * @return
	 */
	public boolean contains(String str, String searchChar) {
		return StringUtils.contains(str, searchChar);
	}

	/**
	 * 分割字符
	 * 
	 * @param str
	 * @return
	 */
	private final static char splitChar = 6;

	public String[] splitStr(String str) {
		return str.split("" + splitChar);
	}

	/**
	 * 分割字符
	 * 
	 * @param str
	 * @param separatorChars
	 * @return
	 */
	public String[] split(String str, String separatorChars) {
		return StringUtils.split(str, separatorChars);
	}

	/**
	 * 分割字符
	 * 
	 * @param str
	 * @param separatorChars
	 * @return
	 */
	public String[] split(String str, char separatorChars) {
		return StringUtils.split(str, separatorChars);
	}

	public Date getDate(String date) {
		if (StringUtils.length(date) >= 19) {
			if (StringUtils.length(date) > 19)
				date = StringUtils.substring(date, 0, 19);
			String[] datetimes = StringUtils.split(date, " ");
			String[] dates = StringUtils.split(datetimes[0], "-");
			String[] times = StringUtils.split(datetimes[1], ":");
			GregorianCalendar gc = (GregorianCalendar) GregorianCalendar
					.getInstance();
			gc.set(new Integer(dates[0]).intValue(),
					new Integer(dates[1]).intValue() - 1,
					new Integer(dates[2]).intValue(),
					new Integer(times[0]).intValue(),
					new Integer(times[1]).intValue(),
					new Integer(times[2]).intValue());
			return gc.getTime();
		} else {
			String[] dates = StringUtils.split(date, "-");
			GregorianCalendar gc = (GregorianCalendar) GregorianCalendar
					.getInstance();
			gc.set(new Integer(dates[0]).intValue(),
					new Integer(dates[1]).intValue() - 1,
					new Integer(dates[2]).intValue());
			return gc.getTime();
		}
	}

	public Date getAllDatePart(String date) throws Exception {
		return sdf.parse(date);
	}

	public String getDateyyyMMdd(String date) {
		return formateDate(getDate(date), "yyyy-MM-dd");
	}

	public String getDateyyyMMdd(Date date) {
		return formateDate(date, "yyyy-MM-dd");
	}

	public Date TimestampToDate(Timestamp tt) {
		GregorianCalendar gc = (GregorianCalendar) GregorianCalendar
				.getInstance();
		gc.setTimeInMillis(tt.getTime());
		return gc.getTime();
	}

	public String formateDate(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	public String formateDate(String date, String pattern) {
		try {
			return DateFormatUtils.format(DateUtils.parseDate(
					StringUtils.substringBefore(date, "."), parsePatterns),
					pattern);
		} catch (Exception e) {
			log.error("formate date error: ", e);
		}
		return null;
	}

	public String formateDate(String date) {
		try {
			return sdf.format(sdf_yyyyMMddHHmmss.parse(date));
		} catch (Exception e) {
			log.error("formate date error: ", e);
		}
		return sdf.format(new Date());
	}

	public String addTime(Date date, int longtime, int unit) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(unit, longtime);
		return sdf.format(c.getTime());
	}

	/**
	 * 功能：比较时间大小
	 * 
	 * @author xinwang.xu
	 * @date 2013-4-3 下午02:54:23
	 * @param date1
	 * @param date2
	 * @return
	 */
	public boolean timeCompareTo(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		int result = c1.compareTo(c2);
		if (result > 0)
			return true;
		return false;
	}

	public void map2Object(Map<?, ?> para, Object obj) throws Exception {
		Field[] selfFileds = obj.getClass().getDeclaredFields();
		Field[] superFileds = obj.getClass().getSuperclass()
				.getDeclaredFields();
		Field[] fs = new Field[selfFileds.length + superFileds.length];
		int index = 0;
		for (Field f : selfFileds) {
			fs[index] = f;
			index++;
		}
		for (Field f : superFileds) {
			fs[index] = f;
			index++;
		}
		String ftype = "";
		Object value;
		for (Field f : fs) {
			String field = getFiled(f.getName());
			if (!para.containsKey(field))
				continue;
			ftype = f.getType().toString().toLowerCase();
			value = para.get(field);
			if (ftype.indexOf("string") != -1) {
				if (value == null || this.isBlank(value.toString())) {
					invokeMethod(obj, getSeterr(f.getName()),
							new Object[] { "" });
				}
				invokeMethod(obj, getSeterr(f.getName()),
						new Object[] { value == null ? "" : value });
				continue;
			}
			if (ftype.indexOf("long") != -1) {
				if (value == null || this.isBlank(value.toString())) {
					invokeMethodValuNull(obj, getSeterr(f.getName()),
							Long.class);
				}
				if (!isNumber(value.toString()))
					continue;
				value = NumberUtils.toLong(value.toString());
				invokeMethod(obj, getSeterr(f.getName()),
						new Object[] { value });
				continue;
			}
			if (ftype.indexOf("date") != -1) {
				if (value == null || this.isBlank(value.toString())) {
					invokeMethodValuNull(obj, getSeterr(f.getName()),
							Date.class);
				}
				value = getDate(value.toString());
				// value = getAllDatePart(value.toString());
				invokeMethod(obj, getSeterr(f.getName()),
						new Object[] { value });
				continue;
			}
			if (ftype.indexOf("integer") != -1) {
				if (value == null || this.isBlank(value.toString())) {
					invokeMethodValuNull(obj, getSeterr(f.getName()),
							Integer.class);
				}
				if (!isNumber(value == null ? null : value.toString()))
					continue;
				value = NumberUtils.toInt(value.toString());
				invokeMethod(obj, getSeterr(f.getName()),
						new Object[] { value });
				continue;
			}
			if (ftype.indexOf("double") != -1) {
				if (value == null || this.isBlank(value.toString())) {
					invokeMethodValuNull(obj, getSeterr(f.getName()),
							Double.class);
				}
				if (!isNumber(value == null ? null : value.toString()))
					continue;
				value = NumberUtils.toDouble(value.toString());
				invokeMethod(obj, getSeterr(f.getName()),
						new Object[] { value });
				continue;
			}
		}
	}

	private String getFiled(String f) {
		char chs[] = f.toCharArray();
		String result = "";
		for (int i = 0; i < chs.length; i++) {
			if (StringUtils.isAllUpperCase(String.valueOf(chs[i]))) {
				result += ("_" + String.valueOf(chs[i]).toLowerCase());
			} else {
				result += String.valueOf(chs[i]);
			}
		}
		return result;
	}

	private String getSeterr(String f) {
		return "set" + f.substring(0, 1).toUpperCase()
				+ f.substring(1, f.length());
	}

	public boolean isAllLetter(String inString) {
		Pattern p = Pattern.compile("[a-zA-Z]+");
		Matcher m = p.matcher(inString);
		return m.matches();
	}

	public boolean isNumber(String number) {
		return NumberUtils.isNumber(number);
	}

	public String doMobile(String mobile) {
		if (StringUtils.isBlank(mobile))
			return null;
		mobile = StringUtils.replace(mobile, " ", "");
		if (mobile.startsWith("86"))
			mobile = mobile.substring(2, mobile.length());
		Pattern p = Pattern
				.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		if (m.matches())
			return mobile;
		return null;
	}

	public boolean isZip(String zip) {
		Pattern p = Pattern.compile("[0-9]{5,8}");
		Matcher m = p.matcher(zip);
		return m.matches();
	}

	public boolean isEmail(String email) {
		Pattern p = Pattern
				.compile("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public boolean isMemberName(String name) {
		Pattern p = Pattern.compile("[a-zA-Z0-9_]{3,15}");
		Matcher m = p.matcher(name);
		return m.matches();
	}

	public boolean isPs(String name) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]{6,20}");
		Matcher m = p.matcher(name);
		return m.matches();
	}

	public boolean isURL(String url) {
		Pattern p = Pattern.compile("(http[s]?|ftp):\\/\\/[^\\/\\.]+?\\..+\\w");
		Matcher m = p.matcher(url);
		return m.matches();
	}

	/**
	 * @return 得到工程当前目录
	 */
	public String getRealPath() {
		String path = this.getClass().getResource("").getPath();
		path = path.substring(1, path.indexOf("WEB-INF/"));
		if (this.equals(File.separator, "/"))
			return "/" + path;
		return path;
	}

	/**
	 * 把文本编码为Html代码
	 * 
	 * @param target
	 * @return 编码后的字符
	 */
	public String htmEncode(String target) {
		StringBuffer stringbuffer = new StringBuffer();
		int j = target.length();
		for (int i = 0; i < j; i++) {
			char c = target.charAt(i);
			switch (c) {
			case 60:
				stringbuffer.append("&lt;");
				break;
			case 62:
				stringbuffer.append("&gt;");
				break;
			case 38:
				stringbuffer.append("&amp;");// &
				break;
			case 34:
				stringbuffer.append("&quot;");
				break;
			case 169:
				stringbuffer.append("&copy;");
				break;
			case 174:
				stringbuffer.append("&reg;");
				break;
			case 165:
				stringbuffer.append("&yen;");
				break;
			case 8364:
				stringbuffer.append("&euro;");
				break;
			case 8482:
				stringbuffer.append("&#153;");
				break;
			case 13:
				if (i < j - 1 && target.charAt(i + 1) == 10) {
					stringbuffer.append("<br>");
					i++;
				}
				break;
			case 32:
				if (i < j - 1 && target.charAt(i + 1) == ' ') {
					stringbuffer.append(" &nbsp;");
					i++;
					break;
				}
			default:
				stringbuffer.append(c);
				break;
			}
		}
		return new String(stringbuffer.toString());
	}

	/**
	 * 把时间转为标准形�? *
	 * 
	 * @param dt
	 * @return 转换后的形式
	 */
	public static String toString(Date dt, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String str = sdf.format(dt);
		return str;
	}

	public static Date toDate(String dt) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = df.parse(dt);
		} catch (ParseException e) {
			log.error("to date error: ", e);
		}
		return d;
	}

	public void copyFile(File from, File to) throws Exception {
		FileChannel inC = new FileInputStream(from).getChannel();
		FileChannel outC = new FileOutputStream(to).getChannel();
		ByteBuffer b = null;
		int length = 1024 * 1024 * 2;
		while (true) {
			if (inC.position() == inC.size()) {
				inC.close();
				outC.close();
				return;
			}
			if ((inC.size() - inC.position()) < length) {
				length = (int) (inC.size() - inC.position());
			} else
				length = 2097152;
			b = ByteBuffer.allocateDirect(length);
			inC.read(b);
			b.flip();
			outC.write(b);
			outC.force(false);
		}
	}

	// public String getHzPy(String hz) throws Exception {
	// if (isBlank(hz))
	// return "";
	// String hanZi = this.getHanzi(hz);
	// String pY = "";
	// if (isBlank(hanZi)) {
	// pY = hz;
	// } else {
	// for (int i = 0; i < hz.length(); i++)
	// if (getHanziCode(hz.substring(i, i + 1)) == -1)
	// pY = new StringBuilder(pY).append(hz.substring(i, i + 1)).toString();
	// else
	// pY = new StringBuilder(pY).append(PYString.getHzSm(hz.substring(i, i +
	// 1))).toString();
	// }
	// return pY;
	// }

	public int getHanziCode(String strHanzi) throws Exception {
		byte bytes[] = strHanzi.getBytes("gbk");
		if (bytes == null || bytes.length > 2 || bytes.length <= 0
				|| bytes.length == 1)
			return -1;
		if (bytes.length == 2) {
			int hByte;
			if (bytes[0] < 0)
				hByte = 256 + bytes[0];
			else
				hByte = bytes[0];
			int lByte;
			if (bytes[1] < 0)
				lByte = 256 + bytes[1];
			else
				lByte = bytes[1];
			int iHanzi = (256 * hByte + lByte) - 0x10000;
			return iHanzi;
		} else {
			return -1;
		}
	}

	public String getHanzi(String inString) throws Exception {
		String hzs[] = new String[inString.length()];
		int hzsIndex = 0;
		for (int i = 0; i < inString.length(); i++)
			if (getHanziCode(inString.substring(i, i + 1)) != -1) {
				hzs[hzsIndex] = inString.substring(i, i + 1);
				hzsIndex++;
			}
		String rhz = "";
		for (int i = 0; i < hzs.length; i++)
			if (!isBlank(hzs[i]))
				rhz = new StringBuilder(rhz).append(hzs[i]).toString();
		return rhz;
	}

	public Date getCurrenTime() {
		return Calendar.getInstance().getTime();
	}

	private static final ObjectMapper mapper = new ObjectMapper();

	public String jackSon(Object object) {
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, object);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			return null;
		}
		return writer.toString();
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @author xinwang.xu
	 * @return
	 */
	public Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @author xinwang.xu
	 * @return
	 */
	public Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 功能：得到日期的星期 1：星期一 ...7:星期�?
	 * 
	 * @author xinwang.xu
	 * @date 2011-6-13 下午04:01:13
	 * @param d
	 * @return
	 */
	public Integer getDayOfWeek(Date d) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		int myWeek = now.get(Calendar.DAY_OF_WEEK) - 1;// �?為生活常用的星期
		if (myWeek == 0)// 如果�?星期天改�?
			myWeek = 7;
		return myWeek;
	}

	/**
	 * 功能：返回月份中的某�?��
	 * 
	 * @author xinwang.xu
	 * @date 2011-6-13 下午04:15:21
	 * @param d
	 * @return
	 */
	public Integer getDayOfMonth(Date d) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		return now.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 * 
	 * @author yali.guo
	 * @version 1.0
	 *          <p>
	 *          <b>功能说明�?/b>删除文件夹下�?��的文件和目录
	 *          </p>
	 *          <p>
	 *          <b>创建时间�?/b>2011-7-12
	 *          </p>
	 *          <p>
	 *          <b>修改时间�?/b>2011-7-12
	 *          </p>
	 */
	public void delAllFile(String path) {
		File file = new File(path);
		String[] files = file.list();
		for (String f : files) {
			File temp = new File(path + File.separator + f);
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + File.separator + f);
				temp.delete();
			}
		}
	}

	public Integer doubleToInt(Double d) {
		if (null == d)
			return null;
		if (d.toString().lastIndexOf(".") > -1)
			return NumberUtils.toInt(d.toString().substring(0,
					d.toString().lastIndexOf(".")));
		else
			return NumberUtils.toInt(d.toString());
	}

	/**
	 * 请求参数信息打包成MAP对象
	 * 
	 * @param reqParaMap
	 * @return
	 */
	public Map<String, String> getReqparaMap(Map<String, String> reqParaMap) {
		Map<String, String> rmap = new HashMap<String, String>();
		for (String key : reqParaMap.keySet()) {
			rmap.put("$" + key, reqParaMap.get(key));
		}
		return rmap;
	}

	/**
	 * 根据手机号码取得前三位
	 * 
	 * @param mobile
	 * @return
	 */
	public static String getMobileToStr3(String mobile) {
		String mobileStr3 = "0";
		if (MyUtil.getInstance().isNotBlank(mobile)) {
			mobile = mobile.trim();
			if (mobile.length() >= 3) {
				if (StringUtils.isNumeric(mobile)) {
					mobileStr3 = mobile.substring(0, 3);
				}
			}
		}
		return mobileStr3;
	}

	public static String convString(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	/**
	 * 解码
	 * 
	 * @param srcStr
	 * @param tagDecodingWay
	 * @return
	 */
	public static String deCode(String srcStr, String tagDecodingWay) {
		try {
			if (!huu.isBlank(srcStr)) {
				if (MyUtil.getInstance()
						.equals(srcStr, "6DDS%3F%3FSHOU%152%3F"))
					return "6DDS${SOHU_2}";
				if (!huu.isBlank(tagDecodingWay)) {
					String temp = URLDecoder.decode(srcStr, tagDecodingWay);
					if (temp.length() < 100)
						return temp;
				}
				log.error("deCode src ---- " + srcStr);
				String keys = new String(srcStr.getBytes("iso-8859-1"));
				log.error("deCode dis ---- " + keys);
				if (keys.length() > 100)
					return keys.substring(0, 99);
				return srcStr;
			}
		} catch (Exception e) {
			log.error("de date error: ", e);
		}
		return "";
	}

	/**
	 * 将代表日期的字符串分割为代表年月日的整形数组
	 * 
	 * @param date
	 * @return
	 */
	public static int[] splitYMD(String date) {
		date = date.replace("-", "");
		int[] ymd = { 0, 0, 0 };
		ymd[Y] = Integer.parseInt(date.substring(0, 4));
		ymd[M] = Integer.parseInt(date.substring(4, 6));
		ymd[D] = Integer.parseInt(date.substring(6, 8));
		return ymd;
	}

	/**
	 * 检查传入的参数代表的年份是否为闰年
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return year >= gregorianCutoverYear ? ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0)))
				: (year % 4 == 0);
	}

	/**
	 * 日期加1天
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private static int[] addOneDay(int year, int month, int day) {
		if (isLeapYear(year)) {
			day++;
			if (day > DAYS_P_MONTH_LY[month - 1]) {
				month++;
				if (month > 12) {
					year++;
					month = 1;
				}
				day = 1;
			}
		} else {
			day++;
			if (day > DAYS_P_MONTH_CY[month - 1]) {
				month++;
				if (month > 12) {
					year++;
					month = 1;
				}
				day = 1;
			}
		}
		int[] ymd = { year, month, day };
		return ymd;
	}

	/**
	 * 将不足两位的月份或日期补足为两位
	 * 
	 * @param decimal
	 * @return
	 */
	public static String formatMonthDay(int decimal) {
		DecimalFormat df = new DecimalFormat("00");
		return df.format(decimal);
	}

	/**
	 * 将不足四位的年份补足为四位
	 * 
	 * @param decimal
	 * @return
	 */
	public static String formatYear(int decimal) {
		DecimalFormat df = new DecimalFormat("0000");
		return df.format(decimal);
	}

	/**
	 * 计算两个日期之间相隔的天数
	 * 
	 * @param begin
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public static long countDay(String begin, String end) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate, endDate;
		long day = 0;
		try {
			beginDate = format.parse(begin);
			endDate = format.parse(end);
			day = (endDate.getTime() - beginDate.getTime())
					/ (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 以循环的方式计算日期
	 * 
	 * @param beginDate
	 *            endDate
	 * @param days
	 * @return
	 */
	public static List<String> getEveryday(String beginDate, String endDate) {
		MyUtil mu = getInstance();
		beginDate = mu.formateDate(beginDate, "yyyy-MM-dd");
		endDate = mu.formateDate(endDate, "yyyy-MM-dd");
		long days = countDay(beginDate, endDate);
		int[] ymd = splitYMD(beginDate);
		List<String> everyDays = new ArrayList<String>();
		everyDays.add(beginDate);
		for (int i = 0; i < days; i++) {
			ymd = addOneDay(ymd[Y], ymd[M], ymd[D]);
			everyDays.add(formatYear(ymd[Y]) + "-" + formatMonthDay(ymd[M])
					+ "-" + formatMonthDay(ymd[D]));
		}
		return everyDays;
	}

	/**
	 * 功能：+-天数
	 * 
	 * @return
	 */
	public Date get_N_Date(int nDay) {
		return get_N_Date(Calendar.getInstance().getTime(), nDay);
	}

	public Date get_N_Date(Date date, int nDay) {
		Calendar cc = Calendar.getInstance();
		cc.setTime(date);
		cc.set(Calendar.DAY_OF_MONTH, cc.get(Calendar.DAY_OF_MONTH) + nDay);
		return cc.getTime();
	}

	public String fetchValueByLen(String value, String valueLen) {
		if (isBlank(value))
			return null;
		if (isBlank(valueLen))
			return value;
		String[] len = StringUtils.split(valueLen, ",");
		if (len.length != 2)
			return value;
		int startIdx = Integer.parseInt(len[0]) - 1;
		int endIdx = Integer.parseInt(len[1]);
		return StringUtils.substring(value, startIdx, endIdx);
	}

	/**
	 * 获取起始日期和截止日期之间的月份
	 * 
	 * @author chuansheng.lei
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public Set<String> getMonthsBetweenDays(String startDate, String endDate)
			throws ParseException {
		Set<String> months = new TreeSet<String>();
		int start = Integer.parseInt(StringUtils.replace(startDate, "-", ""));
		endDate = endDate.substring(0, 8) + 31;
		int end = Integer.parseInt(StringUtils.replace(endDate, "-", ""));
		while (start <= end) {
			String month = StringUtils.replace(startDate.substring(0, 7), "-",
					"");
			months.add(month);
			startDate = increaseOneMonth(startDate);
			start = Integer.parseInt(StringUtils.replace(startDate, "-", ""));
		}
		return months;

	}

	/**
	 * 日期的月份加1
	 * 
	 * @author chuansheng.lei
	 * @param originDate
	 * @return
	 * @throws ParseException
	 */
	private String increaseOneMonth(String originDate) throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(originDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

	/**
	 * 获取响应数据
	 * 
	 * @param inStream
	 * @return
	 */
	public String getResponse(InputStream inStream) {
		String result = "";
		ByteArrayOutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			int len = -1;
			byte[] buffer = new byte[1024];
			while ((len = inStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			byte[] data = outputStream.toByteArray();
			result = new String(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 生成唯一Id：时间戳 + 省份 + 随机int
	 * 
	 * @return
	 */
	public String generateId(String province) {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String nowTime = sdf.format(Calendar.getInstance().getTime());
		sb.append(nowTime);
		sb.append(toInt(province));
		int randomInt = RandomUtils.nextInt();
		sb.append(randomInt);
		return sb.toString();
	}

}
