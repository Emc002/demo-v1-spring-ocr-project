package com.lawencon.ocr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.ocr.model.Arrest;

@Repository
public interface ArrestRepository extends JpaRepository<Arrest, String> {

}
