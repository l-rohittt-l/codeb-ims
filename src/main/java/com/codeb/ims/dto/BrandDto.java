package com.codeb.ims.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BrandDto {

    private Long brandId;

    @NotBlank(message = "Brand name is required")
    private String brandName;

    @NotNull(message = "Chain ID is required")
    private Long chainId;

    private boolean isActive; // ✅ ADDED

    
    public BrandDto() {}

    public BrandDto(Long brandId, String brandName, Long chainId) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.chainId = chainId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getChainId() {
        return chainId;
    }

    public void setChainId(Long chainId) {
        this.chainId = chainId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
