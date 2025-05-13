package com.codeb.ims.service;

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

    // Add Group
    public String addGroup(String groupName) {
        if (groupName == null || groupName.trim().isEmpty()) {
            return "Group name cannot be empty.";
        }
        if (groupRepository.existsByGroupNameIgnoreCase(groupName)) {
            return "Group already exists.";
        }

        Group group = new Group(groupName.trim());
        groupRepository.save(group);
        return "success";
    }

    // List all active groups
    public List<Group> getAllActiveGroups() {
        return groupRepository.findAllByIsActiveTrue();
    }

    // Get group by ID
    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    // Update Group
    public String updateGroup(Long id, String updatedName) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();

            // Check for duplicate name if name is changed
            if (!group.getGroupName().equalsIgnoreCase(updatedName)
                    && groupRepository.existsByGroupNameIgnoreCase(updatedName)) {
                return "Group already exists.";
            }

            group.setGroupName(updatedName);
            groupRepository.save(group);
            return "success";
        }
        return "Group not found.";
    }

    // Soft delete (only if not linked to Chain)
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
}
