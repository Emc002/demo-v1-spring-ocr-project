package com.lawencon.ocr.dto;

public class ErrorDto<T> {

	private T message;

	public ErrorDto() {
		super();
	}

	public ErrorDto(T message) {
		super();
		this.message = message;
	}

	public T getMessage() {
		return message;
	}

	public void setMessage(T message) {
		this.message = message;
	}
	
}
