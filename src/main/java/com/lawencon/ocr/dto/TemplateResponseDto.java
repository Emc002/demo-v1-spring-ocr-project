package com.lawencon.ocr.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateResponseDto {
	
	private String[] headerColumns;

	private String headerData;
	
	private List<String[]> DataTable;
	
	private String footerData;

}
