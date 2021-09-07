package com.pilsa.invest.framework.exception;

import com.pilsa.invest.common.code.ResponseCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * The type Service exception.
 *
 */
@NoArgsConstructor
@Getter
public class ServiceException extends RuntimeException{
    private ResponseCode responseCode;
    protected HttpStatus httpStatus;
    protected String errorCode;
    protected String errorMessage;

    public boolean isSuccess(){
        return responseCode.isSuccess();
    }
    public String getCode(){
        return responseCode.getCode();
    }
    public String getMessage(){
        return responseCode.getMessage();
    }
    public HttpStatus getHttpStatus(){
        return responseCode.getHttpStatus();
    }

    public ServiceException(ResponseCode responseCode) {
        this.responseCode = responseCode;
        errorMessage = responseCode.getMessage();
        errorCode = responseCode.toString();
        httpStatus = responseCode.getHttpStatus();
    }

    public ServiceException(ResponseCode responseCode, Object... params) {
        this.responseCode = responseCode;
        errorMessage = String.format(responseCode.getMessage(), params);
        errorCode = responseCode.toString();
        httpStatus = responseCode.getHttpStatus();
    }
}
