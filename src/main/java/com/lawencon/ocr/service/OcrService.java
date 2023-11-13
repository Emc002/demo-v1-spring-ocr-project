package com.lawencon.ocr.service;

import com.lawencon.ocr.dto.request.FileRequestDto;

public interface OcrService {
	
	Object doOcr(FileRequestDto fileRequestDto, int templateNo);
	
	String[] ocr(FileRequestDto fileRequestDto);

}
