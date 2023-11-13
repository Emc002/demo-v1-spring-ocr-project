package com.lawencon.ocr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "incident")
public class Incident extends BaseModel {

	@Column(name = "nomor")
	private String nomor;
	@Column(name = "nama")
	private String nama;
	@Column(name = "nomor_telp_hp")
	private String no_telp;
	@Column(name = "nik")
	private String nik;
	@Column(name = "apa_yang_terjadi")
	private String apa_yang_terjadi;
	@Column(name = "terlapor")
	private String terlapor;
}
