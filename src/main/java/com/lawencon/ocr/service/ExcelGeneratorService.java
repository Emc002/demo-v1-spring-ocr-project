package com.lawencon.ocr.service;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletResponse;

public interface ExcelGeneratorService {
	public HashMap<String, String> generateExcelFile(int templateNo, HttpServletResponse response) throws IOException;
}
