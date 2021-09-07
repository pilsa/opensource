package com.pilsa.invest.biz.service.mapper;

import com.pilsa.invest.biz.service.dto.InvestProductDTO;
import com.pilsa.invest.biz.service.dto.InvestCondition;
import com.pilsa.invest.biz.service.dto.MemberInvestInfo;
import com.pilsa.invest.biz.service.dto.ProductDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * The interface Invest product mapper.
 *
 * @author pilsa_home1
 */
@Mapper
@Repository
public interface ProductValidityMapper {

    /**
     * Gets invest products.
     * 투자상품 유효성 검증을 위해 단건을 조회합니다.
     *
     * @param condition the condition
     * @return the invest products
     */
    InvestProductDTO selectInvestProduct(InvestCondition condition);

    /**
     * Select real sum amt member invest info.
     * 회원의 유효거래 중 부동산 관련 상품 투자금액 총 합계를 조회합니다.
     *
     * @param condition the condition
     * @return the member invest info
     */
    MemberInvestInfo selectRealSumAmt(InvestCondition condition);

    /**
     * Select invest product cnt member invest info.
     * 회원의 특정 상품 정상거래 투자건수를 조회합니다.
     *
     * @param condition the condition
     * @return the member invest info
     */
    MemberInvestInfo selectInvestProductCnt(InvestCondition condition);


    /**
     * Select invest total sum amt member invest info.
     * 회원의 유효거래 중 투자금액 총 합계를 조회합니다.
     *
     * @param condition the condition
     * @return the member invest info
     */
    MemberInvestInfo selectInvestTotalSumAmt(InvestCondition condition);


    /**
     * Select for update product detail product detail dto.
     * 현재모집금액을 변경하기 위해서 Record Lock을 수행합니다.
     *
     * @param condition the condition
     * @return the product detail dto
     */
    ProductDetailDTO selectForUpdateProductDetail(InvestCondition condition);



}
