package com.pilsa.invest.biz.service;

import com.pilsa.invest.biz.service.mapper.InvestProductMapper;
import com.pilsa.invest.biz.vo.request.InvestListRequest;
import com.pilsa.invest.biz.vo.request.InvestRequest;
import com.pilsa.invest.biz.vo.request.InvestTranRequest;
import com.pilsa.invest.biz.vo.response.InvestListResponse;
import com.pilsa.invest.biz.vo.response.InvestResponse;
import com.pilsa.invest.biz.vo.response.InvestTranResponse;
import com.pilsa.invest.common.code.ProductStatusCode;
import com.pilsa.invest.common.code.SortOptionCode;
import com.pilsa.invest.common.code.TransactionTypeCode;
import com.pilsa.invest.common.code.service.AllianceCodeService;
import com.pilsa.invest.framework.util.DateUtil;
import com.pilsa.invest.biz.service.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Invest product service.
 *
 * @author pilsa_home1
 */
@Validated
@Slf4j
@Service
public class InvestProductServiceImpl implements InvestProductService {

    @Value("${spring.profiles.active}")
    private String profileActive;

    private final InvestProductMapper investProductMapper;
    private final ProductValidityService productValidityService;
    private final AllianceCodeService allianceCodeService;

    /**
     * Instantiates a new Invest product service.
     *
     * @param investProductMapper    the invest product mapper
     * @param productValidityService the product validity service
     * @param allianceCodeService    the alliance code service
     */
    public InvestProductServiceImpl(
            InvestProductMapper investProductMapper
            , ProductValidityService productValidityService
            , AllianceCodeService allianceCodeService
    ) {
        this.investProductMapper = investProductMapper;
        this.productValidityService = productValidityService;
        this.allianceCodeService = allianceCodeService;
    }

    @Override
    public InvestListResponse getInvestProducts(InvestListRequest request) {
        /*======================================================================================
         * 1) 투자상품 목록 조회 조건 : 잘못된 조회조건이 들어왔을경우 기본값으로 셋팅.
        ======================================================================================*/
        InvestCondition condition = InvestCondition.builder()
                        .sortOptionCode(EnumSet.allOf(SortOptionCode.class).stream()
                                .filter(e -> e.getKey().equals(request.getSortOption()))
                                .findAny()
                                .orElse(SortOptionCode.INCOME)).build();

        /*======================================================================================
         * 2) 투자상품 목록 조회
        ======================================================================================*/
        List<InvestProductDTO> investProductList = investProductMapper.selectInvestProducts(condition);

        /*======================================================================================
         * 3) 투자 모집기간안에 있는 상품중에 상품상태코드가 INITIAL(초기상태)의 경우 상태변경 처리.
        ======================================================================================*/
        for (InvestProductDTO investProduct : investProductList){
            if (ProductStatusCode.INITIAL.equals(investProduct.getProductStatus())){

                investProductMapper.updateProductDetail(ProductDetailDTO.builder()
                        .productId(investProduct.getProductId())
                        .productStatus(ProductStatusCode.RECRUIT_ING)
                        .lstChngId(String.valueOf(request.getMemberNum()))
                        .build());
            }
        }
        /*======================================================================================
         * 4) 투자상품 목록 반환
        ======================================================================================*/
        return InvestListResponse.builder()
                .investProductCnt(investProductList.size())
                .investProductList(investProductList.stream()
                        .map(product -> InvestListResponse.InvestProduct.builder()
                                .productId(product.getProductId())
                                .productType(product.getProductType().getValue())
                                .productName(product.getProductName())
                                .productDsc(product.getProductDsc())
                                .intRate(product.getIntRate())
                                .totalInvestAmt(product.getTotalInvestAmount())
                                .startedAt(DateUtil.dateToString(product.getStartedAt()))
                                .finishedAt(DateUtil.dateToString(product.getFinishedAt()))
                                .allianceCode(product.getAllianceCode())
                                .allianceNm(allianceCodeService.getValueByKey(product.getAllianceCode()))
                                .investPeriod(product.getInvestPeriod())
                                .productStatus(product.getProductStatus().equals(ProductStatusCode.INITIAL)?
                                        ProductStatusCode.RECRUIT_ING.getValue():product.getProductStatus().getValue())
                                .crntInvestAmt(product.getCurrentInvestAmount())
                                .crntInvestCnt(product.getCurrentInvestCnt())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public InvestResponse investInProducts(InvestRequest request) {
        /*======================================================================================
         * 1) 투자 유효성 검증
        ======================================================================================*/
        InvestResponse response = productValidityService.productValidity(InvestCondition.builder()
                .memberNum(request.getMemberNum())
                .productId(request.getProductId())
                .investAmount(request.getInvestAmount())
                .build());

        /*======================================================================================
         * 2) 투자상품 상세 수정 : 투자실행 및 상품상태 변경
        ======================================================================================*/
        investProductMapper.updateProductDetail(ProductDetailDTO.builder()
                .productId(request.getProductId())
                .currentInvestAmount(request.getInvestAmount())
                .productStatus(ProductStatusCode.getCodeByString(response.getProductStatusCode()))
                .lstChngId(String.valueOf(request.getMemberNum()))
                .build());

        /*======================================================================================
         * 3) 투자상품 거래내역 등록
        ======================================================================================*/
        investProductMapper.insertProductHistory(ProductHistoryDTO.builder()
                .transactionId(request.getTransactionId())
                .memberNum(request.getMemberNum())
                .productId(request.getProductId())
                .transType(TransactionTypeCode.INVEST)
                .transAmount(request.getInvestAmount())
                .transAt(LocalDateTime.now())
                .frstRgsAt(LocalDateTime.now())
                .frstRgsId(String.valueOf(request.getMemberNum()))
                .lstChngAt(LocalDateTime.now())
                .lstChngId(String.valueOf(request.getMemberNum()))
                .build());

        return response;
    }

    @Override
    public InvestTranResponse getMyInvestTransactions(InvestTranRequest request) {
        /*======================================================================================
         * 나의 투자상품을 조회
        ======================================================================================*/
        List<InvestTransactionDTO> transactionList = investProductMapper.selectMyInvestTransactions(InvestCondition.builder()
                .memberNum(request.getMemberNum())
                .build());

        return InvestTranResponse.builder()
                .investProductCnt(transactionList.size())
                .investTransactionList(transactionList.stream()
                        .map(tran -> InvestTranResponse.InvestTransaction.builder()
                                .productId(tran.getProductId())
                                .productName(tran.getProductName())
                                .totalInvestAmt(tran.getTotalInvestAmount())
                                .crntInvestAmt(tran.getCurrentInvestAmount())
                                .productStatus(tran.getProductStatus().getValue())
                                .myInvestAmt(tran.getMyInvestAmount())
                                .transAt(DateUtil.dateToString(tran.getTransAt()))
                                .build())
                        .collect(Collectors.toList())).build();
    }
}
