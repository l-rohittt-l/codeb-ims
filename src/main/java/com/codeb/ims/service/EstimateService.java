package com.codeb.ims.service;

import com.codeb.ims.dto.EstimateRequestDTO;
import com.codeb.ims.dto.EstimateResponseDTO;
import com.codeb.ims.model.Chain;
import com.codeb.ims.model.Estimate;
import com.codeb.ims.repository.ChainRepository;
import com.codeb.ims.repository.EstimateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstimateService {

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private ChainRepository chainRepository;

    @Transactional(readOnly = true)
    public List<EstimateResponseDTO> getAllEstimates() {
    	List<Estimate> estimates = estimateRepository.findAllSortedByActive();
        return estimates.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    public EstimateResponseDTO createEstimate(EstimateRequestDTO dto) {
        Chain chain = chainRepository.findById(dto.getChainId())
                .orElseThrow(() -> new EntityNotFoundException("Chain not found with ID: " + dto.getChainId()));

        Estimate estimate = new Estimate();
        estimate.setChain(chain);
        estimate.setGroupName(dto.getGroupName());
        estimate.setBrandName(dto.getBrandName());
        estimate.setZoneName(dto.getZoneName());
        estimate.setService(dto.getService());
        estimate.setQty(dto.getQty());
        estimate.setCostPerUnit(dto.getCostPerUnit());

        float total = dto.getTotalCost() == 0 ? dto.getQty() * dto.getCostPerUnit() : dto.getTotalCost();
        estimate.setTotalCost(total);

        estimate.setDeliveryDate(dto.getDeliveryDate());
        estimate.setDeliveryDetails(dto.getDeliveryDetails());

        // ✅ Ensure isActive is set to true on creation
        estimate.setActive(true);

        Estimate saved = estimateRepository.save(estimate);
        return mapToDTO(saved);
    }

    @Transactional
    public EstimateResponseDTO updateEstimate(Long id, EstimateRequestDTO dto) {
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estimate not found with ID: " + id));

        Chain chain = chainRepository.findById(dto.getChainId())
                .orElseThrow(() -> new EntityNotFoundException("Chain not found with ID: " + dto.getChainId()));

        estimate.setChain(chain);
        estimate.setGroupName(dto.getGroupName());
        estimate.setBrandName(dto.getBrandName());
        estimate.setZoneName(dto.getZoneName());
        estimate.setService(dto.getService());
        estimate.setQty(dto.getQty());
        estimate.setCostPerUnit(dto.getCostPerUnit());

        float total = dto.getTotalCost() == 0 ? dto.getQty() * dto.getCostPerUnit() : dto.getTotalCost();
        estimate.setTotalCost(total);

        estimate.setDeliveryDate(dto.getDeliveryDate());
        estimate.setDeliveryDetails(dto.getDeliveryDetails());

        Estimate updated = estimateRepository.save(estimate);
        return mapToDTO(updated);
    }

    @Transactional
    public void deleteEstimate(Long id) {
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estimate not found with ID: " + id));

        // ✅ Soft delete
        estimate.setActive(false);
        estimateRepository.save(estimate);
    }

    @Transactional
    public void reactivateEstimate(Long id) {
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estimate not found with ID: " + id));

        estimate.setActive(true);
        estimateRepository.save(estimate);
    }

    @Transactional(readOnly = true)
    public List<EstimateResponseDTO> getEstimatesByBrand(String brandName) {
        List<Estimate> estimates = estimateRepository.findByBrandNameIgnoreCase(brandName);
        return estimates.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EstimateResponseDTO> getEstimatesByGroup(String groupName) {
        List<Estimate> estimates = estimateRepository.findByGroupNameIgnoreCase(groupName);
        return estimates.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private EstimateResponseDTO mapToDTO(Estimate estimate) {
        EstimateResponseDTO dto = new EstimateResponseDTO();
        dto.setEstimatedId(estimate.getEstimatedId());
        dto.setChainId(estimate.getChain().getChainId());
        dto.setGroupName(estimate.getGroupName());
        dto.setBrandName(estimate.getBrandName());
        dto.setZoneName(estimate.getZoneName());
        dto.setService(estimate.getService());
        dto.setQty(estimate.getQty());
        dto.setCostPerUnit(estimate.getCostPerUnit());
        dto.setTotalCost(estimate.getTotalCost());
        dto.setDeliveryDate(estimate.getDeliveryDate());
        dto.setDeliveryDetails(estimate.getDeliveryDetails());
        dto.setCreatedAt(estimate.getCreatedAt());
        dto.setUpdatedAt(estimate.getUpdatedAt());

        // ✅ Include isActive in response
        dto.setActive(estimate.isActive());

        return dto;
    }
 // Option 1: rename method in service
    @Transactional
    public void softDeleteEstimate(Long id) {
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estimate not found with ID: " + id));
        estimate.setActive(false);
        estimateRepository.save(estimate);
    }

}
