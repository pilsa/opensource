package com.pilsa.invest.biz.service.dto;

import com.pilsa.invest.common.code.TransactionTypeCode;
import lombok.*;

import java.time.LocalDateTime;

/**
 * The type Product history dto.
 *
 * @author pilsa_home1
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductHistoryDTO {

    private String transactionId;          /* 거래ID */
    private long memberNum;                 /* 회원번호 */
    private String productId;              /* 상품ID */
    private TransactionTypeCode transType; /* 거래유형코드 */
    private long transAmount;              /* 거래금액 */
    private LocalDateTime transAt;         /* 거래일시 */

    private LocalDateTime frstRgsAt;       /* 최초등록일시 */
    private String frstRgsId;              /* 최초등록자ID */
    private LocalDateTime lstChngAt;       /* 최종변경일시 */
    private String lstChngId;              /* 최종변경자ID */
}
