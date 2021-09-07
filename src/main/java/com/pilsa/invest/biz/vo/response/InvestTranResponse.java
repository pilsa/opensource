package com.pilsa.invest.biz.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Invest tran response.
 *
 * @author pilsa_home1
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiModel(value="[나의 투자상품 조회] - 응답", description="[나의 투자상품 조회] API Response 입니다.")
public class InvestTranResponse {

    @ApiModelProperty(value="투자상품목록수", example = "")
    private int investProductCnt;

    @ApiModelProperty(value="투자상품목록", example = "")
    private List<InvestTransaction> investTransactionList;


    /**
     * The type Invest transaction.
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class InvestTransaction{

        @ApiModelProperty(value="상품ID")
        private String productId;
        @ApiModelProperty(value="상품이름")
        private String productName;
        @ApiModelProperty(value="총모집금액")
        private long totalInvestAmt;
        @ApiModelProperty(value="현재모집금액")
        private long crntInvestAmt;
        @ApiModelProperty(value="상품상태")
        private String productStatus;
        @ApiModelProperty(value="나의투자금액")
        private int myInvestAmt;
        @ApiModelProperty(value="투자일시")
        private String transAt;
    }

}
