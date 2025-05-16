package com.codeb.ims.controller;

import com.codeb.ims.dto.ZoneDto;
import com.codeb.ims.dto.ZoneFullDto;
import com.codeb.ims.model.Zone;
import com.codeb.ims.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/zones")
@CrossOrigin(origins = "*")
public class ZoneRestController {

    @Autowired
    private ZoneService zoneService;

    // ✅ Add zone
    @PostMapping
    public ResponseEntity<?> addZone(@RequestBody ZoneDto dto) {
        String result = zoneService.addZone(dto);
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Zone added successfully.");
    }

    // ✅ Get all zones (with brand, chain, group info)
    @GetMapping("/all")
    public ResponseEntity<List<ZoneFullDto>> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllZoneDetailsSorted());
    }

    // ✅ Get zone by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getZoneById(@PathVariable Long id) {
        Optional<Zone> zone = zoneService.getZoneById(id);
        return zone.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Update zone
    @PutMapping("/{id}")
    public ResponseEntity<?> updateZone(@PathVariable Long id, @RequestBody ZoneDto dto) {
        String result = zoneService.updateZone(id, dto);
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Zone updated successfully.");
    }

    // ✅ Delete zone (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteZone(@PathVariable Long id) {
        String result = zoneService.softDeleteZone(id);
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Zone deleted successfully.");
    }

    // ✅ Reactivate zone
    @PutMapping("/{id}/reactivate")
    public ResponseEntity<?> reactivateZone(@PathVariable Long id) {
        String result = zoneService.reactivateZone(id);
        if (!result.equals("success")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Zone reactivated successfully.");
    }

    // ✅ Get total zones for KPI
    @GetMapping("/total")
    public ResponseEntity<Map<String, Long>> getTotalZones() {
        long total = zoneService.getTotalActiveZones();
        Map<String, Long> map = new HashMap<>();
        map.put("total", total);
        return ResponseEntity.ok(map);
    }

    // ✅ Filter by Brand ID
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<Zone>> getZonesByBrand(@PathVariable Long brandId) {
        return ResponseEntity.ok(zoneService.getZonesByBrandId(brandId));
    }

    // ✅ Filter by Company Name
    @GetMapping("/company/{companyName}")
    public ResponseEntity<List<Zone>> getZonesByCompany(@PathVariable String companyName) {
        return ResponseEntity.ok(zoneService.getZonesByCompanyName(companyName));
    }

    // ✅ Filter by Group Name
    @GetMapping("/group/{groupName}")
    public ResponseEntity<List<Zone>> getZonesByGroup(@PathVariable String groupName) {
        return ResponseEntity.ok(zoneService.getZonesByGroupName(groupName));
    }

    // ✅ NEW: Get list of zone names by brand name (for estimate form)
    @GetMapping("/names/by-brand/{brandName}")
    public ResponseEntity<List<String>> getZoneNamesByBrand(@PathVariable String brandName) {
        return ResponseEntity.ok(zoneService.getZoneNamesByBrandName(brandName));
    }
}
