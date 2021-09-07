package com.pilsa.invest.framework.servlet.handler;

import com.pilsa.invest.common.code.ResponseCode;
import com.pilsa.invest.common.constant.ApiConstant;
import com.pilsa.invest.common.web.vo.request.CommonResponse;
import com.pilsa.invest.common.web.vo.request.ErrorResponse;
import com.pilsa.invest.framework.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;


/**
 * The type Rest controller handler.
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.pilsa.invest.biz.controller"})
public class RestControllerHandler extends ResponseEntityExceptionHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body
            , MethodParameter returnType
            , MediaType selectedContentType
            , Class<? extends HttpMessageConverter<?>> selectedConverterType
            , ServerHttpRequest request
            , ServerHttpResponse response) {

        /*======================================================================================
         * mdcAdapter clear
        ======================================================================================*/
        String transactionId = MDC.get(ApiConstant.TRANSACTION_ID);
        MDC.clear();
        /*======================================================================================
         * 트레킹을 위한 TRANSACTION_ID를 해더에 준다.
        ======================================================================================*/
        response.getHeaders().add(ApiConstant.TRANSACTION_ID,transactionId);

        return CommonResponse.builder()
                .success(String.valueOf(ResponseCode.SUCCESS.isSuccess()))
                //.code(ResponseCode.SUCCESS.getCode())
                //.message(ResponseCode.SUCCESS.getMessage())
                .data(body)
                .build();
    }

    /**
     * Handle service exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
        /*======================================================================================
         * 애러 로그출력
        ======================================================================================*/
        log.error("["+ ex.getErrorCode()+"] " + ex.getErrorMessage(), ex);
        /*======================================================================================
         * mdcAdapter clear
        ======================================================================================*/
        String transactionId = MDC.get(ApiConstant.TRANSACTION_ID);
        MDC.clear();
        /*======================================================================================
         * 트레킹을 위한 TRANSACTION_ID를 해더에 준다.
        ======================================================================================*/
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ApiConstant.TRANSACTION_ID,transactionId);
        /*======================================================================================
         * 애러응답
        ======================================================================================*/
        return new ResponseEntity<>(ErrorResponse.builder()
                .success(String.valueOf(ex.isSuccess()))
                .errors(ErrorResponse.Errors.builder()
                        .code(ex.getErrorCode())
                        .message(ex.getErrorMessage())
                        .build()).build()
                ,httpHeaders
                , ex.getHttpStatus());
    }


    /**
     * Handle exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        /*======================================================================================
         * 애러 로그출력
        ======================================================================================*/
        log.error(ex.getMessage(), ex);
        /*======================================================================================
         * mdcAdapter clear
        ======================================================================================*/
        String transactionId = MDC.get(ApiConstant.TRANSACTION_ID);
        MDC.clear();

        ResponseCode serverError = ResponseCode.INTERNAL_SERVER_ERROR;
        /*======================================================================================
         * 트레킹을 위한 TRANSACTION_ID를 해더에 준다.
        ======================================================================================*/
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ApiConstant.TRANSACTION_ID,transactionId);
        /*======================================================================================
         * 애러응답
        ======================================================================================*/
        return new ResponseEntity<>(ErrorResponse.builder()
                .success(String.valueOf(serverError.isSuccess()))
                .errors(ErrorResponse.Errors.builder()
                        .code(serverError.getCode())
                        .message(serverError.getMessage())
                        .build()).build()
                ,httpHeaders
                ,serverError.getHttpStatus());
    }


    /**
     * Handle violation exception response entity.
     * hibernate.validator 사용.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleViolationException(ConstraintViolationException ex) {
        /*======================================================================================
         * 애러 로그출력
        ======================================================================================*/
        log.error(ex.getMessage(), ex);
        /*======================================================================================
         * mdcAdapter clear
        ======================================================================================*/
        String transactionId = MDC.get(ApiConstant.TRANSACTION_ID);
        MDC.clear();
        ResponseCode code = ResponseCode.MISSING_REQUIRED_PARAMETERS;
        /*======================================================================================
         * 트레킹을 위한 TRANSACTION_ID를 해더에 준다.
        ======================================================================================*/
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ApiConstant.TRANSACTION_ID,transactionId);
        /*======================================================================================
         * 애러응답
        ======================================================================================*/
        return new ResponseEntity<>(ErrorResponse.builder()
                .success(String.valueOf(code.isSuccess()))
                .errors(ErrorResponse.Errors.builder()
                        .code(code.getCode())
                        .message(getResultMessage(ex.getConstraintViolations().iterator()))
                        .build()).build()
                ,httpHeaders
                ,code.getHttpStatus());
    }

    protected String getResultMessage(final Iterator<ConstraintViolation<?>> violationIterator) {
        final StringBuilder resultMessageBuilder = new StringBuilder();
        while (violationIterator.hasNext() == true) {
            final ConstraintViolation<?> constraintViolation = violationIterator.next();
            resultMessageBuilder
                    .append("[ ")
                    .append(getPopertyName(constraintViolation.getPropertyPath().toString())) // 유효성 검사가 실패한 속성
                    .append(" 는 ")
                    //.append(constraintViolation.getInvalidValue()) // 유효하지 않은 값
                    .append(constraintViolation.getMessage()) // 유효성 검사 실패 시 메시지
                    .append(" ]");
            if (violationIterator.hasNext() == true) {
                resultMessageBuilder.append(", ");
            }
        }

        return resultMessageBuilder.toString();
    }
    protected String getPopertyName(String propertyPath) {
        return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
    }
}
