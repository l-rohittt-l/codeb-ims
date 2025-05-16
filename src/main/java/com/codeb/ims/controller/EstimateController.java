package com.codeb.ims.controller;

import com.codeb.ims.dto.EstimateRequestDTO;
import com.codeb.ims.dto.EstimateResponseDTO;
import com.codeb.ims.service.EstimateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estimates")
@CrossOrigin(origins = "*")
public class EstimateController {

    @Autowired
    private EstimateService estimateService;

    // ✅ Get all estimates (active + inactive)
    @GetMapping
    public ResponseEntity<List<EstimateResponseDTO>> getAllEstimates() {
        List<EstimateResponseDTO> estimates = estimateService.getAllEstimates();
        return ResponseEntity.ok(estimates);
    }

    // ✅ Create a new estimate
    @PostMapping
    public ResponseEntity<EstimateResponseDTO> createEstimate(@Valid @RequestBody EstimateRequestDTO dto) {
        EstimateResponseDTO created = estimateService.createEstimate(dto);
        return ResponseEntity.ok(created);
    }

    // ✅ Update an existing estimate
    @PutMapping("/{id}")
    public ResponseEntity<EstimateResponseDTO> updateEstimate(
            @PathVariable Long id,
            @Valid @RequestBody EstimateRequestDTO dto) {
        EstimateResponseDTO updated = estimateService.updateEstimate(id, dto);
        return ResponseEntity.ok(updated);
    }

    // ✅ Soft delete estimate (set isActive = false)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDeleteEstimate(@PathVariable Long id) {
        estimateService.softDeleteEstimate(id);
        return ResponseEntity.ok("Estimate soft-deleted successfully");
    }

    // ✅ Reactivate an inactive estimate (set isActive = true)
    @PutMapping("/{id}/reactivate")
    public ResponseEntity<String> reactivateEstimate(@PathVariable Long id) {
        estimateService.reactivateEstimate(id);
        return ResponseEntity.ok("Estimate reactivated successfully");
    }

    // ✅ Filter estimates by brand
    @GetMapping("/filter/brand/{brandName}")
    public ResponseEntity<List<EstimateResponseDTO>> getEstimatesByBrand(@PathVariable String brandName) {
        List<EstimateResponseDTO> filtered = estimateService.getEstimatesByBrand(brandName);
        return ResponseEntity.ok(filtered);
    }

    // ✅ Filter estimates by group
    @GetMapping("/filter/group/{groupName}")
    public ResponseEntity<List<EstimateResponseDTO>> getEstimatesByGroup(@PathVariable String groupName) {
        List<EstimateResponseDTO> filtered = estimateService.getEstimatesByGroup(groupName);
        return ResponseEntity.ok(filtered);
    }
}
