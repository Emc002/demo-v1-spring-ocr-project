package com.lawencon.ocr.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lawencon.ocr.dto.ErrorDto;

@ControllerAdvice
public class ErrorHandlerController {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDto<Map<String, String>>> handleValidation(MethodArgumentNotValidException e) {
		final ErrorDto<Map<String, String>> errorDto = new ErrorDto<>();
		
		Map<String, String> errors = new HashMap<>();
	    e.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    
	    errorDto.setMessage(errors);
	    
	    return new ResponseEntity<ErrorDto<Map<String,String>>>(errorDto, HttpStatus.BAD_REQUEST);
	}
	
}
