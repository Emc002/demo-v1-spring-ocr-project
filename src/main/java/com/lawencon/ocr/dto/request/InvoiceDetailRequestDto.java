package com.lawencon.ocr.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceDetailRequestDto {

	private String itemName;

	private String quantity;

	private String unitPrice;

	private String subTotal;

}
