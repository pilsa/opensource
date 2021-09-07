package com.pilsa.invest.common.web.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The type Common response.
 * 공통 응답 (모든 Response VO는 상속받아서 사용한다.)
 *
 * @author pilsa_home1
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommonResponse {

    private String success;
    private String code;
    private String message;
    private Object data;

}
