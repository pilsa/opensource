package com.pilsa.invest.biz.service.dto;

import com.pilsa.invest.common.code.ProductStatusCode;
import lombok.*;

import java.time.LocalDateTime;

/**
 * The type Invest transaction dto.
 *
 * @author pilsa_home1
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvestTransactionDTO {

    private String productId;                   /* 상품ID */
    private String productName;                 /* 상품이름 */
    private long totalInvestAmount;             /* 총모집금액 */
    private long currentInvestAmount;           /* 현재모집금액 */
    private ProductStatusCode productStatus;    /* 상품상태코드 */
    private int myInvestAmount;                 /* 나의투자금액 */
    private LocalDateTime transAt;              /* 거래일시 */

}
