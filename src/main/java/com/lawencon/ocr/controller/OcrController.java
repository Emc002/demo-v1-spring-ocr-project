package com.lawencon.ocr.controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.ocr.dto.request.FileRequestDto;
import com.lawencon.ocr.dto.request.IncidentRequestDto;
import com.lawencon.ocr.dto.request.InvoiceRequestDto;
import com.lawencon.ocr.dto.request.LetterRequestDto;
import com.lawencon.ocr.service.ExcelGeneratorService;
import com.lawencon.ocr.service.IncidentService;
import com.lawencon.ocr.service.InvoiceService;
import com.lawencon.ocr.service.LetterService;
import com.lawencon.ocr.service.OcrService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/ocr")
public class OcrController<T> {

	@Autowired
	private OcrService ocrService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private IncidentService incidentService;

	@Autowired
	private ExcelGeneratorService excelGeneratorService;

	@Autowired
	private LetterService letterService;

	@PostMapping
	public ResponseEntity<?> ocr(@Valid @RequestBody FileRequestDto request,
			@RequestParam(value = "template", required = true) int templateNo) {
		final var response = ocrService.doOcr(request, templateNo);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("invoices")
	public ResponseEntity<?> invoiceCreate(@Valid @RequestBody InvoiceRequestDto invoiceRequest) {
		final var response = invoiceService.save(invoiceRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("police-report")
	public ResponseEntity<?> policeReportCreate(@Valid @RequestBody IncidentRequestDto incidentRequest) {
		final var response = incidentService.save(incidentRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("letter-report")
	public ResponseEntity<?> letterReportCreate(@Valid @RequestBody LetterRequestDto letterRequest) {
		final var response = letterService.save(letterRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("incident-report")
	public ResponseEntity<?> incidentReportGet() {
		final var response = incidentService.getTheListIncident();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("letter-report")
	public ResponseEntity<?> letterReportGet() {
		final var response = incidentService.getTheListIncident();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("police-report")
	public ResponseEntity<?> policeReportGet() {
		final var response = incidentService.getThePoliceIncident();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("download-excel-report")
	public ResponseEntity<?> excelReportDownload(HttpServletResponse response,
			@RequestParam(value = "template", required = true) int templateNo) throws IOException {
		final HashMap<String, String> responses = excelGeneratorService.generateExcelFile(templateNo, response);
		return new ResponseEntity<>(responses, HttpStatus.OK);
	}

	@GetMapping("invoices")
	public ResponseEntity<?> invoiceGet() {
		final var response = invoiceService.getAll();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
