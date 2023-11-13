package com.lawencon.ocr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "letter")
public class Letter extends BaseModel {

	@Column(name = "Nomor")
	private String nomor;
	@Column(name = "Tanggal")
	private String tanggal;
	@Column(name = "Klasifikasi")
	private String klasifikasi;
	@Column(name = "Lampiran")
	private String lampiran;
	@Column(name = "Perihal")
	private String perihal;
	@Column(name = "Nama")
	private String nama;
	@Column(name = "Jenis_Kelamin")
	private String jenis_kelamin;
	@Column(name = "Tempat_/_Tanggal_Lahir")
	private String tempat_tanggal_lahir;
	@Column(name = "Alamat")
	private String alamat;
	@Column(name = "Pekerjaan")
	private String pekerjaan;
	@Column(name = "Kewarganegaraan")
	private String kewarganegaraan;
	@Column(name = "Agama")
	private String agama;

}
