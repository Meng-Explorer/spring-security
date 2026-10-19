package com.security_asymmetric_encryption.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND("USER_NOT_FOUND","User Not Found With Id %S", NOT_FOUND),
    CHANGE_PASSWORD_MISMATCH("CHANGE_PASSWORD_MISMATCH","Current Password and New Password are Not The Same" ,BAD_REQUEST ),
    INVALID_CURRENT_PASSWORD("INVALID_CURRENT_PASSWORD","Invalid Current Password" ,BAD_REQUEST ),
    ACCOUNT_ALREADY_DEACTIVATE("ACOUNT_ALREADY_DEACTIVATE","Account Already Deactivated" ,BAD_REQUEST ),
    ACCOUNT_ALREADY_REACTIVATE("ACCOUNT_ALREADY_REACTIVATE","Account Already Reactivated" ,BAD_REQUEST ),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS","Email Already Exists" ,BAD_REQUEST ),
    PHONE_NUMBER_ALREADY_EXISTS("PHONE_NUMBER_ALREADY_EXISTS","Phone Number Already Exists" ,BAD_REQUEST ),
    PASSWORD_MISMATCH("PASSWORD_MISMATCH","Password Mismatch" ,BAD_REQUEST ),
    ACCOUNT_USER_DISABLED("ACCOUNT_USER_DISABLED","Account User Disabled" ,UNAUTHORIZED ),
    BAD_CREDENTIALS("BAD_CREDENTIALS","Username or Password is incorrect" ,UNAUTHORIZED ),
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND","User Not Found With Id %S", NOT_FOUND),
    INTERNAL_EXCEPTION("INTERNAL_EXCEPTION","Internal Exception" ,INTERNAL_SERVER_ERROR ),


    ;

    private final String code;
    private final String defaultMessage;
    private final HttpStatus status;
    // i18n l10n

    ErrorCode(String code, String defaultMessage, HttpStatus status) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.status = status;
    }

}
