package com.pilsa.invest.biz.controller;

import com.pilsa.invest.biz.service.InvestProductService;
import com.pilsa.invest.biz.service.ProductValidityService;
import com.pilsa.invest.biz.vo.request.InvestListRequest;
import com.pilsa.invest.biz.vo.request.InvestRequest;
import com.pilsa.invest.biz.vo.request.InvestTranRequest;
import com.pilsa.invest.biz.vo.response.InvestListResponse;
import com.pilsa.invest.biz.vo.response.InvestResponse;
import com.pilsa.invest.biz.vo.response.InvestTranResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Invest api controller.
 * 온라인 연계 투자 서비스 RestAPI Controller.
 */
@RestController
@RequestMapping("/v1/invest")
@Tags(@Tag(name = "InvestProduct", description = "투자상품 API"))
public class InvestApiController {

    private final InvestProductService investProductService;
    private final ProductValidityService productValidityService;


    /**
     * Instantiates a new Invest api controller.
     *
     * @param investProductService   the invest product service
     * @param productValidityService the product validity service
     */
    @Autowired
    public InvestApiController(
            InvestProductService investProductService
            , ProductValidityService productValidityService) {
        this.investProductService = investProductService;
        this.productValidityService = productValidityService;
    }

    /**
     * Gets invest product list.
     *
     * @param request the request
     * @return the invest product list
     */
    @PostMapping("/products")
    @ApiOperation(
            value = "전체 투자 상품 조회 API", tags = "InvestProduct",
            notes = "<b>※ sortOption 정렬순서 파라미터 설명</b>" +
                    "<li>INCOME : 높은 수익률 순서</li>" +
                    "<li>PERIOD : 짧은 기간 순서</li>" +
                    "<li>CLOSING : 마감 임박 순서</li>")
    public InvestListResponse getInvestProductList(@RequestBody InvestListRequest request) {
        return investProductService.getInvestProducts(request);
    }

    /**
     * Invest in products invest response.
     *
     * @param request the request
     * @return the invest response
     */
    @PostMapping("")
    @ApiOperation(
            value = "투자하기 API", tags = "InvestProduct",
            notes = "사용자 식별값, 상품ID, 투자금액을 투입하여 투자를 수행 합니다.")
    public InvestResponse investInProducts(@RequestBody InvestRequest request) {
        return investProductService.investInProducts(request);
    }

    /**
     * Gets my invest transactions.
     *
     * @param request the request
     * @return the my invest transactions
     */
    @PostMapping("/transactions")
    @ApiOperation(
            value = "나의 투자상품 조회 API", tags = "InvestProduct",
            notes = "사용자가 투자한 모든 상품을 반환합니다.")
    public InvestTranResponse getMyInvestTransactions(@RequestBody InvestTranRequest request) {
        return investProductService.getMyInvestTransactions(request);
    }


}
