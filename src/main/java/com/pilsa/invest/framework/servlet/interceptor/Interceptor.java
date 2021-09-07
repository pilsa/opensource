package com.pilsa.invest.framework.servlet.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class Interceptor extends HandlerInterceptorAdapter {

    /**
     * Controller의 처리 직전 시행된다.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler controller
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    /**
     * controller의 handler가 끝나면 view 랜더링 전에 해당 메소드가 호출.
     * modelAndView 인자를 이용해 view model 조작이 가능
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //postHandle를 위한 Empty Function
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * view에서 최종 결과가 생성하는 일을 포함한 모든 일이 완료 되었을 때 실행.
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }


    /**
     * Servlet 3.0 부터 비동기 요청이 가능.
     * 비동기 요청시 postHandle와 afterCompletion은 실행되지 않고, afterConcurrentHandlingStarted 메소드가 실행.
     * @param request
     * @param response
     * @param handler
     * @throws Exception
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (log.isInfoEnabled()) log.info("[{}] :: Action",Thread.currentThread().getStackTrace()[1].getMethodName());
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
