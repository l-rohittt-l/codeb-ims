package com.codeb.ims.service;

import com.codeb.ims.dto.*;
import com.codeb.ims.model.Estimate;
import com.codeb.ims.model.Invoice;
import com.codeb.ims.repository.EstimateRepository;
import com.codeb.ims.repository.InvoiceRepository;
import com.codeb.ims.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private EstimateRepository estimateRepository;

    @Override
    public InvoicePrefillDTO getPrefillDataFromEstimate(Long estimateId) {
        Estimate estimate = estimateRepository.findById(estimateId)
                .orElseThrow(() -> new RuntimeException("Estimate not found"));

        InvoicePrefillDTO dto = new InvoicePrefillDTO();
        dto.setEstimateId(estimate.getEstimatedId());
        dto.setChainId(estimate.getChain().getChainId());
        dto.setGroupName(estimate.getGroupName());
        dto.setBrandName(estimate.getBrandName());
        dto.setZoneName(estimate.getZoneName());
        dto.setService(estimate.getService());
        dto.setQty(estimate.getQty());
        dto.setCostPerUnit(estimate.getCostPerUnit());
        dto.setTotalCost(estimate.getTotalCost());
        dto.setDeliveryDate(estimate.getDeliveryDate() != null ? estimate.getDeliveryDate().toString() : null);
        dto.setDeliveryDetails(estimate.getDeliveryDetails());
        dto.setGstin(estimate.getChain().getGstn()); // assume chain has getGstin()

        return dto;
    }

    @Override
    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO requestDTO) {
        Estimate estimate = estimateRepository.findById(requestDTO.getEstimateId())
                .orElseThrow(() -> new RuntimeException("Estimate not found"));

        // Calculate total paid so far
        List<Invoice> existingInvoices = invoiceRepository.findByEstimate_EstimatedId(estimate.getEstimatedId());
        double alreadyPaid = existingInvoices.stream().mapToDouble(Invoice::getAmountPaid).sum();

        double newPaid = requestDTO.getAmountPaid();
        double newTotalPaid = alreadyPaid + newPaid;
        double balance = estimate.getTotalCost() - newTotalPaid;

        if (newTotalPaid > estimate.getTotalCost()) {
            throw new RuntimeException("Payment exceeds total estimate amount");
        }

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(generateInvoiceNumber());
        invoice.setEstimate(estimate);
        invoice.setAmountPaid(newPaid);
        invoice.setBalanceAmount(balance);
        invoice.setEmail(requestDTO.getEmail());
        invoice.setPdfPath(""); // to be set later
        invoice.setDeliveryDetails(requestDTO.getDeliveryDetails());
        invoice.setDeliveryDate(requestDTO.getDeliveryDate());
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setUpdatedAt(LocalDateTime.now());

        Invoice saved = invoiceRepository.save(invoice);

        return mapToDTO(saved);
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceResponseDTO getInvoiceById(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        return mapToDTO(invoice);
    }

    @Override
    public InvoiceResponseDTO updateInvoiceEmail(Long invoiceId, String newEmail) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        invoice.setEmail(newEmail);
        invoice.setUpdatedAt(LocalDateTime.now());
        return mapToDTO(invoiceRepository.save(invoice));
    }

    @Override
    public void deleteInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        invoiceRepository.delete(invoice);
    }

    // Utility method
    private String generateInvoiceNumber() {
        return "INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private InvoiceResponseDTO mapToDTO(Invoice invoice) {
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setInvoiceId(invoice.getInvoiceId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setEstimateId(invoice.getEstimate().getEstimatedId());
        dto.setAmountPaid(invoice.getAmountPaid());
        dto.setBalanceAmount(invoice.getBalanceAmount());
        dto.setEmail(invoice.getEmail());
        dto.setPdfPath(invoice.getPdfPath());
        dto.setDeliveryDetails(invoice.getDeliveryDetails());
        dto.setDeliveryDate(invoice.getDeliveryDate());
        dto.setCreatedAt(invoice.getCreatedAt());
        dto.setUpdatedAt(invoice.getUpdatedAt());
        return dto;
    }
}
