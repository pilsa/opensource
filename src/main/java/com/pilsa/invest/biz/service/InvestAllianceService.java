package com.pilsa.invest.biz.service;

import com.pilsa.invest.common.code.AllianceCode;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The interface Invest alliance service.
 * 투자연계금융기관 정보를 서비스 합니다.
 *
 * @author pilsa_home1
 */
public interface InvestAllianceService {

    /**
     * Gets alliance codes from cache.
     * 투자연계금융기관 정보를 LocalCache에서 가져옵니다.
     *
     * @return the alliance codes from cache
     */
    @Transactional
    List<AllianceCode> getAllianceCodesFromCache();
}
