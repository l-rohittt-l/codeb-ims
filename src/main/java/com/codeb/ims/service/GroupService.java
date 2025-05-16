package com.codeb.ims.service;

import com.codeb.ims.dto.GroupDto;
import com.codeb.ims.model.Group;
import com.codeb.ims.repository.GroupRepository;
import com.codeb.ims.repository.ChainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ChainRepository chainRepository;

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

        if (!group.getGroupName().equalsIgnoreCase(dto.getGroupName()) &&
                groupRepository.existsByGroupNameIgnoreCase(dto.getGroupName())) {
            return "Group name already exists.";
        }

        if (!group.getGroupCode().equalsIgnoreCase(dto.getGroupCode()) &&
                groupRepository.existsByGroupCodeIgnoreCase(dto.getGroupCode())) {
            return "Group code already exists.";
        }

        group.setGroupName(dto.getGroupName().trim());
        group.setGroupCode(dto.getGroupCode().trim());

        groupRepository.save(group);
        return "success";
    }

    public List<Group> getAllActiveGroups() {
        return groupRepository.findAllByIsActiveTrue();
    }

    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    // ✅ NEW: prevent deletion if group is linked to active chains
    public String softDeleteGroup(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty()) {
            return "Group not found.";
        }

        long linkedChains = chainRepository.countByGroupIdAndIsActiveTrue(id);
        if (linkedChains > 0) {
            return "Group is linked to active chains and cannot be deleted.";
        }

        Group group = optionalGroup.get();
        group.setActive(false);
        groupRepository.save(group);
        return "success";
    }

    public List<Group> getAllGroupsSorted() {
        return groupRepository.findAllByOrderByIsActiveDesc();
    }

    public String reactivateGroup(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();

            if (group.isActive()) {
                return "Group is already active.";
            }

            group.setActive(true);
            groupRepository.save(group);
            return "success";
        }
        return "Group not found.";
    }

    public long getTotalActiveGroups() {
        return groupRepository.countActiveGroups();
    }
}
