package com.chilleric.franchise_sys.annotation.IsBase64;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.tomcat.util.codec.binary.Base64;
import com.chilleric.franchise_sys.constant.TypeValidation;

public class IsBase64Validator implements ConstraintValidator<IsBase64, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!Base64.isBase64(value)) {
            return false;
        } else {
            try {
                String decodedNewPassword = new String(Base64.decodeBase64(value));
                if (!decodedNewPassword.matches(TypeValidation.BASE64_REGEX)) {
                    return false;
                }
            } catch (IllegalArgumentException e) {
                return false;

            } catch (IllegalStateException e) {
                return false;

            }
        }
        return true;
    }

}
