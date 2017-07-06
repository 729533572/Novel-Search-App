package com.yonyou.framework.library.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 具体校验规则工具类
 * 
 * @author joe
 * @version 2015.05.25
 */
public class PatternUtils {
	/**
	 * 
	 * Description: 是否是手机号 Title: isMobileNO
	 * 
	 * @param mobiles
	 * @return boolean true是
	 */
	public static boolean isMobileNO(CharSequence mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	public static boolean isRightName(CharSequence mobiles) {
		Pattern p = Pattern
				.compile("/^[\u4e00-\u9fa5 ]{2,20}$/");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 
	 * Description: 是否是联通手机号 Title: isChinaUnicom
	 * 
	 * @param mobiles
	 * @return boolean true是
	 */
	public static boolean isChinaUnicom(String mobiles) {
		boolean b = false;
		String sim2 = mobiles.substring(0, 3);
		if (sim2.equals("130") || sim2.equals("131") || sim2.equals("132")
				|| sim2.equals("155") || sim2.equals("156")
				|| sim2.equals("185") || sim2.equals("186")
				|| sim2.equals("145")) {
			b = true;
		}
		return b;
	}
	/**
	 * 正则表达式：验证邮箱
	 */

	/**
	 * 判断邮箱格式是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(CharSequence email) {
//		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		String str = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 验证用户名格式
	 * 只能是 中文 英文字母 数字长度 1-15
	 * @param userName
	 * @return
	 */
	public static boolean checkName(String userName) {
		Pattern p = Pattern.compile("[0-9a-zA-Z\u4e00-\u9fa5]{1,18}");
		Matcher m = p.matcher(userName);
		return m.matches();
	}

	/**
	 * 判断账户格式是否正确（只能是字母或数字，长度在3-14之间）
	 * 
	 * @param userName
	 * @return
	 */
	public static boolean checkAccount(String userName) {
		Pattern p = Pattern.compile("^[0-9a-zA-Z][0-9A-Za-z]{3,14}$");
		Matcher m = p.matcher(userName);
		return m.matches();
	}

	/**
	 * 密码格式是否正确（只能是数字、字母 6-16wei）
	 * 
	 * @param passWord
	 * @return
	 */
	// Pattern p = Pattern.compile("^[a-zA-Z0-9]{6}[a-zA-Z0-9]*$");
	public static boolean checkPassWord(String passWord) {
		Pattern p = Pattern.compile("^[0-9a-zA-Z][0-9A-Za-z]{5,16}$");
		Matcher m = p.matcher(passWord);
		return m.matches();
	}
	/**
	 * 密码2格式是否正确（只能是6位数字）
	 *
	 * @param passWord
	 * @return
	 */
	// Pattern p = Pattern.compile("^\d{6}$");
	public static boolean checkPassWordSix(String passWord) {
		Pattern p = Pattern.compile("^\\d{6}$");
		Matcher m = p.matcher(passWord);
		return m.matches();
	}
	/**
	 * 
	 * @Title: isIdentityValid 
	 * @Description: 检验身份证格式
	 * @param identityStr
	 * @return
	 * @return: boolean
	 */
	public static boolean checkIdentity(String identityStr) {
		Pattern p = Pattern.compile("^(\\d{14}|\\d{17})(\\d|[xX])$");
		Matcher m = p.matcher(identityStr);
		return m.matches();
	}

	public static void main(String[] args) {
		System.out.println(PatternUtils
				.checkPassWord("121212121212@12-12.co-m.cn"));
	}
}
