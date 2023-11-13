package com.lawencon.ocr.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceResponseDto {
	
	private String id;
	
	private String invoiceNo;
	
	private String Total;
	
	private LocalDateTime createdAt;
	
	private List<InvoiceDetailResponseDto> invoiceDetail;

}
