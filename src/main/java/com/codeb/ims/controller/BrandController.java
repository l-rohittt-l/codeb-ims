package com.codeb.ims.controller;

import com.codeb.ims.dto.BrandDto;
import com.codeb.ims.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@CrossOrigin(origins = "*")
public class BrandController {

    @Autowired
    private BrandService brandService;

    // ✅ Create a brand
    @PostMapping
    public ResponseEntity<BrandDto> createBrand(@Valid @RequestBody BrandDto brandDto) {
        BrandDto created = brandService.createBrand(brandDto);
        return ResponseEntity.ok(created);
    }

    // ✅ Update a brand
    @PutMapping("/{id}")
    public ResponseEntity<BrandDto> updateBrand(@PathVariable Long id, @Valid @RequestBody BrandDto brandDto) {
        BrandDto updated = brandService.updateBrand(id, brandDto);
        return ResponseEntity.ok(updated);
    }

    // ✅ Soft delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok("Brand deleted (soft delete applied)");
    }

    // ✅ Reactivate
    @PutMapping("/{id}/reactivate")
    public ResponseEntity<String> reactivateBrand(@PathVariable Long id) {
        String result = brandService.reactivateBrand(id);
        if (!"success".equals(result)) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Brand reactivated successfully");
    }

    // ✅ Get all brands (sorted by active)
    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrandsSortedByActive());
    }

    // ✅ Get brands by chain ID
    @GetMapping("/by-chain/{chainId}")
    public ResponseEntity<List<BrandDto>> getBrandsByChain(@PathVariable Long chainId) {
        List<BrandDto> brands = brandService.getBrandsByChainId(chainId);
        return ResponseEntity.ok(brands);
    }

    // ✅ Get brands by group name
    @GetMapping("/by-group/{groupName}")
    public ResponseEntity<List<BrandDto>> getBrandsByGroup(@PathVariable String groupName) {
        List<BrandDto> brands = brandService.getBrandsByGroupName(groupName);
        return ResponseEntity.ok(brands);
    }

    // ✅ Get only brand names by chain ID (for dropdown)
    @GetMapping("/names/by-chain/{chainId}")
    public ResponseEntity<List<String>> getBrandNamesByChain(@PathVariable Long chainId) {
        return ResponseEntity.ok(brandService.getBrandNamesByChainId(chainId));
    }
}
