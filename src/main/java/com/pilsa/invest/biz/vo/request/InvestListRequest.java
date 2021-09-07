package com.pilsa.invest.biz.vo.request;

import com.pilsa.invest.common.web.vo.request.CommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The type Invest list request.
 *
 * @author pilsa_home1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="[전체 투자 상품 조회] - 요청", description="[전체 투자 상품 조회] API Request 입니다.")
public class InvestListRequest extends CommonRequest {

    @ApiModelProperty(value="정렬방법", example = "INCOME")
    private String sortOption;

}
