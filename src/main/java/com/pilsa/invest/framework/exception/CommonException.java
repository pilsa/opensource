package com.pilsa.invest.framework.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * The type Common exception.
 * 미사용.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommonException extends RuntimeException{

    /**
     * The Http status.
     */
    protected HttpStatus httpStatus;
    /**
     * The Error code.
     */
    protected String errorCode;
    /**
     * The Error message.
     */
    protected String errorMessage;

}
