package com.lawencon.ocr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invoice_detail")
public class InvoiceDetail extends BaseModel {

	@Column(name = "item_name")
	private String itemName;
	
	@Column(name = "quantity")
	private String quantity;
	
	@Column(name = "unit_price")
	private String unitPrice;
	
	@Column(name = "sub_total")
	private String subTotal;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;
}
