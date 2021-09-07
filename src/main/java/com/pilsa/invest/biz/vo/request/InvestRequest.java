package com.pilsa.invest.biz.vo.request;

import com.pilsa.invest.common.web.vo.request.CommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * The type Invest request.
 *
 * @author pilsa_home1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="[투자하기] - 요청", description="[투자하기] API Request 입니다.")
public class InvestRequest extends CommonRequest {

    @NotBlank
    @Length(min = 1 , max = 10)
    @ApiModelProperty(value="상품ID", example = "PRD0000010", required = true)
    private String productId;

    @Min(10000)
    @Max(100000000)
    @ApiModelProperty(value="투자금액", example = "1500000", required = true)
    private int investAmount;


}
