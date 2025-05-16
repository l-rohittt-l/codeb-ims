package com.codeb.ims.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class EstimateRequestDTO {

    @NotNull(message = "Chain ID is required")
    private Long chainId;

    @NotBlank(message = "Group name is required")
    private String groupName;

    @NotBlank(message = "Brand name is required")
    private String brandName;

    @NotBlank(message = "Zone name is required")
    private String zoneName;

    @NotBlank(message = "Service is required")
    private String service;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int qty;

    @DecimalMin(value = "0.0", inclusive = false, message = "Cost per unit must be greater than 0")
    private float costPerUnit;

    private float totalCost;

    private LocalDate deliveryDate;

    private String deliveryDetails;

    // Getters and Setters

    public Long getChainId() {
        return chainId;
    }

    public void setChainId(Long chainId) {
        this.chainId = chainId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(float costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(String deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }
}
