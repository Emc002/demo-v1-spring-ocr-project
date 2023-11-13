package com.lawencon.ocr.service;

import java.util.List;
import java.util.Map;

import com.lawencon.ocr.dto.WebResponse;
import com.lawencon.ocr.dto.request.IncidentRequestDto;
import com.lawencon.ocr.model.Incident;

public interface IncidentService<BaseEntity> {

	public WebResponse<String> save(IncidentRequestDto incidentRequest);
	
	public List<Incident> getListIncidentDownload();

	WebResponse<List<Incident>> getTheListIncident();
	
	WebResponse<Map<String, List<? extends BaseEntity>>> getThePoliceIncident();
}
