package com.pilsa.invest.biz.service.mapper;

import com.pilsa.invest.biz.service.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Invest product mapper.
 *
 * @author pilsa_home1
 */
@Mapper
@Repository
public interface InvestProductMapper {

    /**
     * Gets invest products.
     * 투자상품 목록을 조회합니다.
     *
     * @param condition the condition
     * @return the invest products
     */
    List<InvestProductDTO> selectInvestProducts(InvestCondition condition);

    /**
     * Update product detail.
     * 투자상품 상세 업데이트합니다.
     *
     * @param productDetailDTO the product detail dto
     */
    void updateProductDetail(ProductDetailDTO productDetailDTO);


    /**
     * Insert product history.
     * 투자상품 이력을 등록합니다.
     *
     * @param productHistoryDTO the product history dto
     */
    void insertProductHistory(ProductHistoryDTO productHistoryDTO);


    /**
     * Select my invest transactions list.
     * 나의 투자상품을 조회합니다.
     *
     * @param condition the condition
     * @return the list
     */
    List<InvestTransactionDTO> selectMyInvestTransactions(InvestCondition condition);
}
