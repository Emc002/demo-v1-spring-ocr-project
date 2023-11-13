package com.lawencon.ocr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.ocr.dto.WebResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class IntrospectController {

	@GetMapping
	public ResponseEntity<?> introspect() {
		final var response = WebResponse.<String>builder().message("running...").data("ocr backend").build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
