package com.security_asymmetric_encryption.exception;

import com.security_asymmetric_encryption.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;
    private final Object[] args;

    public BusinessException(ErrorCode errorCode,final Object... args){
        super(getFormaterMessage(errorCode,args));
        this.errorCode = errorCode;
        this.args = args;
    }

    private static String getFormaterMessage(final ErrorCode errorCode, final Object... args){
        if(args != null && args.length > 0){
            return String.format(errorCode.getDefaultMessage(),args);
        }
        return errorCode.getDefaultMessage();
    }

}
