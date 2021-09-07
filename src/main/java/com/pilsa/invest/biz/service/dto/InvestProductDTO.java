package com.pilsa.invest.biz.service.dto;

import com.pilsa.invest.common.code.ProductStatusCode;
import com.pilsa.invest.common.code.ProductTypeCode;
import lombok.*;

import java.time.LocalDateTime;

/**
 * The type Invest product dto.
 *
 * @author pilsa_home1
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvestProductDTO {

    private String productId;                   /* 상품ID */
    private ProductTypeCode productType;        /* 상품구분코드 */
    private String productName;                 /* 상품이름 */
    private String productDsc;                  /* 상품설명 */
    private double intRate;                     /* 적용이율 */
    private long totalInvestAmount;             /* 총모집금액 */
    private LocalDateTime startedAt;            /* 투자시작일시 */
    private LocalDateTime finishedAt;           /* 투자종료일시 */
    private String allianceCode;                /* 투자연계금융기관코드 */
    private int investPeriod;                   /* 투자기간 (월단위)*/
    private ProductStatusCode productStatus;    /* 상품상태코드 */
    private long currentInvestAmount;           /* 현재모집금액 */
    private int currentInvestCnt;               /* 현재투자자수 */

    private LocalDateTime frstRgsAt;            /* 최초등록일시 */
    private String frstRgsId;                   /* 최초등록자ID */
    private LocalDateTime lstChngAt;            /* 최종변경일시 */
    private String lstChngId;                   /* 최종변경자ID */

}
