package com.energysh.egame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class Utils {

	public static String getRandom(int length) {
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
	 * Map按首字母排序
	 * 
	 * @return
	 */
	public static String sortMap(Map<String, String> map) {
		Object[] signParamArray = map.keySet().toArray();
		Arrays.sort(signParamArray);
		StringBuilder contentBuffer = new StringBuilder();
		for (Object key : signParamArray) {
			String value = map.get(key);
			if (!"sign".equals(key)) {// sign不参与签名
				contentBuffer.append(key + "=" + value + "&");
			}
		}
		return StringUtils.removeEnd(contentBuffer.toString(), "&");
	}

	/**
	 * 格式化double
	 * 
	 * @param value
	 * @param scale
	 * @return
	 */
	public static Double round(Double value, int scale) {
		double result = 0.0;
		if (null != value) {
			result = new BigDecimal(String.valueOf(value)).setScale(scale,
					RoundingMode.HALF_UP).doubleValue();
		}
		return result;
	}

	/**
	 * 补零
	 * 
	 * @param n
	 * @param length
	 * @return
	 */
	public static String addZore(Integer n, int length) {
		StringBuffer t = new StringBuffer("");
		for (int i = 0; i < length - n.toString().length(); i++) {
			t.append("0");
		}
		return t.toString() + n.toString();
	}

	public static Object invokeMethod(Object obj, String methodName,
			Object[] args) throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Reflection rf = Reflection.getInstance();
		try {
			return rf.invokeMethod(obj, methodName, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object invokeMethodValuNull(Object owner, String methodName,
			Class arg) throws Exception {
		Reflection rf = Reflection.getInstance();
		return rf.invokeMethodValuNull(owner, methodName, arg);
	}

	/**
	 * 判断字符串是否为
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullEmpty(String str) {
		return org.apache.commons.lang.StringUtils.isEmpty(str);
	}

	/**
	 * 分割字符
	 * 
	 * @param str
	 * @return
	 */
	private final static char splitChar = 6;

	public static String[] splitStr(String str) {
		return str.split("" + splitChar);
	}

	public static Date getDate(String date) {
		String[] dates = StringUtils.split(date, "-");
		GregorianCalendar gc = (GregorianCalendar) GregorianCalendar
				.getInstance();
		gc.set(new Integer(dates[0]).intValue(),
				new Integer(dates[1]).intValue() - 1,
				new Integer(dates[2]).intValue());
		return gc.getTime();
	}

	public static Date getAllDatePart(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(date);
	}

	public static Date getDatePart(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public static String getDateyyyMMdd(String date) {
		return formateDate(getDate(date), "yyyy-MM-dd");
	}

	public static String getDateyyyMMdd(Date date) {
		return formateDate(date, "yyyy-MM-dd");
	}

	public static String getDateyyMMdd(Date date) {
		return formateDate(date, "yyyyMMdd");
	}

	public static Date TimestampToDate(java.sql.Timestamp tt) {
		GregorianCalendar gc = (GregorianCalendar) GregorianCalendar
				.getInstance();
		gc.setTimeInMillis(tt.getTime());
		return gc.getTime();
	}

	public static String formateDate(Date date, String pattern) {
		return org.apache.commons.lang.time.DateFormatUtils.format(date,
				pattern);
	}

	public static void map2Object(Map<String, String> para, Object obj)
			throws Exception {
		java.lang.reflect.Field[] fs = obj.getClass().getDeclaredFields();
		String ftype = "";
		Object value;
		for (java.lang.reflect.Field f : fs) {
			if (!para.containsKey(f.getName()))
				continue;
			// if (isNullEmpty(para.get(f.getName())))continue;
			ftype = f.getType().toString().toLowerCase();
			value = para.get(f.getName());
			if (ftype.indexOf("string") != -1) {
				invokeMethod(obj, getSeterr(f.getName()),
						new Object[] { value });
				continue;
			}
			if (ftype.indexOf("long") != -1) {
				if (!isNumber(value.toString()))
					continue;
				value = Long.valueOf(value.toString());
				invokeMethod(obj, getSeterr(f.getName()),
						new Object[] { value });
				continue;
			}
			if (ftype.indexOf("date") != -1) {
				value = getDate(value.toString());
				// value = getAllDatePart(value.toString());
				invokeMethod(obj, getSeterr(f.getName()),
						new Object[] { value });
				continue;
			}
			if (ftype.indexOf("integer") != -1) {
				if (!isNumber(value.toString()))
					continue;
				value = Integer.valueOf(value.toString());
				invokeMethod(obj, getSeterr(f.getName()),
						new Object[] { value });
				continue;
			}
			if (ftype.indexOf("double") != -1) {
				if (!isNumber(value.toString()))
					continue;
				value = Double.valueOf(value.toString());
				invokeMethod(obj, getSeterr(f.getName()),
						new Object[] { value });
				continue;
			}

		}
	}

	private static String getSeterr(String f) {
		return "set" + f.substring(0, 1).toUpperCase()
				+ f.substring(1, f.length());
	}

	public static boolean isAllLetter(String inString) {
		Pattern p = Pattern.compile("[a-zA-Z]+");
		Matcher m = p.matcher(inString);
		return m.matches();
	}

	public static boolean isNumber(String number) {
		return org.apache.commons.lang.math.NumberUtils.isNumber(number);
	}

	public static boolean isPhone(String phone) {
		Pattern p = Pattern
				.compile("^13[0-9]{1}[0-9]{8}$|15[0125689]{1}[0-9]{8}$|18[0-3,5-9]{1}[0-9]{8}$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	public static boolean isZip(String zip) {
		Pattern p = Pattern.compile("[0-9]{5,8}");
		Matcher m = p.matcher(zip);
		return m.matches();
	}

	public static boolean isEmail(String email) {
		Pattern p = Pattern
				.compile("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isMemberName(String name) {
		Pattern p = Pattern.compile("[a-zA-Z0-9_]{3,15}");
		Matcher m = p.matcher(name);
		return m.matches();
	}

	public static boolean isPs(String name) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]{6,20}");
		Matcher m = p.matcher(name);
		return m.matches();
	}

	public static boolean isURL(String url) {
		Pattern p = Pattern.compile("(http[s]?|ftp):\\/\\/[^\\/\\.]+?\\..+\\w");
		Matcher m = p.matcher(url);
		return m.matches();
	}

	/**
	 * @return 得到工程当前目录
	 */
	public static String getRealPath() {
		String path = Utils.class.getResource("").getPath();
		path = path.substring(1, path.indexOf("WEB-INF/"));
		return path;
	}

	/**
	 * 把文本编码为Html代码
	 * 
	 * @param target
	 * @return 编码后的字符
	 */
	public static String htmEncode(String target) {
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
	 * 把时间转为标准形式
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
			e.printStackTrace();
		}
		return d;
	}

	public static void copyFile(File from, File to) throws Exception {
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

	public static Date getCurrenTime() {
		return java.util.Calendar.getInstance().getTime();
	}

	private static final ObjectMapper mapper = new ObjectMapper();

	public static String jackSon(Object object) {
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
	 * 功能: 删除指定目录及其中的所有内容。
	 * 
	 * @param dir
	 *            要删除的目录
	 * @return 删除成功时返回true，否则返回false。
	 * @author xinwang.xu
	 */
	public static boolean deleteDirectory(File dir) {
		if ((dir == null) || !dir.isDirectory()) {
			throw new IllegalArgumentException("Argument " + dir
					+ " is not a directory. ");
		}
		File[] entries = dir.listFiles();
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].isDirectory()) {
				if (!deleteDirectory(entries[i])) {
					return false;
				}
			} else {
				if (!entries[i].delete()) {
					return false;
				}
			}
		}
		if (!dir.delete()) {
			return false;
		}
		return true;
	}

	// 获得当前日期与本周日相差的天数
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得上周星期一的日期
	public static String getPreviousWeekday() {
		int weeks = 0;
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		String preMonday = new SimpleDateFormat("yyyy-MM-dd").format(monday);
		return preMonday;
	}

	// 获得上周星期日的日期
	public static String getPreviousWeekSunday() {
		int weeks = 0;
		weeks = 0;
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		String preMonday = new SimpleDateFormat("yyyy-MM-dd").format(monday);
		return preMonday;
	}

	// 获取上上周星期一的日期
	public static String getBeforeLastWeekday() throws Exception {
		Date previousWeekday = new SimpleDateFormat("yyyy-MM-dd")
				.parse(getPreviousWeekday()); // 上周星期一日期
		Date beforeLastWeekday = getDateBefore(previousWeekday, 7);

		return new SimpleDateFormat("yyyy-MM-dd").format(beforeLastWeekday);
	}

	// 获取上上周星期日的日期
	public static String getBeforeLastWeekSunday() throws Exception {
		Date previousWeekSunday = new SimpleDateFormat("yyyy-MM-dd")
				.parse(getPreviousWeekSunday()); // 上周星期日期
		Date beforeLastWeekSunday = getDateBefore(previousWeekSunday, 7);

		return new SimpleDateFormat("yyyy-MM-dd").format(beforeLastWeekSunday);
	}

	// 两个int型正数a/b转化为保留两位小数的百分位数
	public static String getFloat(int a, int b) {
		float fa = a;
		float fb = b;
		float f = fa / fb;
		int i = (int) (f * 10000);
		float fi = i;
		return fi / 100 + "%";
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getLastDayOfMonth(Date date) {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(date);
		final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		String ldate = getDateyyyMMdd(lastDate);
		return ldate;
	}

	/**
	 * 求得两个日期中的每一天
	 * 
	 * @param dateFirst
	 * @param dateSecond
	 * @return
	 */
	public static List<String> getDateArray(String dateFirst, String dateSecond) {
		List<String> dateList = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dateOne = dateFormat.parse(dateFirst);
			Date dateTwo = dateFormat.parse(dateSecond);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateOne);
			while (calendar.getTime().before(dateTwo)) {
				dateList.add(dateFormat.format(calendar.getTime()));
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dateList.add(dateSecond);
		return dateList;

	}

	/**
	 * 判断当前SDK版本是否<=2.1.0
	 * 
	 * @param version
	 * @return
	 */
	public static boolean compareSDKVersion(String version) {
		if (version.contains("#"))
			version = version.substring(0, version.indexOf("#"));
		String[] aa = version.split("[.]");
		if (aa.length < 3)
			return false;
		if (Integer.valueOf(aa[0]) < 2)
			return true;
		else if (Integer.valueOf(aa[0]) == 2) {
			if (Integer.valueOf(aa[1]) < 1)
				return true;
			else if (Integer.valueOf(aa[1]) == 1) {
				if (Integer.valueOf(aa[2]) == 0)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 判断当前SDK版本是否<=2.3.1
	 * 
	 * @param version
	 * @return
	 */
	public static boolean compareSDKVersion231(String version) {
		if (version.contains("#"))
			version = version.substring(0, version.indexOf("#"));
		String[] aa = version.split("[.]");
		if (aa.length < 3)
			return false;
		if (Integer.valueOf(aa[0]) < 2)
			return true;
		else if (Integer.valueOf(aa[0]) == 2) {
			if (Integer.valueOf(aa[1]) < 3)
				return true;
			else if (Integer.valueOf(aa[1]) == 3) {
				if (Integer.valueOf(aa[2]) <= 1)
					return true;
				return false;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 去除<=2.1.0版本的#*数据
	 * 
	 * @param version
	 * @return
	 */
	public static String convertVersion(String version) {
		if (!version.contains("#"))
			return version;
		return version.substring(0, version.indexOf("#"));
	}

	/**
	 * 根据应用ID判断是否需要冻结
	 * 
	 * @return
	 */
	public static boolean noFreezeAmt(int appId) {
		if (appId == 1001 || appId == 3000)
			return true;
		return false;
	}

	/**
	 * 根据应用ID判断是否需要验证登陆
	 * 
	 * @return
	 */
	public static boolean noLoginVerify(int appId) {
		if (appId == 1001 || appId == 3000)
			return true;
		return false;
	}

	/**
	 * 仙魔奇侠目前最高版本2.3.1，这个版本之前的不使用异步通知模式
	 * 
	 * @param appId
	 * @param ver
	 * @return
	 */
	public static boolean isNotNotifyUrl(String appId, String ver) {
		if ("128".equals(appId)) {
			if (compareSDKVersion231(ver)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @author xinwang.xu
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	public static Date getDealCurrentDate(Integer day) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar currentTime = Calendar.getInstance();
		currentTime.add(Calendar.DATE, -day);
		Date dealCurrentDate = sdf.parse(sdf.format(currentTime.getTime()));
		return dealCurrentDate;
	}

	/**
	 * 得到Sql(包含在某一个厂商出厂手机注册的用户的columnName集合里)
	 * 
	 * @param userList
	 * @return
	 */
	public static StringBuilder getSql(List columnList, String columnName) {
		StringBuilder userSql = new StringBuilder();
		if (columnList.size() > 0) {
			userSql.append(" and  ");
			userSql.append(columnName);
			userSql.append("  in (");
			for (int z = 0; z < columnList.size(); z++) {
				if (z == 0) {
					userSql.append("'" + columnList.get(z) + "'");
				} else {
					userSql.append("," + "'" + columnList.get(z) + "'");
				}
			}
			userSql.append(")");
		} else {
			userSql.append(" and 1=2");
		}
		return userSql;
	}

	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	public static void main(String[] args) {
		System.out.println(isNotNotifyUrl("128", "2.3.1"));
	}

}
