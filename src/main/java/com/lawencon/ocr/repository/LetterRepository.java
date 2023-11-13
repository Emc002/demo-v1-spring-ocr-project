package com.lawencon.ocr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.ocr.model.Letter;

@Repository
public interface LetterRepository extends JpaRepository<Letter, String> {

}
