package com.pilsa.invest.biz.service;

import com.pilsa.invest.biz.service.dto.InvestCondition;
import com.pilsa.invest.biz.service.dto.InvestProductDTO;
import com.pilsa.invest.biz.service.dto.MemberInvestInfo;
import com.pilsa.invest.biz.service.dto.ProductDetailDTO;
import com.pilsa.invest.biz.service.mapper.InvestAllianceMapper;
import com.pilsa.invest.biz.service.mapper.InvestProductMapper;
import com.pilsa.invest.biz.service.mapper.ProductValidityMapper;
import com.pilsa.invest.biz.vo.response.InvestResponse;
import com.pilsa.invest.common.code.*;
import com.pilsa.invest.common.code.service.AllianceCodeService;
import com.pilsa.invest.framework.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * The type Invest product service.
 *
 * @author pilsa_home1
 */
@Slf4j
@Service
public class ProductValidityServiceImpl implements ProductValidityService {

    private final ProductValidityMapper productValidityMapper;
    private final AllianceCodeService allianceCodeService;
    private final InvestAllianceMapper investAllianceMapper;
    private final InvestProductMapper investProductMapper;


    public ProductValidityServiceImpl(
            ProductValidityMapper productValidityMapper
            , AllianceCodeService allianceCodeService
            , InvestAllianceMapper investAllianceMapper
            , InvestProductMapper investProductMapper
    ) {
        this.productValidityMapper = productValidityMapper;
        this.allianceCodeService = allianceCodeService;
        this.investAllianceMapper = investAllianceMapper;
        this.investProductMapper = investProductMapper;
    }

