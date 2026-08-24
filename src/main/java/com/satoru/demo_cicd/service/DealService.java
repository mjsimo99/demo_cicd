package com.satoru.demo_cicd.service;

import com.satoru.demo_cicd.model.dto.DealDTO;
import com.satoru.demo_cicd.model.dto.responseDto.DealRespDTO;

import java.util.List;

public interface DealService {
    List<DealRespDTO> getAllDeals();

    DealRespDTO getDealById(Long dealId);

    DealDTO importDeal(DealDTO dealDTO);

    DealDTO updateDeal(Long dealId, DealDTO dealDTO);

    void deleteDeal(Long dealId);
}

