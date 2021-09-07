package com.pilsa.invest.framework.aspect;

import com.pilsa.invest.common.code.ParameterTypeCode;
import com.pilsa.invest.common.code.ResponseCode;
import com.pilsa.invest.common.constant.ApiConstant;
import com.pilsa.invest.common.web.vo.request.CommonRequest;
import com.pilsa.invest.framework.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class ControllerAspect {
    private static final String NUMBER_REGEX = "[0-9]+";

    @Pointcut("execution(* com.pilsa.invest..*Controller.*(..))")
    public void controllerAdvice(){}

    @Before("controllerAdvice() && args(commonRequest)")
    public void requestLogging(JoinPoint joinPoint, CommonRequest commonRequest){
        MDC.put(ApiConstant.TRANSACTION_ID, commonRequest.getTransactionId());
    }

    //@RequestHeader(value = ApiConstant.X_USER_ID) String userNum
    @Before("controllerAdvice() && args(commonRequest)")
    public void maintenance(JoinPoint joinPoint, CommonRequest commonRequest){
        /*======================================================================================
         * HttpServletRequest 객체 생성.
        ======================================================================================*/
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        /*======================================================================================
         * Header 정보를 모두 가져온다. (공통으로 Header 값 검증이 더 필요의 경우)
        ======================================================================================*/
        Map<String,String> headerMap = getHeaderMap(request);
        /*======================================================================================
         * Controller 수행 전 공통로직을 처리한다.
        ======================================================================================*/
        String headerUserId = headerMap.get(ApiConstant.X_USER_ID);
        if (StringUtils.isEmpty(headerUserId)){
            throw new ServiceException(ResponseCode.INVALID_HEADER, ApiConstant.X_USER_ID);
        }
        if (!headerUserId.matches(NUMBER_REGEX)){
            throw new ServiceException(ResponseCode.INVALID_PARAMETER_TYPE, ParameterTypeCode.NUMBER);
        }
        if (headerUserId.length()>10){
            throw new ServiceException(ResponseCode.INVALID_PARAMETER_RANGE,ApiConstant.X_USER_ID,"0~9999999999");
        }
        try{
            Long.parseLong(headerUserId);
        } catch (NumberFormatException e){
            throw new ServiceException(ResponseCode.INVALID_PARAMETER_RANGE,ApiConstant.X_USER_ID,"0~9999999999");
        }

        /*======================================================================================
         * business 에서 사용할 수 있도록 commonRequest 에 Set 한다.
        ======================================================================================*/
        commonRequest.setMemberNum(Long.parseLong(headerUserId));
        commonRequest.setTransactionId(MDC.get(ApiConstant.TRANSACTION_ID));
    }

    private Map getHeaderMap(HttpServletRequest request) {
        Map<String,String> headerMap = new HashMap();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            headerMap.put(key.toLowerCase(),request.getHeader(key));
        }
        return headerMap;
    }
}