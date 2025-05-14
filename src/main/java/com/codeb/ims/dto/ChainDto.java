package com.codeb.ims.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChainDto {

    @NotBlank(message = "Chain name is required")
    @Size(max = 100, message = "Chain name must not exceed 100 characters")
    private String chainName;

    @NotNull(message = "Group ID is required")
    private Long groupId;

    public ChainDto() {}

    public ChainDto(String chainName, Long groupId) {
        this.chainName = chainName;
        this.groupId = groupId;
    }

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
