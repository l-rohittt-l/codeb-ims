package com.codeb.ims.service;

import com.codeb.ims.model.Chain;
import com.codeb.ims.repository.ChainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChainService {

    @Autowired
    private ChainRepository chainRepository;

    // Add chain
    public String addChain(String chainName) {
        if (chainName == null || chainName.trim().isEmpty()) {
            return "Chain name cannot be empty.";
        }
        if (chainRepository.existsByChainNameIgnoreCase(chainName)) {
            return "Chain already exists.";
        }

        Chain chain = new Chain(chainName.trim());
        chainRepository.save(chain);
        return "success";
    }

    // Get all active chains
    public List<Chain> getAllActiveChains() {
        return chainRepository.findAllByIsActiveTrue();
    }

    // Get chain by ID
    public Optional<Chain> getChainById(Long id) {
        return chainRepository.findById(id);
    }

    // Update chain
    public String updateChain(Long id, String updatedName) {
        Optional<Chain> optionalChain = chainRepository.findById(id);
        if (optionalChain.isPresent()) {
            Chain chain = optionalChain.get();

            if (!chain.getChainName().equalsIgnoreCase(updatedName)
                    && chainRepository.existsByChainNameIgnoreCase(updatedName)) {
                return "Chain already exists.";
            }

            chain.setChainName(updatedName);
            chainRepository.save(chain);
            return "success";
        }
        return "Chain not found.";
    }

    // Soft delete
    public String softDeleteChain(Long id) {
        Optional<Chain> optionalChain = chainRepository.findById(id);
        if (optionalChain.isPresent()) {
            Chain chain = optionalChain.get();
            chain.setActive(false);
            chainRepository.save(chain);
            return "success";
        }
        return "Chain not found.";
    }
}
