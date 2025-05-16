package com.codeb.ims.service;

import com.codeb.ims.dto.InvoiceRequestDTO;
import com.codeb.ims.dto.InvoiceResponseDTO;
import com.codeb.ims.dto.InvoicePrefillDTO;

import java.util.List;

public interface InvoiceService {

    InvoicePrefillDTO getPrefillDataFromEstimate(Long estimateId);

    InvoiceResponseDTO createInvoice(InvoiceRequestDTO requestDTO);

    List<InvoiceResponseDTO> getAllInvoices();

    InvoiceResponseDTO getInvoiceById(Long invoiceId);

    InvoiceResponseDTO updateInvoiceEmail(Long invoiceId, String newEmail);

    void deleteInvoice(Long invoiceId);
}
