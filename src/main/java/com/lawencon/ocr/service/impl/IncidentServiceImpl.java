package com.lawencon.ocr.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.ocr.dto.WebResponse;
import com.lawencon.ocr.dto.request.IncidentRequestDto;
import com.lawencon.ocr.model.BaseModel;
import com.lawencon.ocr.model.Incident;
import com.lawencon.ocr.model.Letter;
import com.lawencon.ocr.repository.IncidentRepository;
import com.lawencon.ocr.repository.LetterRepository;
import com.lawencon.ocr.service.IncidentService;

import jakarta.transaction.Transactional;

@Service
public class IncidentServiceImpl<BaseEntity> implements IncidentService {
	@Autowired
	private IncidentRepository incidentRepository;
	
	@Autowired
	private LetterRepository letterRepository;

	@Override
	@Transactional
	public WebResponse<String> save(IncidentRequestDto incidentRequest) {
		final LocalDateTime timeNow = LocalDateTime.now();
		Incident incident = new Incident();
		incident.setId(UUID.randomUUID().toString());
		incident.setNomor(incidentRequest.getNomor());
		incident.setNama(incidentRequest.getNama());
		incident.setNo_telp(incidentRequest.getNo_telp());
		incident.setNik(incidentRequest.getNik());
		incident.setApa_yang_terjadi(incidentRequest.getApa_yang_terjadi());
		incident.setTerlapor(incidentRequest.getTerlapor());
		incident.setCreateAt(timeNow);
		incident.setUpdateAt(timeNow);
		incidentRepository.save(incident);
		return WebResponse.<String>builder().message("ok").data("insert successfully").build();
	}

	@Override
	public WebResponse<List<Incident>> getTheListIncident() {
		final List<Incident> incident = incidentRepository.findAll();
		return WebResponse.<List<Incident>>builder().message("ok").data(incident).build();
	}

	@Override
	public List<Incident> getListIncidentDownload() {
		return incidentRepository.findAll();
	}

	@Override
	public WebResponse<Map<String, List<? extends BaseEntity>>> getThePoliceIncident() {
	    final List<Incident> incidents = incidentRepository.findAll();
	    final List<Letter> letters = letterRepository.findAll();

	    Map<String, List<? extends BaseEntity>> dataMap = new HashMap<>();
	    dataMap.put("incidents", (List<? extends BaseEntity>) incidents);
	    dataMap.put("letters", (List<? extends BaseEntity>) letters);

	    return WebResponse.<Map<String, List<? extends BaseEntity>>>builder()
	            .message("ok")
	            .data(dataMap)
	            .build();
	}
//	public WebResponse<List<Incident>> getThePoliceIncident() {
//		final List<Incident> incident = incidentRepository.findAll();
//		final List<Letter> letter = letterRepository.findAll();
//		return WebResponse.<List<Incident>>builder().message("ok").data(incident).build();
//	}
	
//	public Map<String, List<? extends BaseEntity>> getThePoliceIncident() {
//	    @SuppressWarnings("unchecked")
//		final List<? extends BaseEntity> incidents = (List<? extends BaseEntity>) incidentRepository.findAll();
//	    @SuppressWarnings("unchecked")
//		final List<? extends BaseEntity> letters = (List<? extends BaseEntity>) letterRepository.findAll();
//
//	    Map<String, List<? extends BaseEntity>> dataMap = new HashMap<>();
//	    dataMap.put("incidents", incidents);
//	    dataMap.put("letters", letters);
//
//	    return dataMap;
//	}
	
//	public Map<String, List<Map<String, Object>>> getThePoliceIncident() {
//	    final List<Incident> incidents = incidentRepository.findAll();
//	    final List<Letter> letters = letterRepository.findAll();
//
//	    List<Map<String, Object>> dataList = new ArrayList<>();
//
//	    for (Incident incident : incidents) {
//	        dataList.add(convertToMapIncident(incident));
//	    }
//
//	    for (Letter letter : letters) {
//	        dataList.add(convertToMapLetter(letter));
//	    }
//
//	    Map<String, List<Map<String, Object>>> dataMap = new HashMap<>();
//	    dataMap.put("data", dataList);
//
//	    return dataMap;
//	}
//
//	private Map<String, Object> convertToMapIncident(Incident incident) {
//		Map<String, Object> map = new HashMap<>();
//		final LocalDateTime timeNow = LocalDateTime.now();
//		map.put("id",UUID.randomUUID().toString());
//		map.put("nomor",incident.getNomor());
//		map.put("nama",incident.getNama());
//		map.put("nomorTelpHp",incident.getNomorTelpHp());
//		map.put("nik",incident.getNik());
//		map.put("apaYangTerjadi",incident.getApaYangTerjadi());
//		map.put("terlapor",incident.getTerlapor());
//		map.put("createAt",timeNow);
//		map.put("updateAt",timeNow);
//	    return map;
//	}
//	
//	private Map<String, Object> convertToMapLetter(Letter letter) {
//		Map<String, Object> map = new HashMap<>();
//		final LocalDateTime timeNow = LocalDateTime.now();
//		map.put("id",UUID.randomUUID().toString());
//		map.put("nomor",letter.getNomor());
//		map.put("tanggal",letter.getTanggal());
//		map.put("klasifikasi",letter.getKlasifikasi());
//		map.put("lampiran",letter.getLampiran());
//		map.put("perihal",letter.getPerihal());
//		map.put("nama",letter.getNama());
//		map.put("jenisKelamin",letter.getJenisKelamin());
//		map.put("tempatTanggalLahir",letter.getTempatTanggalLahir());
//		map.put("alamat",letter.getAlamat());
//		map.put("pekerjaan",letter.getPekerjaan());
//		map.put("kewarganegaraan",letter.getKewarganegaraan());
//		map.put("agama",letter.getAgama());
//		map.put("createAt",timeNow);
//		map.put("updateAt",timeNow);
//	    return map;
//	}



}
