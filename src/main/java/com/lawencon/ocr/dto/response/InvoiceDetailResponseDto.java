package com.lawencon.ocr.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceDetailResponseDto {
	
	private String id;
	
	private String itemName;
	
	private String quantity;
	
	private String unitPrice;
	
	private String subTotal;

}
