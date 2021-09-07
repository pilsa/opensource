package com.pilsa.invest.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {
    /*======================================================================================
     * 200
    ======================================================================================*/
    SUCCESS(HttpStatus.OK,                    true  ,"SUCCESS", "성공"),
    SUCCESS_CREATED(HttpStatus.CREATED,       true  ,"SUCCESS_CREATED", "등록성공"),

    /*======================================================================================
     * 400
    ======================================================================================*/
    MISSING_REQUIRED_PARAMETERS(HttpStatus.BAD_REQUEST, false ,"MISSING_REQUIRED_PARAMETERS","%s"),
    INVALID_HEADER(HttpStatus.BAD_REQUEST,              false ,"INVALID_HEADER", "header 필수 파라미터가 누락되었습니다. : [%s]"),
    INVALID_PARAMETER_TYPE(HttpStatus.BAD_REQUEST,      false ,"INVALID_PARAMETER_TYPE","파라미터 타입이 올바르지 않습니다. : [%s]"),
    INVALID_PARAMETER_RANGE(HttpStatus.BAD_REQUEST,     false ,"INVALID_PARAMETER_RANGE","[%s] 파라미터 값의 입력 범위는 [%s] 입니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,           false ,"INVALID_PARAMETER","파라미터 값이 올바르지 않습니다. : [%s]"),

    NOT_FOUND_RESOURCE(HttpStatus.NOT_FOUND,            false ,"NOT_FOUND_RESOURCE", "요청한 자원에 대한 정보가 없습니다."),
    PRODUCT_NOT_READY(HttpStatus.CONFLICT,              false ,"PRODUCT_NOT_READY", "투자모집이 시작되지 않은 상품입니다. : [%s]"),
    PRODUCT_END(HttpStatus.CONFLICT,                    false ,"PRODUCT_END", "투자모집이 종료된 상품입니다. : [%s]"),

    ONEC_LIMIT_EXCESS(HttpStatus.CONFLICT,              false ,"ONEC_LIMIT_EXCESS", "요청하신 투자금액이 1회 투자한도금액을 초과하였습니다. 해당 제공사의 1회 투자한도금액은 [%s]입니다."),
    ALREADY_INVESTED(HttpStatus.CONFLICT,              false ,"ALREADY_INVESTED", "이미 투자중인 상품입니다. 투자금 변경을 원하는 경우, 기존 건을 취소하고 진행해주세요."),
    REALTY_LIMIT_EXCESS(HttpStatus.CONFLICT,             false ,"ONEC_LIMIT_EXCESS", "부동산(담보/PF) 상품에 투자하신 누적금액이 해당 제공사의 부동산 투자한도 금액 [%s]을 초과하였습니다."),
    TOTAL_LIMIT_EXCESS(HttpStatus.CONFLICT,             false ,"ONEC_LIMIT_EXCESS", "투자하신 누적금액이 해당 제공사의 업권 통합 최대 투자한도 금액 [%s]을 초과하였습니다."),
    BUSINESS_LIMIT_EXCESS(HttpStatus.CONFLICT,             false ,"ONEC_LIMIT_EXCESS", "투자하신 누적금액이 해당 제공사 별 최대 투자한도 금액 [%s]을 초과하였습니다."),
    RECRUIT_LIMIT_EXCESS(HttpStatus.CONFLICT,             false ,"ONEC_LIMIT_EXCESS", "해당 상품의 총 모집금액을 초과하였습니다. 투자가능 금액은 [%s]입니다."),

    NOT_FOUND_API(HttpStatus.NOT_FOUND,                 false ,"NOT_FOUND_API", "요청한 엔드포인트는 존재하지 않음(일반적인 HTTP 404 에러)"),
    API_NOT_FOUND(HttpStatus.BAD_REQUEST,               false ,"API_NOT_FOUND","요청이 API가 요구하는 URL 혹은 HTTP 메서드와 다름"),
    INVALID_CONTENT_TYPE(HttpStatus.BAD_REQUEST,        false ,"INVALID_CONTENT_TYPE","	요청이 API가 요구하는 Content Type과 다름"),
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST,           false ,"MISSING_PARAMETER","필요한 파라미터 값이 제공되지 않았음"),

    /*======================================================================================
     * 500
    ======================================================================================*/
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,false , "INTERNAL_SERVER_ERROR", "시스템장애"),
    FAIL_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR,false , "FAIL_REQUEST", "API 요청 처리 실패"),
    TIMEOUT(HttpStatus.INTERNAL_SERVER_ERROR,false , "TIMEOUT", "처리시간 초과 에러"),
    ;

    private HttpStatus httpStatus;
    private boolean success;
    private String code;
    private String message;

}
