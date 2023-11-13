package com.lawencon.ocr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "arrest")
public class Arrest extends BaseModel {
	
	@Column(name = "Surat_Perintah_Tugas_Nomor")
	private String suratPerintahTugasNomor;
	@Column(name = "Surat_Perintah_Penangkapan_Nomor")
	private String suratPerintahPenangkapanNomor;
	@Column(name = "Nama_Tersangka")
	private String namaTersangka;

}
