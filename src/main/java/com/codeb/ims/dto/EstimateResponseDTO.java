package com.codeb.ims.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EstimateResponseDTO {

    private Long estimatedId;
    private Long chainId;

    private String groupName;
    private String brandName;
    private String zoneName;
    private String service;

    private int qty;
    private float costPerUnit;
    private float totalCost;

    private LocalDate deliveryDate;
    private String deliveryDetails;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean isActive; // ✅ new field

    // Getters and Setters

    public Long getEstimatedId() {
        return estimatedId;
    }

    public void setEstimatedId(Long estimatedId) {
        this.estimatedId = estimatedId;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
