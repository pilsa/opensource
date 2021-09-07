package com.pilsa.invest.biz.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Invest list response.
 *
 * @author pilsa_home1
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiModel(value="[전체 투자 상품 조회] - 응답", description="[전체 투자 상품 조회] API Response 입니다.")
public class InvestListResponse {

    @ApiModelProperty(value="투자상품목록수")
    private int investProductCnt;

    @ApiModelProperty(value="투자상품목록")
    private List<InvestProduct> investProductList;

    /**
     * The type Invest product.
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class InvestProduct{
        @ApiModelProperty(value="상품ID", example = "10010123456789")
        private String productId;
        @ApiModelProperty(value="상품구분코드", example = "01")
        private String productType;
        @ApiModelProperty(value="상품이름", example = "")
        private String productName;
        @ApiModelProperty(value="상품설명")
        private String productDsc;
        @ApiModelProperty(value="적용이율")
        private Double intRate;
        @ApiModelProperty(value="총모집금액")
        private Long totalInvestAmt;
        @ApiModelProperty(value="투자시작일시")
        private String startedAt;
        @ApiModelProperty(value="투자종료일시")
        private String finishedAt;
        @ApiModelProperty(value="투자연계금융기관코드")
        private String allianceCode;
        @ApiModelProperty(value="투자연계금융기관명")
        private String allianceNm;
        @ApiModelProperty(value="투자기간")
        private Integer investPeriod;
        @ApiModelProperty(value="상품상태")
        private String productStatus;
        @ApiModelProperty(value="상품상태코드")
        private String productStatusCode;
        @ApiModelProperty(value="현재모집금액")
        private Long crntInvestAmt;
        @ApiModelProperty(value="현재투자자수")
        private Integer crntInvestCnt;
    }
}
