package com.lawencon.ocr.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.ocr.dto.WebResponse;
import com.lawencon.ocr.dto.request.InvoiceDetailRequestDto;
import com.lawencon.ocr.dto.request.InvoiceRequestDto;
import com.lawencon.ocr.dto.response.InvoiceDetailResponseDto;
import com.lawencon.ocr.dto.response.InvoiceResponseDto;
import com.lawencon.ocr.model.Invoice;
import com.lawencon.ocr.model.InvoiceDetail;
import com.lawencon.ocr.repository.InvoiceDetailRepository;
import com.lawencon.ocr.repository.InvoiceRepository;
import com.lawencon.ocr.service.InvoiceService;

import jakarta.transaction.Transactional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private InvoiceDetailRepository invoiceDetailRepository;

	@Override
	@Transactional
	public WebResponse<String> save(InvoiceRequestDto invoiceRequest) {
		final Invoice invoice = new Invoice();
		final InvoiceDetail invoiceDetail = new InvoiceDetail();
		final LocalDateTime timeNow = LocalDateTime.now();

		invoice.setId(UUID.randomUUID().toString());
		invoice.setInvoiceNo(invoiceRequest.getInvoiceNo().trim());
		invoice.setTotal(invoiceRequest.getTotal().trim());
		invoice.setCreateAt(timeNow);
		invoice.setUpdateAt(timeNow);

		invoiceRepository.save(invoice);

		final List<InvoiceDetailRequestDto> invoiceDetailRequests = invoiceRequest.getInvoiceDetail();
		for (InvoiceDetailRequestDto detailRequest : invoiceDetailRequests) {
			invoiceDetail.setId(UUID.randomUUID().toString());
			invoiceDetail.setItemName(detailRequest.getItemName());
			invoiceDetail.setQuantity(detailRequest.getQuantity());
			invoiceDetail.setUnitPrice(detailRequest.getUnitPrice());
			invoiceDetail.setSubTotal(detailRequest.getSubTotal());
			invoiceDetail.setInvoice(invoice);
			invoiceDetail.setCreateAt(timeNow);
			invoiceDetail.setUpdateAt(timeNow);

			invoiceDetailRepository.save(invoiceDetail);
		}
		
		return WebResponse.<String>builder().message("ok").data("insert successfully").build();

	}

	@Override
	public WebResponse<List<InvoiceResponseDto>> getAll() {
		final List<Invoice> invoices = invoiceRepository.findAll();
		final List<InvoiceResponseDto> invoiceResponses = new ArrayList<>();
		
		for(Invoice invoice : invoices) {
			final InvoiceResponseDto invoiceResponse = new InvoiceResponseDto();
			final List<InvoiceDetailResponseDto> listInvoiceDetail = new ArrayList<>();
			
			invoiceResponse.setId(invoice.getId());
			invoiceResponse.setInvoiceNo(invoice.getInvoiceNo());
			invoiceResponse.setTotal(invoice.getTotal());

			for(InvoiceDetail invoiceDetail : invoice.getInvoiceDetail()) {
				final InvoiceDetailResponseDto invoiceDetailResponse = new InvoiceDetailResponseDto();
				invoiceDetailResponse.setId(invoiceDetail.getId());
				invoiceDetailResponse.setItemName(invoiceDetail.getItemName());
				invoiceDetailResponse.setQuantity(invoiceDetail.getQuantity());
				invoiceDetailResponse.setUnitPrice(invoiceDetail.getUnitPrice());
				invoiceDetailResponse.setSubTotal(invoiceDetail.getSubTotal());
				
				listInvoiceDetail.add(invoiceDetailResponse);
			}
			
			invoiceResponse.setInvoiceDetail(listInvoiceDetail);
			invoiceResponse.setCreatedAt(invoice.getCreateAt());
			invoiceResponses.add(invoiceResponse);
		}
		
		return WebResponse.<List<InvoiceResponseDto>>builder().message("ok").data(invoiceResponses).build();
	}
	
}
