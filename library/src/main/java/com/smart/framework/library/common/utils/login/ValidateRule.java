package com.smart.framework.library.common.utils.login;


import android.text.TextUtils;


/**
 * <p>校验规则</p>
 * <p>默认提供了如下校验规则，可以直接调用validator.addRules添加
 * <pre>
 *IS_DIGITS_ONLY	是否为纯数字
 *IS_NOT_EMPTY	不可为空验证规则
 *IS_MOBILE_NUMBER	是否为手机号
 *IS_CHINAUNICOM_MOBILE_NUMBER	是否为中国联通手机号规则
 *IS_VALID_EMAIL	是否合法的电子邮件
 *IS_REACH_MAX_LENGTH	是否超出最大允许长度
 *IS_VALID_PASSWORD 是否是有效的密码格式
 * </pre>
 * </p>
 * <p>
 * 如果需要自己扩展，需要构造时传入规则名称
 * </p>
 *
 * @author joe
 * @version 2015.05.25
 */

public abstract class ValidateRule {
	private final String ruleName;

	public ValidateRule(String ruleName){
		this.ruleName = ruleName;
	}

	public String getRuleName(){
		return ruleName;
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ruleName == null) ? 0 : ruleName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		ValidateRule other = (ValidateRule)obj;
		if(ruleName == null){
			if(other.ruleName != null) return false;
		}
		else if(!ruleName.equals(other.ruleName)) return false;
		return true;
	}

	/**
	 * 是否为纯数字
	 */
	public static ValidateRule IS_DIGITS_ONLY = new ValidateRule("IS_DIGITS_ONLY"){
		@Override
		public ReturnObject doValidate(CharSequence text){
			ReturnObject ro = new ReturnObject("是否为纯数字");
			if(TextUtils.isDigitsOnly(text)){
				ro.isSuccess = true;
			}
			else{
				ro.isSuccess = false;
				ro.data = "字符串不是纯数字";
			}
			return ro;
		}
	};

	/**
	 * 不可为空验证规则
	 */
	public static ValidateRule IS_NOT_EMPTY = new ValidateRule("IS_NOT_EMPTY"){
		@Override
		public ReturnObject doValidate(CharSequence text){
			ReturnObject ro = new ReturnObject(this.getRuleName());
			if(TextUtils.isEmpty(text)){
				ro.isSuccess = false;
				ro.data = "字符串为空";
			}
			else{
				ro.isSuccess = true;
			}
			return ro;
		}
	};

	/**
	 * 是否为手机号
	 */
	public static ValidateRule IS_MOBILE_NUMBER = new ValidateRule("IS_MOBILE_NUMBER"){
		@Override
		public ReturnObject doValidate(CharSequence text){
			ReturnObject ro = new ReturnObject(this.getRuleName());
			if(PatternUtils.isChinaPhoneLegal(text.toString())){
				ro.isSuccess = true;
			} else{
				ro.isSuccess = false;
				ro.data = "请填写正确的手机号码";
			}
			return ro;
		}
	};

	/**
	 * 是否为中国联通手机号规则
	 */
	public static ValidateRule IS_CHINAUNICOM_MOBILE_NUMBER = new ValidateRule("IS_CHINAUNICOM_MOBILE_NUMBER"){
		@Override
		public ReturnObject doValidate(CharSequence text){
			ReturnObject ro = new ReturnObject(this.getRuleName());
			if(PatternUtils.isChinaUnicom(text.toString())){
				ro.isSuccess = true;
			}
			else{
				ro.isSuccess = false;
				ro.data = "不是中国联通手机号";
			}
			return ro;
		}
	};

	/**
	 * 是否合法的电子邮件
	 */
	public static ValidateRule IS_VALID_EMAIL = new ValidateRule("IS_VALID_EMAIL"){
		@Override
		public ReturnObject doValidate(CharSequence text){
			ReturnObject ro = new ReturnObject(this.getRuleName());
			if(PatternUtils.isEmail(text)){
				ro.isSuccess = true;
			}
			else{
				ro.isSuccess = false;
				ro.data = "请填写正确的电子邮件";
			}
			return ro;
		}
	};
	/**
	 * 验证用户名格式
	 * 只能是 中文 英文字母 数字长度 1-18
	 */
	public static ValidateRule IS_VALID_NAME = new ValidateRule("IS_VALID_NAME"){
		@Override
		public ReturnObject doValidate(CharSequence text){
			ReturnObject ro = new ReturnObject(this.getRuleName());
			if(PatternUtils.checkName(text.toString())){
				ro.isSuccess = true;
			}
			else{
				ro.isSuccess = false;
				ro.data = "请输入不含特殊字符的姓名";
			}
			return ro;
		}
	};
	/**
	 * 是否合法的身份证
	 */
	public static ValidateRule IS_VALID_IDENTITY = new ValidateRule("IS_VALID_IDENTITY"){
		@Override
		public ReturnObject doValidate(CharSequence text){
			ReturnObject ro = new ReturnObject(this.getRuleName());
			if(!TextUtils.isEmpty(text) && PatternUtils.checkIdentity(text.toString())){
				ro.isSuccess = true;
			}
			else{
				ro.isSuccess = false;
				ro.data = "不符合身份证格式";
			}
			return ro;
		}
	};

	/**
	 * 是否超出最大允许长度
	 */
	public static ValidateRule IS_REACH_MAX_LENGTH = new ValidateRule("IS_REACH_MAX_LENGTH"){
		@Override
		public ReturnObject doValidate(CharSequence text){
			ReturnObject ro = new ReturnObject(this.getRuleName());
			if(text.length() > 100){
				ro.isSuccess = false;
				ro.data = "字符串超长";
			}
			else{
				ro.isSuccess = true;
			}
			return ro;
		}
	};
	/**
	 * 验证密码格式是否正确 
	 * 只能包含字母、数字、至少是6位
	 * 错误提示 6-16位字符,可包含数字，字母(不区分大小写)
	 */
	public static String PASSWORDERRORALERT = "6-16位字符,可包含数字,字母(不区分大小写)";
	public static ValidateRule IS_VALID_PASSWORD = new ValidateRule("IS_VALID_PASSWORD"){
		@Override
		public ReturnObject doValidate(CharSequence text){
			ReturnObject ro = new ReturnObject(this.getRuleName());
			if(!TextUtils.isEmpty(text)&& PatternUtils.checkPassWord(text.toString())){
				ro.isSuccess = true;
			}
			else{
				ro.isSuccess = false;
				ro.data =PASSWORDERRORALERT;
			}
			return ro;
		}
	};

	/**
	 * 是否为6位验证码
	 */
	public static ValidateRule IS_CORRECT_VERIFYCODE = new ValidateRule("IS_CORRECT_VERIFYCODE"){
		@Override
		public ReturnObject doValidate(CharSequence text){
			ReturnObject ro = new ReturnObject(this.getRuleName());
			if(PatternUtils.isVerifyCode(text)){
				ro.isSuccess = true;
			}
			else{
				ro.isSuccess = false;
				ro.data = "请正确填写验证码";
			}
			return ro;
		}
	};

	/**
	 * 是否为正确的纳税人识别号
	 */
	public static ValidateRule IS_CORRECT_TAXPAYER_ID = new ValidateRule("IS_CORRECT_TAXPAYER_ID"){
		@Override
		public ReturnObject doValidate(CharSequence text){
			ReturnObject ro = new ReturnObject(this.getRuleName());
			if(PatternUtils.isCorrectTaxPayerId(text)){
				ro.isSuccess = true;
			}
			else{
				ro.isSuccess = false;
				ro.data = "税号格式不正确";
			}
			return ro;
		}
	};

	protected abstract ReturnObject doValidate(CharSequence text);

}
