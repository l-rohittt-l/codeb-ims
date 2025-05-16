package com.codeb.ims.repository;

import com.codeb.ims.model.Brand;
import com.codeb.ims.model.Chain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findByIsActiveTrue();

    List<Brand> findAllByOrderByIsActiveDesc(); // 🆕 For admin/sales view

    List<Brand> findByChainAndIsActiveTrue(Chain chain);

    List<Brand> findByChain_Group_GroupNameAndIsActiveTrue(String groupName);

    List<Brand> findByBrandNameContainingIgnoreCaseAndIsActiveTrue(String brandName);

    // ✅ NEW: for AddEstimate.jsx dynamic dropdown
    List<Brand> findByChain_ChainIdAndIsActiveTrue(Long chainId);
}
