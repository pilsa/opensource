package com.pilsa.invest.biz.service;

import com.pilsa.invest.biz.service.dto.InvestCondition;
import com.pilsa.invest.biz.vo.response.InvestResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface Product validity service.
 * 상품에 대한 유효성 검증을 위한 서비스를 제공합니다.
 *
 * @author pilsa_home1
 */
public interface ProductValidityService {

    /**
     * Product validity invest response.
     *
     * @param request the request
     * @return the invest response
     */
    @Transactional
    InvestResponse productValidity(InvestCondition request);


}
