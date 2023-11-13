package com.lawencon.ocr.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice extends BaseModel{

	@Column(name = "invoice_no")
	private String invoiceNo;
	
	@Column(name = "total")
	private String total;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "invoice")
	private List<InvoiceDetail> invoiceDetail;
}
