package com.liuming.util;

 
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
 
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.liuming.util.ConstantUtil;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import sun.misc.BASE64Encoder;
 
 

/**
 * String manipulation tools
 * 
 * @author Administrator
 * 
 */
public class StringUtil {

	private static Pattern integerPattern = Pattern.compile("^-?\\d+");// 整数的正则

	static Logger log = Logger.getLogger("logger");

	/**
	 * Converting null string to ""
	 * 
	 * @param src
	 * @return
	 */
	public static String tranNullString(String src) {
		return null == src ? "" : src;
	}

	/**
	 * 2012-08-07 07:55:57.0 -> 2012-08-07 07:55:57
	 */
	public static String formatStringDate(String str) {
		if (null == str) {
			return null;
		}
		if (str.endsWith(".0")) {
			return str.substring(0, str.length() - 2);
		}
		return null;
	}

	public static String getRandomStr(String[] arr) {
		try {
			List<String> list = Arrays.asList(arr);
			Collections.shuffle(list);
			return list.get(new Random().nextInt(arr.length)).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isNumber(String num) {
		Matcher mr = integerPattern.matcher(num.trim());
		return mr.matches();
	}

	/**
	 * 64位加密
	 * 
	 * @param encodeStr
	 * @return
	 * @author
	 */
	public static String getBase64EncodeStr(String encodeStr) {
		BASE64Encoder encoder = new BASE64Encoder();
		return new String(encoder.encodeBuffer(encodeStr.getBytes()));
	}

	/**
	 * To check whether a string is empty ( contains null and "")
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * Check the time format 'YYYYMMDDHHMMII'
	 * 
	 * @param dateString
	 * @return
	 */
	public static boolean isLegalDate(String dateString) {
		String regeb = "^(?:(?!0000)[0-9]{4}(?:(?:0[1-9]|1[0-2])(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)0229)((0[0-9])|(1[0-9])|(2[0-3]))[0-5][0-9][0-5][0-9]{1}";
		return dateString.matches(regeb);
	}

	/**
	 * Check the time format 'YYYY-MM-DD HH:MM:II'
	 * 
	 * @param strDate
	 * @return
	 */
	public static boolean isLegalDate2(String strDate) {
		if (isEmpty(strDate)) {
			return false;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			format1.format(formatter.parse(strDate));
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 验证金额
	public static boolean isMoney(String str) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后一位的数字的正则表达式
		java.util.regex.Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] agu) {
		System.out.println("AG12345678".substring(2, 10));
	}

	
	
	public static String replaceMapValByKey(String sorStr, String key, String val) {
		String[] arr = sorStr.split("&");
		Map<String, String> m = new HashMap<String, String>();
		String[] arr2;
		for (int i = 0; i < arr.length; i++) {
			arr2 = arr[i].split("=");
			System.out.println("====" + arr2.length);
			if (null == arr2 || arr2.length == 0 || ( arr2.length == 1 && "".equals( arr2[0])))
				continue;
			else if (arr2.length == 1)
				m.put("spckRmk", arr2[0]);
			else
				m.put(arr2[0], arr2[1]);
		}
		String res = "";
		int iCnt = 0;
		for (Map.Entry<String, String> entry : m.entrySet()) {
			iCnt++;
			if (iCnt == 1) {
				if (entry.getKey().equals(key))
					res += entry.getKey() + "=" + val;
				else
					res += entry.getKey() + "=" + entry.getValue();
			} else {
				if (entry.getKey().equals(key))
					res += "&" + entry.getKey() + "=" + val;
				else
					res += "&" + entry.getKey() + "=" + entry.getValue();
			}
		}
		return res;
	}
	public static String getCellValue(Cell cell) {
		String value = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				value = cell.getNumericCellValue() + "";
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date != null) {
						value = new SimpleDateFormat("yyyy-MM-dd").format(date);
					} else {
						value = "";
					}
				} else {
					value = new DecimalFormat("0").format(cell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				value = cell.getBooleanCellValue() + "";
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				value = cell.getCellFormula() + "";
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				value = "";
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				value = "非法字符";
				break;
			default:
				value = "未知类型 ";
				break;
			}
		}
		return value.trim();
	}
}
