package com.codeb.ims.controller;

import com.codeb.ims.dto.ChainDto;
import com.codeb.ims.model.Chain;
import com.codeb.ims.service.ChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chains")
@CrossOrigin(origins = "*") // For local and Netlify frontend access
public class ChainRestController {

    @Autowired
    private ChainService chainService;

    // ✅ Get all active chains
    @GetMapping
    public ResponseEntity<List<Chain>> getAllChains() {
        return ResponseEntity.ok(chainService.getAllActiveChains());
    }

    // ✅ Add chain using ChainDto
    @PostMapping
    public ResponseEntity<?> addChain(@RequestBody ChainDto dto) {
        String result = chainService.addChain(dto);
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Chain added successfully.");
    }

    // ✅ Get chain by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getChainById(@PathVariable Long id) {
        Optional<Chain> chain = chainService.getChainById(id);
        return chain.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Update chain using ChainDto
    @PutMapping("/{id}")
    public ResponseEntity<?> updateChain(@PathVariable Long id, @RequestBody ChainDto dto) {
        String result = chainService.updateChain(id, dto);
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Chain updated successfully.");
    }

    // ✅ Soft delete chain
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChain(@PathVariable Long id) {
        String result = chainService.softDeleteChain(id);
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Chain deleted successfully.");
    }
    
    @PutMapping("/{id}/reactivate")
    public ResponseEntity<?> reactivateChain(@PathVariable Long id) {
        String result = chainService.reactivateChain(id);
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Chain reactivated successfully.");
    }

}
