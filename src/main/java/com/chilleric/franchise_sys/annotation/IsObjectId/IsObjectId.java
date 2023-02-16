package com.chilleric.franchise_sys.annotation.IsObjectId;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IsObjectIdValidator.class)
public @interface IsObjectId {
  public String message() default LanguageMessageKey.INVALID_OBJECT_ID;

  // represents group of constraints
  public Class<?>[] groups() default {};

  // represents additional information about annotation
  public Class<? extends Payload>[] payload() default {};
}
