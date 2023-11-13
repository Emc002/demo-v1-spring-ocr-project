package com.lawencon.ocr.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.ocr.dto.WebResponse;
import com.lawencon.ocr.dto.request.LetterRequestDto;
import com.lawencon.ocr.model.Letter;
import com.lawencon.ocr.repository.LetterRepository;
import com.lawencon.ocr.service.LetterService;

@Service
public class LetterServiceImpl implements LetterService {
	
	@Autowired
	LetterRepository letterRepository;

	@Override
	public WebResponse<String> save(LetterRequestDto letterRequest) {
		final LocalDateTime timeNow = LocalDateTime.now();
		Letter letter = new Letter();
		letter.setId(UUID.randomUUID().toString());
		letter.setNomor(letterRequest.getNomor());
		letter.setTanggal(letterRequest.getTanggal());
		letter.setKlasifikasi(letterRequest.getKlasifikasi());
		letter.setLampiran(letterRequest.getLampiran());
		letter.setPerihal(letterRequest.getPerihal());
		letter.setNama(letterRequest.getNama());
		letter.setJenis_kelamin(letterRequest.getJenis_kelamin());
		letter.setTempat_tanggal_lahir(letterRequest.getTempat_tanggal_lahir());
		letter.setAlamat(letterRequest.getAlamat());
		letter.setPekerjaan(letterRequest.getPekerjaan());
		letter.setKewarganegaraan(letterRequest.getKewarganegaraan());
		letter.setAgama(letterRequest.getAgama());
		letter.setCreateAt(timeNow);
		letter.setUpdateAt(timeNow);
		letterRepository.save(letter);
		return WebResponse.<String>builder().message("ok").data("insert successfully").build();
	}

	@Override
	public List<Letter> getListLetterDownload() {
		return letterRepository.findAll();
	}

	@Override
	public WebResponse<List<Letter>> getTheListLetter() {
		final List<Letter> letter = letterRepository.findAll();
		return WebResponse.<List<Letter>>builder().message("ok").data(letter).build();
	}

	
}
