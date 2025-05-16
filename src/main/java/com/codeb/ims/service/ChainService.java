package com.codeb.ims.service;

import com.codeb.ims.dto.ChainDto;
import com.codeb.ims.model.Chain;
import com.codeb.ims.model.Group;
import com.codeb.ims.repository.ChainRepository;
import com.codeb.ims.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChainService {

    @Autowired
    private ChainRepository chainRepository;

    @Autowired
    private GroupRepository groupRepository;

    public String addChain(ChainDto dto) {
        if (dto.getCompanyName() == null || dto.getCompanyName().trim().isEmpty()) {
            return "Company name cannot be empty.";
        }

        if (chainRepository.existsByGstnIgnoreCase(dto.getGstn())) {
            return "GSTN already exists.";
        }

        if (chainRepository.existsByCompanyNameIgnoreCase(dto.getCompanyName())) {
            return "Company name already exists.";
        }

        Optional<Group> optionalGroup = groupRepository.findById(dto.getGroupId());
        if (optionalGroup.isEmpty()) {
            return "Group not found.";
        }

        Chain chain = new Chain();
        chain.setCompanyName(dto.getCompanyName().trim());
        chain.setGstn(dto.getGstn().trim().toUpperCase());
        chain.setGroup(optionalGroup.get());
        chain.setActive(true);

        chainRepository.save(chain);
        return "success";
    }

    @Transactional
    public List<Chain> getAllChainsSorted() {
        List<Chain> chains = chainRepository.findAllByOrderByIsActiveDesc();
        chains.forEach(chain -> {
            if (chain.getGroup() != null) {
                chain.getGroup().getGroupName(); // lazy-load
            }
        });
        return chains;
    }

    public Optional<Chain> getChainById(Long id) {
        return chainRepository.findById(id);
    }

    public String updateChain(Long id, ChainDto dto) {
        Optional<Chain> optionalChain = chainRepository.findById(id);
        if (optionalChain.isEmpty()) {
            return "Company not found.";
        }

        Chain chain = optionalChain.get();

        if (!chain.getCompanyName().equalsIgnoreCase(dto.getCompanyName()) &&
                chainRepository.existsByCompanyNameIgnoreCase(dto.getCompanyName())) {
            return "Company name already exists.";
        }

        if (!chain.getGstn().equalsIgnoreCase(dto.getGstn()) &&
                chainRepository.existsByGstnIgnoreCase(dto.getGstn())) {
            return "GSTN already exists.";
        }

        Optional<Group> optionalGroup = groupRepository.findById(dto.getGroupId());
        if (optionalGroup.isEmpty()) {
            return "Group not found.";
        }

        chain.setCompanyName(dto.getCompanyName().trim());
        chain.setGstn(dto.getGstn().trim().toUpperCase());
        chain.setGroup(optionalGroup.get());

        chainRepository.save(chain);
        return "success";
    }

    public String softDeleteChain(Long id) {
        Optional<Chain> optionalChain = chainRepository.findById(id);
        if (optionalChain.isPresent()) {
            Chain chain = optionalChain.get();
            chain.setActive(false);
            chainRepository.save(chain);
            return "success";
        }
        return "Company not found.";
    }

    public String reactivateChain(Long id) {
        Optional<Chain> optionalChain = chainRepository.findById(id);
        if (optionalChain.isEmpty()) {
            return "Company not found.";
        }

        Chain chain = optionalChain.get();
        if (chain.isActive()) {
            return "Company is already active.";
        }

        chain.setActive(true);
        chainRepository.save(chain);
        return "success";
    }

    public long getTotalActiveChains() {
        return chainRepository.countActiveChains();
    }

    public List<Chain> getChainsByGroupId(Long groupId) {
        // Changed this line to use the new eager fetching method to avoid LazyInitializationException
        List<Chain> chains = chainRepository.findChainsByGroupIdWithGroup(groupId);
        // No need to manually lazy-load group name here because of JOIN FETCH
        return chains;
    }

}
