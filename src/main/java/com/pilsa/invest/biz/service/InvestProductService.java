package com.pilsa.invest.biz.service;

import com.pilsa.invest.biz.vo.request.InvestListRequest;
import com.pilsa.invest.biz.vo.request.InvestRequest;
import com.pilsa.invest.biz.vo.request.InvestTranRequest;
import com.pilsa.invest.biz.vo.response.InvestListResponse;
import com.pilsa.invest.biz.vo.response.InvestResponse;
import com.pilsa.invest.biz.vo.response.InvestTranResponse;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

/**
 * The interface Invest product service.
 * 상품을 조회하거나 투자 서비스를 제공합니다.
 */
public interface InvestProductService {

    /**
     * Gets invest products.
     * 투자상품 목록을 조회합니다.
     *
     * @param request the request
     * @return the invest products
     */
    @Transactional
    InvestListResponse getInvestProducts(InvestListRequest request);

    /**
     * Invest in products invest response.
     * 상품에 대한 유효성 검증과 투자를 수행합니다.
     *
     * @param request the request
     * @return the invest response
     */
    @Transactional
    InvestResponse investInProducts(@Valid InvestRequest request);

    /**
     * Gets my invest transactions.
     * 회원이 투자한 상품에대한 내역을 조회합니다.
     *
     * @param request the request
     * @return the my invest transactions
     */
    @Transactional
    InvestTranResponse getMyInvestTransactions(InvestTranRequest request);
}
