package com.codeb.ims.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ChainDto {

    @NotBlank(message = "Company name is required")
    @Size(max = 100, message = "Company name must not exceed 100 characters")
    private String companyName;

    @NotBlank(message = "GSTN is required")
    @Size(min = 15, max = 15, message = "GSTN must be 15 characters")
    @Pattern(regexp = "^[0-9A-Z]{15}$", message = "GSTN must be 15 uppercase alphanumeric characters")
    private String gstn;

    @NotNull(message = "Group ID is required")
    private Long groupId;

    public ChainDto() {}

    public ChainDto(String companyName, String gstn, Long groupId) {
        this.companyName = companyName;
        this.gstn = gstn;
        this.groupId = groupId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGstn() {
        return gstn;
    }

    public void setGstn(String gstn) {
        this.gstn = gstn;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
