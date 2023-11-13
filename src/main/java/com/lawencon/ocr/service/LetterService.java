package com.lawencon.ocr.service;

import java.util.List;

import com.lawencon.ocr.dto.WebResponse;
import com.lawencon.ocr.dto.request.LetterRequestDto;
import com.lawencon.ocr.model.Letter;

public interface LetterService {
	public WebResponse<String> save(LetterRequestDto letterRequest);
	
	public List<Letter> getListLetterDownload();
	
	public WebResponse<List<Letter>> getTheListLetter();
}
