package com.codeb.ims.repository;

import com.codeb.ims.model.Chain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChainRepository extends JpaRepository<Chain, Long> {

    Optional<Chain> findByChainNameIgnoreCase(String chainName);

    boolean existsByChainNameIgnoreCase(String chainName);

    List<Chain> findAllByIsActiveTrue();
}
