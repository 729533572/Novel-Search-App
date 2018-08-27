package com.smart.framework.library.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyUtil {

	// 校验身份证
	public static boolean checkIdentity(String identityStr){
		Pattern p = Pattern.compile("^(\\d{14}|\\d{17})(\\d|[xX])$");
		Matcher m = p.matcher(identityStr);
		return m.matches();
	}

	// 校验手机号
	public static boolean checkMobile(String phontStr) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");
		Matcher m = p.matcher(phontStr);
		return m.matches();
	}
	
	// 校验中文
	public static boolean checkChinese(String chineseStr){
		Pattern p = Pattern.compile("^[\u4e00-\u9fa5]*$");
		Matcher m = p.matcher(chineseStr);
		return m.matches();
	}
}