package com.codeb.ims.controller;

import com.codeb.ims.dto.GroupDto;
import com.codeb.ims.model.Group;
import com.codeb.ims.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*")
public class GroupRestController {

    @Autowired
    private GroupService groupService;

    // ✅ GET all active groups
    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllActiveGroups());
    }

    // ✅ POST add a new group using DTO
    @PostMapping
    public ResponseEntity<?> addGroup(@Valid @RequestBody GroupDto groupDto) {
        String result = groupService.addGroup(groupDto);
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Group added successfully.");
    }

    // ✅ GET group by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {
        Optional<Group> group = groupService.getGroupById(id);
        return group.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ PUT update group using DTO
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable Long id, @Valid @RequestBody GroupDto updatedDto) {
        String result = groupService.updateGroup(id, updatedDto);
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Group updated successfully.");
    }

    // ✅ DELETE soft delete group
    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDeleteGroup(@PathVariable Long id, @RequestParam boolean isLinked) {
        String result = groupService.softDeleteGroup(id, isLinked);
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Group deleted successfully.");
    }
    
 // ✅ GET all groups (active + inactive) sorted
    @GetMapping("/all")
    public ResponseEntity<List<Group>> getAllGroupsIncludingInactive() {
        return ResponseEntity.ok(groupService.getAllGroupsSorted());
    }
    
    @PutMapping("/{id}/activate")
    public ResponseEntity<?> reactivateGroup(@PathVariable Long id) {
        String result = groupService.reactivateGroup(id);
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Group reactivated successfully.");
    }

}
