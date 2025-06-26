package com.codeb.ims.repository;

import com.codeb.ims.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    // Get all invoices for a specific estimate
    List<Invoice> findByEstimate_EstimatedId(Long estimateId);

    // Optional: Find invoice by invoice number
    Invoice findByInvoiceNumber(String invoiceNumber);
    
    
}
