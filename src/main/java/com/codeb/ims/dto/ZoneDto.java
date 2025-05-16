package com.codeb.ims.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ZoneDto {

    @NotBlank(message = "Zone name is required")
    @Size(max = 50, message = "Zone name must not exceed 50 characters")
    private String zoneName;

    @NotNull(message = "Brand ID is required")
    private Long brandId;

    // Constructors
    public ZoneDto() {}

    public ZoneDto(String zoneName, Long brandId) {
        this.zoneName = zoneName;
        this.brandId = brandId;
    }

    // Getters and Setters
    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
