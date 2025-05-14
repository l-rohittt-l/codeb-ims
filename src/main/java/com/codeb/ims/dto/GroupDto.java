package com.codeb.ims.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class GroupDto {

    @NotBlank(message = "Group name is required")
    @Size(max = 100, message = "Group name must not exceed 100 characters")
    private String groupName;

    @NotBlank(message = "Group code is required")
    @Size(max = 50, message = "Group code must not exceed 50 characters")
    @Pattern(regexp = "^[A-Z0-9_-]+$", message = "Group code must be uppercase and may only contain letters, numbers, hyphens, and underscores")
    private String groupCode;

    public GroupDto() {}

    public GroupDto(String groupName, String groupCode) {
        this.groupName = groupName;
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}
