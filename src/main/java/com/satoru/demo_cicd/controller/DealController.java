package com.satoru.demo_cicd.controller;

import com.satoru.demo_cicd.model.dto.DealDTO;
import com.satoru.demo_cicd.model.dto.responseDto.DealRespDTO;
import com.satoru.demo_cicd.service.DealService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deals")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class DealController {

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @GetMapping
    public ResponseEntity<List<DealRespDTO>> getAllDeals() {
        List<DealRespDTO> deals = dealService.getAllDeals();
        return ResponseEntity.ok(deals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealRespDTO> getDealById(
            @PathVariable("id") @Positive(message = "Deal ID must be a positive number") Long dealId) {
        DealRespDTO dealDTO = dealService.getDealById(dealId);
        return ResponseEntity.ok(dealDTO);
    }

    @PostMapping("/import")
    public ResponseEntity<DealRespDTO> importDeal(@Valid @RequestBody DealDTO dealDTO) {
        DealRespDTO importedDeal = dealService.importDeal(dealDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(importedDeal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DealRespDTO> updateDeal(
            @PathVariable("id") @Positive(message = "Deal ID must be a positive number") Long dealId,
            @Valid @RequestBody DealDTO dealDTO) {
        DealRespDTO updatedDeal = dealService.updateDeal(dealId, dealDTO);
        return ResponseEntity.ok(updatedDeal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeal(
            @PathVariable("id") @Positive(message = "Deal ID must be a positive number") Long dealId) {
        dealService.deleteDeal(dealId);
        return ResponseEntity.noContent().build();
    }
}

