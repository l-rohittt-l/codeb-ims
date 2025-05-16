package com.codeb.ims.repository;

import com.codeb.ims.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone, Long> {

    // Get all zones where is_active = true
    List<Zone> findAllByIsActiveTrue();

    // Get all zones ordered by active status
    List<Zone> findAllByOrderByIsActiveDesc();

    // Count active zones for dashboard KPI
    @Query("SELECT COUNT(z) FROM Zone z WHERE z.isActive = true")
    long countActiveZones();

    // Filter by Brand
    List<Zone> findByBrand_BrandIdAndIsActiveTrue(Long brandId);

    // Filter by Company (via brand → chain → companyName)
    @Query("SELECT z FROM Zone z WHERE z.isActive = true AND z.brand.chain.companyName = :companyName")
    List<Zone> findByCompanyName(String companyName);

    // Filter by Group
    @Query("SELECT z FROM Zone z WHERE z.isActive = true AND z.brand.chain.group.groupName = :groupName")
    List<Zone> findByGroupName(String groupName);

    @Query("SELECT z FROM Zone z " +
           "JOIN FETCH z.brand b " +
           "JOIN FETCH b.chain c " +
           "JOIN FETCH c.group g " +
           "ORDER BY z.isActive DESC")
    List<Zone> findAllWithFullInfoSorted();

    @Query("SELECT z FROM Zone z " +
           "JOIN FETCH z.brand b " +
           "JOIN FETCH b.chain c " +
           "JOIN FETCH c.group g " +
           "ORDER BY z.isActive DESC")
    List<Zone> fetchAllZonesWithFullInfo();

    @Query("SELECT z FROM Zone z " +
           "JOIN FETCH z.brand b " +
           "JOIN FETCH b.chain c " +
           "JOIN FETCH c.group g " +
           "ORDER BY z.isActive DESC")
    List<Zone> findAllWithFullHierarchy();

    // ✅ NEW: Used to get zone names by brand name (for dropdown)
    List<Zone> findByBrand_BrandNameIgnoreCaseAndIsActiveTrue(String brandName);
}
