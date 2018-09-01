package com.smart.framework.library.common.utils.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * <p>
 * 校验类
 * </p>
 * <p>
 * 使用方法：
 * </p>
 * <pre>
 * Validator validator = null;
 * validator = new Validator();
 * validator.addRules(ValidateRule.IS_NOT_EMPTY, ValidateRule.IS_REACH_MAX_LENGTH,
 *     ValidateRule.IS_MOBILE_NUMBER, ValidateRule.IS_ChinaUnicom_MOBILE_NUMBER);
 * Log.v(Constants.LOG_TAG, validator.val(&quot;1234567890123456789012345678901234567890&quot;)
 *     .getErrorMessage());
 * validator = new Validator();
 * validator.addRules(ValidateRule.IS_NOT_EMPTY, ValidateRule.IS_VALID_EMAIL);
 * Log.v(Constants.LOG_TAG, &quot;------------------------&quot;);
 * </pre>
 * @author joe
 * @version 2015.05.25
 */

public class Validator {
  private LinkedHashSet<ValidateRule> validateRules = new LinkedHashSet<ValidateRule>();

  public Validator() {
  }

  public Validator(Collection<ValidateRule> validateRules) {
    this.validateRules.addAll(validateRules);
  }

  @Override
  public String toString() {
    return "Validator [validateRules=" + validateRules + "]";
  }

  /**
   * 增加校验规则，多个规则用,隔开；已经写好的校验规则参考 {@link com.jiuqi.njt.util.ValidateRule
   * ValidateRule}
   * 
   * @param rules
   *          void
   */
  public void addRules(ValidateRule... rules) {
    if (null == rules)
      return;
    for (ValidateRule r : rules) {
      if (!validateRules.contains(r)) {
        validateRules.add(r);
      }
    }
  }

  /**
   * 删除校验规则，多个规则用,隔开
   * 
   * @param rules
   *          void
   */
  public void removeRules(ValidateRule... rules) {
    if (null == rules)
      return;
    for (ValidateRule r : rules) {
      validateRules.remove(r);
    }
  }

  /**
   * 校验规则中有一条失败，就终止后续校验
   * 
   * @param text
   * @return ReturnObject
   */
  public ReturnObject val(CharSequence text) {
    return val(text, true);
  }

  /**
   * @param text
   * @param stopWhenValidationFall
   *          如为false，校验规则中有一条失败，将继续后续校验，有可能导致未知异常抛出
   * @return ReturnObject
   */
  public ReturnObject val(CharSequence text, boolean stopWhenValidationFall) {
    ReturnObject ro = new ReturnObject();
    ReturnObject o = null;
    ArrayList<ReturnObject> rlist = new ArrayList<ReturnObject>();
    for (ValidateRule r : validateRules) {
      o = r.doValidate(text);
      if (!o.isSuccess) {
        rlist.add(o);
        if (stopWhenValidationFall) {
          break;
        }
      }
    }
    if (rlist.size() != 0) {
      ro.data = rlist;
      ro.isSuccess = false;
    } else {
      ro.isSuccess = true;
    }
    return ro;
  }

  /**
   * 跟val()用法一样，只不过返回boolean，只要有一条规则验证失败，即返回false，全部通过则返回true。
   * 
   * @param text
   * @return boolean
   */
  public boolean validate(CharSequence text) {
    ReturnObject o = null;
    for (ValidateRule r : validateRules) {
      o = r.doValidate(text);
      if (!o.isSuccess) {
        return false;
      }
    }

    return true;
  }

  /**
   * 清除所有的规则
   */
  public void clearRules() {
    validateRules.clear();
  }
}
