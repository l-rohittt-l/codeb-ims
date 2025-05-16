package com.codeb.ims.service;

import com.codeb.ims.dto.BrandDto;

import java.util.List;

public interface BrandService {

    BrandDto createBrand(BrandDto brandDto);

    BrandDto updateBrand(Long brandId, BrandDto brandDto);

    void deleteBrand(Long brandId);

    String reactivateBrand(Long brandId);

    List<BrandDto> getAllActiveBrands();

    List<BrandDto> getBrandsByChainId(Long chainId);

    List<BrandDto> getBrandsByGroupName(String groupName);

    List<BrandDto> getAllBrandsSortedByActive();

    // ✅ New for dropdown
    List<String> getBrandNamesByChainId(Long chainId);
}
