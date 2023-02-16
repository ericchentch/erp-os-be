package com.chilleric.franchise_sys.annotation.IsBase64;

import com.chilleric.franchise_sys.annotation.IsObjectId.IsObjectIdValidator;
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
public @interface IsBase64 {
  public String message() default LanguageMessageKey.IS_NOT_BASE64;

  public Class<?>[] groups() default {};

  public Class<? extends Payload>[] payload() default {};
}
