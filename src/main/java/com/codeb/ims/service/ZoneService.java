package com.codeb.ims.service;

import com.codeb.ims.dto.ZoneDto;
import com.codeb.ims.dto.ZoneFullDto;
import com.codeb.ims.model.Brand;
import com.codeb.ims.model.Zone;
import com.codeb.ims.repository.BrandRepository;
import com.codeb.ims.repository.ZoneRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private BrandRepository brandRepository;

    // ✅ Add zone
    public String addZone(ZoneDto dto) {
        Optional<Brand> brandOpt = brandRepository.findById(dto.getBrandId());
        if (brandOpt.isEmpty()) return "Brand not found.";

        Zone zone = new Zone(dto.getZoneName().trim(), brandOpt.get());
        zoneRepository.save(zone);
        return "success";
    }

    // ✅ Get all zones as DTOs with full info
    @Transactional
    public List<ZoneFullDto> getAllZoneDetailsSorted() {
        return zoneRepository.findAllWithFullHierarchy()
                .stream()
                .map(zone -> {
                    String brandName = zone.getBrand().getBrandName();
                    String companyName = zone.getBrand().getChain().getCompanyName();
                    String groupName = zone.getBrand().getChain().getGroup().getGroupName();
                    return new ZoneFullDto(
                            zone.getZoneId(),
                            zone.getZoneName(),
                            brandName,
                            companyName,
                            groupName,
                            zone.isActive()
                    );
                })
                .collect(Collectors.toList());
    }

    // ✅ Get zone by ID
    public Optional<Zone> getZoneById(Long id) {
        return zoneRepository.findById(id);
    }

    // ✅ Update zone
    public String updateZone(Long id, ZoneDto dto) {
        Optional<Zone> zoneOpt = zoneRepository.findById(id);
        if (zoneOpt.isEmpty()) return "Zone not found.";

        Optional<Brand> brandOpt = brandRepository.findById(dto.getBrandId());
        if (brandOpt.isEmpty()) return "Brand not found.";

        Zone zone = zoneOpt.get();
        zone.setZoneName(dto.getZoneName().trim());
        zone.setBrand(brandOpt.get());
        zoneRepository.save(zone);
        return "success";
    }

    // ✅ Soft delete
    public String softDeleteZone(Long id) {
        Optional<Zone> zoneOpt = zoneRepository.findById(id);
        if (zoneOpt.isEmpty()) return "Zone not found.";

        Zone zone = zoneOpt.get();
        zone.setActive(false);
        zoneRepository.save(zone);
        return "success";
    }

    // ✅ Reactivate zone
    public String reactivateZone(Long id) {
        Optional<Zone> zoneOpt = zoneRepository.findById(id);
        if (zoneOpt.isEmpty()) return "Zone not found.";

        Zone zone = zoneOpt.get();
        if (zone.isActive()) return "Zone is already active.";

        zone.setActive(true);
        zoneRepository.save(zone);
        return "success";
    }

    // ✅ Filters
    public List<Zone> getZonesByBrandId(Long brandId) {
        return zoneRepository.findByBrand_BrandIdAndIsActiveTrue(brandId);
    }

    public List<Zone> getZonesByCompanyName(String companyName) {
        return zoneRepository.findByCompanyName(companyName);
    }

    public List<Zone> getZonesByGroupName(String groupName) {
        return zoneRepository.findByGroupName(groupName);
    }

    // ✅ KPI count
    public long getTotalActiveZones() {
        return zoneRepository.countActiveZones();
    }

    // ✅ New: get zone names for a brand (used in dropdown)
    public List<String> getZoneNamesByBrandName(String brandName) {
        return zoneRepository.findByBrand_BrandNameIgnoreCaseAndIsActiveTrue(brandName)
                .stream()
                .map(Zone::getZoneName)
                .collect(Collectors.toList());
    }
}
