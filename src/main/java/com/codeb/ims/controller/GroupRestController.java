package com.codeb.ims.controller;

import com.codeb.ims.model.Group;
import com.codeb.ims.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*") // Allow frontend requests during development
public class GroupRestController {

    @Autowired
    private GroupService groupService;

    // GET all active groups
    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllActiveGroups());
    }

    // POST add a group
    @PostMapping
    public ResponseEntity<?> addGroup(@RequestBody Group group) {
        String result = groupService.addGroup(group.getGroupName());
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Group added successfully.");
    }

    // GET group by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {
        Optional<Group> group = groupService.getGroupById(id);
        return group.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT update group
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestBody Group updatedGroup) {
        String result = groupService.updateGroup(id, updatedGroup.getGroupName());
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Group updated successfully.");
    }

    // DELETE (soft) group
    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDeleteGroup(@PathVariable Long id, @RequestParam boolean isLinked) {
        String result = groupService.softDeleteGroup(id, isLinked);
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Group deleted successfully.");
    }
}
