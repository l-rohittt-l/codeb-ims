package com.codeb.ims.dto;

public class ZoneFullDto {
    private Long zoneId;
    private String zoneName;
    private String brandName;
    private String companyName;
    private String groupName;
    private boolean isActive; // ✅ Add this

    // ✅ Constructor
    public ZoneFullDto(Long zoneId, String zoneName, String brandName, String companyName, String groupName, boolean isActive) {
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.brandName = brandName;
        this.companyName = companyName;
        this.groupName = groupName;
        this.isActive = isActive;
    }

    // ✅ Getters & Setters
    public Long getZoneId() { return zoneId; }
    public void setZoneId(Long zoneId) { this.zoneId = zoneId; }

    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
