package com.pilsa.invest.biz.service;

import com.pilsa.invest.biz.service.mapper.InvestAllianceMapper;
import com.pilsa.invest.common.code.AllianceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * The type Invest alliance service.
 */
@Slf4j
@Service
public class InvestAllianceServiceImpl implements InvestAllianceService {
    private final InvestAllianceMapper investAllianceMapper;


    /**
     * Instantiates a new Invest alliance service.
     *
     * @param investAllianceMapper the invest alliance mapper
     */
    public InvestAllianceServiceImpl(
            InvestAllianceMapper investAllianceMapper
    ) {
        this.investAllianceMapper = investAllianceMapper;
    }

    @Override
    @Cacheable(cacheNames = "allianceCache", key="'allAlliance'")
    public List<AllianceCode> getAllianceCodesFromCache() {
        return investAllianceMapper.selectInvestAlliance().stream()
                .map(alliance -> AllianceCode.builder()
                        .key(alliance.getAllianceCode())
                        .value(alliance.getAllianceNm())
                        .onlineInvestLawYn(alliance.getOnlineInvestLawYn())
                        .totalLimitAmount(alliance.getTotalLimitAmount())
                        .onecLimitAmount(alliance.getOnecLimitAmount())
                        .realtyLimitAmount(alliance.getRealtyLimitAmount())
                        .creditLimitAmount(alliance.getCreditLimitAmount())
                        .build())
                .collect(Collectors.toList());
    }

}