    @Override
    public InvestResponse productValidity(InvestCondition request) {
        /** ======================================================================================
         * 1) 투자상품 검증 : 존재하지 않음
        ======================================================================================= */
        InvestProductDTO product = productValidityMapper.selectInvestProduct(request);
        if (null == product) {
            throw new ServiceException(ResponseCode.NOT_FOUND_RESOURCE);
        }

        /** ======================================================================================
         * 2) 투자 모집시간 검증
        ======================================================================================= */
        LocalDateTime currentAt = LocalDateTime.now();
        // 2-1) 현재일시 보다 모집시작일시가 미래이면
        if (currentAt.isBefore(product.getStartedAt())){
            //투자모집이 시작되지 않은 상품입니다. 현재일시 보다 모집시작일시가 미래입니다.
            throw new ServiceException(ResponseCode.PRODUCT_NOT_READY,request.getProductId());
        }
        // 2-2) 현재일시 보다 모집종료일시가 과거이면 상품 상태변경 처리
        if (currentAt.isAfter(product.getFinishedAt())){
            investProductMapper.updateProductDetail(ProductDetailDTO.builder()
                    .productId(request.getProductId())
                    .productStatus(ProductStatusCode.RECRUIT_END)
                    .lstChngId(String.valueOf(request.getMemberNum()))
                    .build());
            // 투자모집이 종료된 상품입니다. 현재일시 보다 모집종료일시가 과거입니다.
            throw new ServiceException(ResponseCode.PRODUCT_END,request.getProductId());
        }

        /** ======================================================================================
         * 투자연계금융기관정보 조회 (Cache 조회)
        ======================================================================================= */
        AllianceCode allianceCode = allianceCodeService.getAllianceCodeByKey(product.getAllianceCode());

        /** ======================================================================================
         * 3) 1회투자 한도 검증 : 회원의 투자요청금액이 해당 제공사의 1회투자한도금액을 초과했는지 검증.
        ======================================================================================= */
        if (request.getInvestAmount() > allianceCode.getOnecLimitAmount()){
            // 요청하신 투자금액이 1회 투자한도금액을 초과하였습니다. 해당 제공사의 1회 투자한도금액은 {}입니다.
            throw new ServiceException(ResponseCode.ONEC_LIMIT_EXCESS,allianceCode.getOnecLimitAmount());
        }

        /** ======================================================================================
         * 4) 동일 투자 상품 검증 : 회원이 동일한 상품에 투자한 이력이 있는지 검증
        ======================================================================================= */
        MemberInvestInfo memberInvestInfo = productValidityMapper.selectInvestProductCnt(InvestCondition.builder()
                .memberNum(request.getMemberNum())
                .productId(request.getProductId())
                .build());
        if (memberInvestInfo.getInvestPrdCnt() > 0){
            //이미 투자중인 상품입니다. 투자금 변경을 원하는 경우, 기존 건을 취소하고 진행해주세요.
            throw new ServiceException(ResponseCode.ALREADY_INVESTED,allianceCode.getOnecLimitAmount());
        }

        /** ======================================================================================
         * 5) 부동산투자 한도 검증 : 투자요청한 상품이 부동산(담보/PF)일 경우 해당 제공사의 부동산투자한도금액을 초과했는지 검증.
        ======================================================================================= */
        if (product.getProductType().equals(ProductTypeCode.REALTY) ||
            product.getProductType().equals(ProductTypeCode.REALTY_PF)){

            // 투자요청금액 + 누적 부동산투자총합을 더한다
            int totalRealSumAmt = Math.addExact(request.getInvestAmount(),
                    productValidityMapper.selectRealSumAmt(InvestCondition.builder()
                            .memberNum(request.getMemberNum())
                            .build()).getRealSumAmt());

            // 회원의 (투자요청금액 + 누적 부동산투자총합)과 제공사별 부동산투자한도금액을 비교
            if (totalRealSumAmt > allianceCode.getRealtyLimitAmount()){
                //부동산(담보/PF) 상품에 투자하신 누적금액이 해당 제공사의 부동산 투자한도 금액 [%s]을 초과하였습니다.
                throw new ServiceException(ResponseCode.REALTY_LIMIT_EXCESS,allianceCode.getRealtyLimitAmount());
            }
        }

        /** ======================================================================================
         * 6) 제공사 별 최대 한도 검증 : (회원의 제공사 별 투자금액 총 합계) 가 (해당 제공사의 전체한도금액) 을 초과했는지 검증.
         ======================================================================================= */
        // 투자요청금액 + 제공사 별 투자금액총합을 더한다.
        int totalSumAmt = Math.addExact(request.getInvestAmount(),
                productValidityMapper.selectInvestTotalSumAmt(InvestCondition.builder()
                        .memberNum(request.getMemberNum())
                        .build()).getRealSumAmt());

        if (totalSumAmt > allianceCode.getTotalLimitAmount()){
            if (allianceCode.getOnlineInvestLawYn().equals(YesNoCode.Y)){
                // 투자하신 누적금액이 해당 제공사의 업권 통합 최대 투자한도 금액 [%s]을 초과하였습니다.
                throw new ServiceException(ResponseCode.REALTY_LIMIT_EXCESS,allianceCode.getTotalLimitAmount());
            } else {
                // 투자하신 누적금액이 해당 제공사 별 최대 투자한도 금액 [%s]을 초과하였습니다.
                throw new ServiceException(ResponseCode.BUSINESS_LIMIT_EXCESS,allianceCode.getTotalLimitAmount());
            }
        }

        /** ======================================================================================
         * 7) 최종으로 (투자요청금액 + 상품의 현재모집금액) 가 (상품의 총모집금액)을 초과하였는지 검증.
         * 투자상품 상세 (INVEST_PRODUCT_TF) Row-Level Record Locking.
        ======================================================================================= */
        ProductDetailDTO productDetail = productValidityMapper.selectForUpdateProductDetail(InvestCondition.builder()
                .productId(request.getProductId())
                .build());

        // (투자요청금액 + 상품의 현재모집금액)
        long currentSumAmt = Math.addExact(Long.valueOf(request.getInvestAmount()),productDetail.getCurrentInvestAmount());
        // 상품 총모집금액
        long totalInvestAmt = product.getTotalInvestAmount();
        // 상품 총모집금액 - (사용자가 투자하려고 하는 금액 + 현재모집금액)
        long differenceAmt = Math.subtractExact(totalInvestAmt,currentSumAmt);

        if (differenceAmt<0L){
            throw new ServiceException(ResponseCode.RECRUIT_LIMIT_EXCESS,Math.abs(differenceAmt));
        } else if ( differenceAmt == 0L ) {
            return InvestResponse.builder()
                    .productStatus(ProductStatusCode.RECRUIT_END.getValue())
                    .productStatusCode(ProductStatusCode.RECRUIT_END.getKey())
                    .build();
        }
        return InvestResponse.builder()
                .productStatus(ProductStatusCode.RECRUIT_ING.getValue())
                .productStatusCode(ProductStatusCode.RECRUIT_ING.getKey())
                .build();
    }

}
