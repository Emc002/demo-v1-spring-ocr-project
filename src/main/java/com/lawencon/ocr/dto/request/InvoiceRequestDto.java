package com.lawencon.ocr.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRequestDto {
	
	private String invoiceNo;

	private String total;

	private List<InvoiceDetailRequestDto> invoiceDetail;
	
}
