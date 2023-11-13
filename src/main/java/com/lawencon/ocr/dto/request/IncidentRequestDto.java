package com.lawencon.ocr.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncidentRequestDto {

	private String nomor;
	private String nama;
	private String no_telp;
	private String nik;
	private String apa_yang_terjadi;
	private String terlapor;
}
