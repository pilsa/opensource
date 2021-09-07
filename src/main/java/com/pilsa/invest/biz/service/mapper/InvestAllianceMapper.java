package com.pilsa.invest.biz.service.mapper;

import com.pilsa.invest.biz.service.dto.InvestAllianceDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Invest product mapper.
 *
 * @author pilsa_home1
 */
@Mapper
@Repository
public interface InvestAllianceMapper {

    /**
     * Select invest alliance invest alliance dto.
     * 활성화 된 투자연계금융기관 정보를 조회합니다.
     *
     * @return the invest alliance dto
     */
    List<InvestAllianceDTO> selectInvestAlliance();
}
