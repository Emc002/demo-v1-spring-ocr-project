package com.lawencon.ocr.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {

	@Id
	@Column(length = 36)
	private String id;
	
	@Column(name = "created_at")
	private LocalDateTime createAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updateAt;

}
