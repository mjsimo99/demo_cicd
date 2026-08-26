package com.satoru.demo_cicd.service.impl;

import com.satoru.demo_cicd.exception.DealAlreadyExistsException;
import com.satoru.demo_cicd.exception.ResourceNotFoundException;
import com.satoru.demo_cicd.model.dto.DealDTO;
import com.satoru.demo_cicd.model.dto.responseDto.DealRespDTO;
import com.satoru.demo_cicd.model.entity.Deal;
import com.satoru.demo_cicd.repository.DealRepository;
import com.satoru.demo_cicd.service.DealService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final ModelMapper modelMapper;

    public DealServiceImpl(DealRepository dealRepository, ModelMapper modelMapper) {
        this.dealRepository = dealRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DealRespDTO> getAllDeals() {
        try {
            List<Deal> deals = dealRepository.findAll();
            return deals.stream()
                    .map(deal -> modelMapper.map(deal, DealRespDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all deals: " + e.getMessage());
        }
    }

    @Override
    public DealRespDTO getDealById(Long dealId) {
        try {
            Deal deal = dealRepository.findById(dealId)
                    .orElseThrow(() -> new ResourceNotFoundException("Deal not found with ID: " + dealId));
            return modelMapper.map(deal, DealRespDTO.class);
        } catch (Exception e) {
            if (e instanceof ResourceNotFoundException) {
                throw e;
            }
            throw new RuntimeException("Failed to fetch deal with ID " + dealId + ": " + e.getMessage());
        }
    }

    @Override
    public DealRespDTO importDeal(DealDTO dealDTO) {
        try {
            if (dealRepository.existsByDealUniqueId(dealDTO.getDealUniqueId())) {
                throw new DealAlreadyExistsException("Deal with unique ID '" + dealDTO.getDealUniqueId() + "' already exists");
            }
            Deal deal = modelMapper.map(dealDTO, Deal.class);
            deal.setId(null);
            deal = dealRepository.save(deal);
            return modelMapper.map(deal, DealRespDTO.class);
        } catch (Exception e) {
            if (e instanceof DealAlreadyExistsException) {
                throw e;
            }
            throw new RuntimeException("Failed to import deal: " + e.getMessage());
        }
    }

    @Override
    public DealRespDTO updateDeal(Long dealId, DealDTO dealDTO) {
        try {
            Deal existingDeal = dealRepository.findById(dealId)
                    .orElseThrow(() -> new ResourceNotFoundException("Deal not found with ID: " + dealId));

            if (!existingDeal.getDealUniqueId().equals(dealDTO.getDealUniqueId()) &&
                    dealRepository.existsByDealUniqueId(dealDTO.getDealUniqueId())) {
                throw new DealAlreadyExistsException("Deal with unique ID '" + dealDTO.getDealUniqueId() + "' already exists");
            }

            modelMapper.map(dealDTO, existingDeal);
            existingDeal.setId(dealId);
            existingDeal = dealRepository.save(existingDeal);
            return modelMapper.map(existingDeal, DealRespDTO.class);
        } catch (Exception e) {
            if (e instanceof ResourceNotFoundException || e instanceof DealAlreadyExistsException) {
                throw e;
            }
            throw new RuntimeException("Failed to update deal with ID " + dealId + ": " + e.getMessage());
        }
    }

    @Override
    public void deleteDeal(Long dealId) {
        try {
            if (!dealRepository.existsById(dealId)) {
                throw new ResourceNotFoundException("Deal not found with ID: " + dealId);
            }
            dealRepository.deleteById(dealId);
        } catch (Exception e) {
            if (e instanceof ResourceNotFoundException) {
                throw e;
            }
            throw new RuntimeException("Failed to delete deal with ID " + dealId + ": " + e.getMessage());
        }
    }
}

