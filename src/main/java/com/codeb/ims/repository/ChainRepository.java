package com.codeb.ims.repository;

import com.codeb.ims.model.Chain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChainRepository extends JpaRepository<Chain, Long> {

    Optional<Chain> findByCompanyNameIgnoreCase(String companyName);
    Optional<Chain> findByGstnIgnoreCase(String gstn);

    boolean existsByCompanyNameIgnoreCase(String companyName);
    boolean existsByGstnIgnoreCase(String gstn);

    List<Chain> findAllByIsActiveTrue();

    @Query("SELECT COUNT(c) FROM Chain c WHERE c.isActive = true")
    long countActiveChains();

    @Query("SELECT COUNT(c) FROM Chain c WHERE c.group.groupId = :groupId AND c.isActive = true")
    long countByGroupIdAndIsActiveTrue(Long groupId);

    List<Chain> findAllByOrderByIsActiveDesc();
    List<Chain> findByGroup_GroupIdOrderByIsActiveDesc(Long groupId);
    @Query("SELECT c FROM Chain c JOIN FETCH c.group g WHERE g.groupId = :groupId ORDER BY c.isActive DESC")
    List<Chain> findChainsByGroupIdWithGroup(Long groupId);

}
