package com.pilsa.invest.biz.service.dto;

import com.pilsa.invest.common.code.ProductStatusCode;
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
public class ProductDetailDTO {

    private String productId;                   /* 상품ID */
    private ProductStatusCode productStatus;    /* 상품상태코드 */
    private long currentInvestAmount;           /* 현재모집금액 */
    private int currentInvestCnt;               /* 현재투자자수 */

    private LocalDateTime frstRgsAt;            /* 최초등록일시 */
    private String frstRgsId;                   /* 최초등록자ID */
    private LocalDateTime lstChngAt;            /* 최종변경일시 */
    private String lstChngId;                   /* 최종변경자ID */

}
