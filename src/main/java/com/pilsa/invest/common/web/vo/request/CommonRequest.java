package com.pilsa.invest.common.web.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.BindingResult;

/**
 * The type Common request.
 * 공통 요청 (모든 Request VO는 상속받아서 사용한다.)
 *
 * @author pilsa_home1
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CommonRequest {

    @JsonIgnore
    @ApiParam(value="트렌젝션ID")
    private String transactionId;

    @JsonIgnore
    @ApiParam(value="회원번호")
    private long memberNum;
}
