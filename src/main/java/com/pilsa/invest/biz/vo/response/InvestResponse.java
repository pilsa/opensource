package com.pilsa.invest.biz.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The type Invest response.
 *
 * @author pilsa_home1
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiModel(value="[투자하기] - 응답", description="[투자하기] API Response 입니다.")
public class InvestResponse {

    @ApiModelProperty(value="상품상태")
    private String productStatus;
    @ApiModelProperty(value="상품상태코드")
    private String productStatusCode;

}
