package com.pilsa.invest.biz.vo.request;

import com.pilsa.invest.common.web.vo.request.CommonRequest;
import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * The type Invest tran request.
 *
 * @author pilsa_home1
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="[나의 투자상품 조회] - 요청", description="[나의 투자상품 조회] API Request 입니다.")
public class InvestTranRequest extends CommonRequest {

}
