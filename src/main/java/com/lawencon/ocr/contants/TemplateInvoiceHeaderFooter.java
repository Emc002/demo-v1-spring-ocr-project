package com.lawencon.ocr.contants;

import lombok.Getter;

@Getter
public enum TemplateInvoiceHeaderFooter {
	TEMPLATE_INVOICE_A("Nama Jumlah Harga_Satuan Sub_total", "sub total", "invoice # ", "total:"),
	TEMPLATE_INVOICE_B("ITEMS UNIT_PRICE QTY TOTAL", "sub total", "invoice no:", "grand total"),
	TEMPLATE_INVOICE_C("Nomor Nama No_Telp NIK Apa_Yang_Terjadi Terlapor", "", "", ""),
	TEMPLATE_INVOICE_D(
			"Nomor Tanggal Klasifikasi Lampiran Perihal Nama Jenis_Kelamin Tempat_Tanggal_Lahir Alamat Pekerjaan Kewarganegaraan Agama",
			"", "", "");

	private String headerData;

	private String footerData;

	private String invoiceNoFormat;

	private String invoiceTotalFormat;

	private TemplateInvoiceHeaderFooter(String headerData, String footerData, String invoiceNoFormat,
			String invoiceTotalFormat) {
		this.headerData = headerData;
		this.footerData = footerData;
		this.invoiceNoFormat = invoiceNoFormat;
		this.invoiceTotalFormat = invoiceTotalFormat;
	}
}
