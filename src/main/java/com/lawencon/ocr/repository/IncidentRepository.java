package com.lawencon.ocr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.ocr.model.Incident;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, String>{
}
