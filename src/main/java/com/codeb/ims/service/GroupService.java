package com.codeb.ims.service;
import com.codeb.ims.dto.GroupDto;
import com.codeb.ims.model.Group;
import com.codeb.ims.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    // ✅ Add Group using DTO
    public String addGroup(GroupDto dto) {
        if (groupRepository.existsByGroupNameIgnoreCase(dto.getGroupName())) {
            return "Group name already exists.";
        }

        if (groupRepository.existsByGroupCodeIgnoreCase(dto.getGroupCode())) {
            return "Group code already exists.";
        }

        Group group = new Group();
        group.setGroupName(dto.getGroupName().trim());
        group.setGroupCode(dto.getGroupCode().trim());
        group.setActive(true);

        groupRepository.save(group);
        return "success";
    }

    // ✅ Update Group using DTO
    public String updateGroup(Long id, GroupDto dto) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty()) {
            return "Group not found.";
        }

        Group group = optionalGroup.get();

        // Check if name changed and is already taken
        if (!group.getGroupName().equalsIgnoreCase(dto.getGroupName()) &&
                groupRepository.existsByGroupNameIgnoreCase(dto.getGroupName())) {
            return "Group name already exists.";
        }

        // Check if code changed and is already taken
        if (!group.getGroupCode().equalsIgnoreCase(dto.getGroupCode()) &&
                groupRepository.existsByGroupCodeIgnoreCase(dto.getGroupCode())) {
            return "Group code already exists.";
        }

        group.setGroupName(dto.getGroupName().trim());
        group.setGroupCode(dto.getGroupCode().trim());

        groupRepository.save(group);
        return "success";
    }

    // ✅ Other methods remain unchanged
    public List<Group> getAllActiveGroups() {
        return groupRepository.findAllByIsActiveTrue();
    }

    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    public String softDeleteGroup(Long id, boolean isLinkedToChain) {
        if (isLinkedToChain) {
            return "Group is linked to a chain and cannot be deleted.";
        }

        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            group.setActive(false);
            groupRepository.save(group);
            return "success";
        }
        return "Group not found.";
    }
    
    public List<Group> getAllGroupsSorted() {
        return groupRepository.findAllByOrderByIsActiveDesc();
    }


}
