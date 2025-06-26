package com.codeb.ims.repository;

import com.codeb.ims.model.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {

    // 🔍 Filter estimates by brand (case-insensitive)
    @Query("SELECT e FROM Estimate e WHERE LOWER(e.brandName) = LOWER(:brandName)")
    List<Estimate> findByBrandNameIgnoreCase(@Param("brandName") String brandName);

    // 🔍 Filter estimates by group (case-insensitive)
    @Query("SELECT e FROM Estimate e WHERE LOWER(e.groupName) = LOWER(:groupName)")
    List<Estimate> findByGroupNameIgnoreCase(@Param("groupName") String groupName);

    // ✅ Fetch all estimates ordered by active status
    @Query("SELECT e FROM Estimate e ORDER BY e.isActive DESC, e.createdAt DESC")
    List<Estimate> findAllSortedByActive();
    
    @Query("SELECT e FROM Estimate e JOIN FETCH e.chain WHERE e.estimatedId = :id")
    Optional<Estimate> findByIdWithChain(@Param("id") Long id);

}
