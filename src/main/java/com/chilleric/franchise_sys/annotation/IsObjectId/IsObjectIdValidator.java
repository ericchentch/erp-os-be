package com.chilleric.franchise_sys.annotation.IsObjectId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;

public class IsObjectIdValidator implements ConstraintValidator<IsObjectId, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (ObjectId.isValid(value)) {
      return true;
    } else {
      return false;
    }
  }
}
