package com.satoru.demo_cicd.controller;

import com.satoru.demo_cicd.model.dto.DealDTO;
import com.satoru.demo_cicd.model.dto.responseDto.DealRespDTO;
import com.satoru.demo_cicd.service.DealService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deals")
@CrossOrigin(origins = "http://localhost:4200")
public class DealController {

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @GetMapping
    public ResponseEntity<List<DealRespDTO>> getAllDeals() {
        List<DealRespDTO> deals = dealService.getAllDeals();
        return new ResponseEntity<>(deals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealRespDTO> getDealById(@PathVariable("id") Long dealId) {
        DealRespDTO dealDTO = dealService.getDealById(dealId);
        return new ResponseEntity<>(dealDTO, HttpStatus.OK);
    }

    @PostMapping("/import")
    public ResponseEntity<DealDTO> importDeal(@Valid @RequestBody DealDTO dealDTO) {
        DealDTO importedDealDTO = dealService.importDeal(dealDTO);
        return new ResponseEntity<>(importedDealDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DealDTO> updateDeal(@PathVariable("id") Long dealId, @Valid @RequestBody DealDTO dealDTO) {
        DealDTO updatedDealDTO = dealService.updateDeal(dealId, dealDTO);
        return new ResponseEntity<>(updatedDealDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeal(@PathVariable("id") Long dealId) {
        dealService.deleteDeal(dealId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

