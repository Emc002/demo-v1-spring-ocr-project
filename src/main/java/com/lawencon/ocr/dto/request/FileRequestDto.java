package com.lawencon.ocr.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRequestDto {
	
	@NotNull
	@NotBlank
	private String base64;
	
	@NotNull
	@NotBlank
	private String fileType;

}
