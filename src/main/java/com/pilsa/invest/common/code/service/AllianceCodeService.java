package com.pilsa.invest.common.code.service;

import com.pilsa.invest.biz.service.InvestAllianceService;
import com.pilsa.invest.common.code.AllianceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Alliance code service.
 */
@Slf4j
@Service
public class AllianceCodeService {

    private final InvestAllianceService investAllianceService;

    /**
     * Instantiates a new Alliance code service.
     *
     * @param investAllianceService the invest alliance service
     */
    @Autowired
    public AllianceCodeService(
            InvestAllianceService investAllianceService
    ) {
        this.investAllianceService = investAllianceService;
    }

    /**
     * Get value by key string.
     * 투자연계금융기관 Cache 에서
     *
     * @param key the key
     * @return the string
     */
    public String getValueByKey(String key){
        return investAllianceService.getAllianceCodesFromCache().stream()
                .filter(allianceList -> allianceList.getKey().equals(key))
                .findAny()
                .map(AllianceCode::getValue).orElse("");
    }


    /**
     * Get alliance code by key alliance code.
     *
     * @param key the key
     * @return the alliance code
     */
    public AllianceCode getAllianceCodeByKey(String key){
        return investAllianceService.getAllianceCodesFromCache().stream()
                .filter(allianceList -> allianceList.getKey().equals(key))
                .findAny()
                .map(alliance -> AllianceCode.builder()
                        .key(alliance.getKey())
                        .value(alliance.getValue())
                        .onlineInvestLawYn(alliance.getOnlineInvestLawYn())
                        .totalLimitAmount(alliance.getTotalLimitAmount())
                        .onecLimitAmount(alliance.getOnecLimitAmount())
                        .realtyLimitAmount(alliance.getRealtyLimitAmount())
                        .creditLimitAmount(alliance.getCreditLimitAmount())
                        .build())
                .orElse(null);
    }
}
