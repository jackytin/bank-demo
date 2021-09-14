package com.doublechaintech.validator;

import com.doublechaintech.util.TextUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

  @Override
  public boolean isValid(
      String phoneNumber, ConstraintValidatorContext pConstraintValidatorContext) {
    if (phoneNumber == null) {
      return true;
    }
    return TextUtils.formatChinaMobile(phoneNumber) != null;
  }
}
