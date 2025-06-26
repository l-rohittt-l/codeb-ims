package com.codeb.ims.controller;

import com.codeb.ims.dto.InvoiceRequestDTO;
import com.codeb.ims.dto.InvoiceResponseDTO;
import com.codeb.ims.dto.InvoicePrefillDTO;
import com.codeb.ims.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // ✅ Prefill form with estimate data
    @GetMapping("/prepare/{estimateId}")
    public ResponseEntity<InvoicePrefillDTO> prefillInvoice(@PathVariable Long estimateId) {
        InvoicePrefillDTO dto = invoiceService.getPrefillDataFromEstimate(estimateId);
        return ResponseEntity.ok(dto);
    }

    // ✅ Create new invoice
    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> createInvoice(@Valid @RequestBody InvoiceRequestDTO requestDTO) {
        InvoiceResponseDTO response = invoiceService.createInvoice(requestDTO);
        return ResponseEntity.ok(response);
    }

    // ✅ Get all invoices
    @GetMapping
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    // ✅ Get one invoice
    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponseDTO> getInvoice(@PathVariable Long invoiceId) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(invoiceId));
    }

    // ✅ Update email only
    @PutMapping("/{id}/email")
    public ResponseEntity<InvoiceResponseDTO> updateInvoiceEmail(
            @PathVariable Long id,
            @RequestParam String email) {
        InvoiceResponseDTO updated = invoiceService.updateInvoiceEmail(id, email);
        return ResponseEntity.ok(updated);
    }

    // ✅ Delete invoice
    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/by-estimate/{estimateId}")
    public ResponseEntity<List<InvoiceResponseDTO>> getInvoicesByEstimate(@PathVariable Long estimateId) {
        List<InvoiceResponseDTO> list = invoiceService.getInvoicesByEstimateId(estimateId);
        return ResponseEntity.ok(list);
    }
    
    


}
