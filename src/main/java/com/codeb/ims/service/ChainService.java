package com.codeb.ims.service;

import com.codeb.ims.dto.ChainDto;
import com.codeb.ims.model.Chain;
import com.codeb.ims.model.Group;
import com.codeb.ims.repository.ChainRepository;
import com.codeb.ims.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChainService {

    @Autowired
    private ChainRepository chainRepository;

    @Autowired
    private GroupRepository groupRepository;

    // ✅ Add chain using DTO
    public String addChain(ChainDto dto) {
        if (dto.getChainName() == null || dto.getChainName().trim().isEmpty()) {
            return "Chain name cannot be empty.";
        }
        if (chainRepository.existsByChainNameIgnoreCase(dto.getChainName())) {
            return "Chain already exists.";
        }

        Optional<Group> optionalGroup = groupRepository.findById(dto.getGroupId());
        if (optionalGroup.isEmpty()) {
            return "Group not found.";
        }

        Chain chain = new Chain();
        chain.setChainName(dto.getChainName().trim());
        chain.setGroup(optionalGroup.get());
        chain.setActive(true);

        chainRepository.save(chain);
        return "success";
    }

    // ✅ Get all active chains
    public List<Chain> getAllActiveChains() {
        List<Chain> chains = chainRepository.findAllByIsActiveTrue();
        chains.forEach(chain -> {
            if (chain.getGroup() != null) {
                chain.getGroup().getGroupName(); // force initialize
            }
        });
        return chains;
    }


    // ✅ Get chain by ID
    public Optional<Chain> getChainById(Long id) {
        return chainRepository.findById(id);
    }

    // ✅ Update chain using DTO
    public String updateChain(Long id, ChainDto dto) {
        Optional<Chain> optionalChain = chainRepository.findById(id);
        if (optionalChain.isEmpty()) {
            return "Chain not found.";
        }

        Chain chain = optionalChain.get();

        if (!chain.getChainName().equalsIgnoreCase(dto.getChainName()) &&
            chainRepository.existsByChainNameIgnoreCase(dto.getChainName())) {
            return "Chain already exists.";
        }

        Optional<Group> optionalGroup = groupRepository.findById(dto.getGroupId());
        if (optionalGroup.isEmpty()) {
            return "Group not found.";
        }

        chain.setChainName(dto.getChainName().trim());
        chain.setGroup(optionalGroup.get());
        chainRepository.save(chain);
        return "success";
    }

    // ✅ Soft delete chain
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
