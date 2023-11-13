package com.lawencon.ocr.service;

import java.util.List;

import com.lawencon.ocr.dto.WebResponse;
import com.lawencon.ocr.dto.request.InvoiceRequestDto;
import com.lawencon.ocr.dto.response.InvoiceResponseDto;

public interface InvoiceService {

	WebResponse<String> save(InvoiceRequestDto request);
	
	WebResponse<List<InvoiceResponseDto>> getAll();
	
}
