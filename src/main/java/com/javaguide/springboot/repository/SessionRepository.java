package com.javaguide.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.javaguide.springboot.model.Seesions;

public interface SessionRepository extends JpaRepository<Seesions, String>{
	@Transactional
	@Modifying   
	@Query(value="update sessions set token = ?1, phone = ?2, status = ?3 where phone = ?2", nativeQuery = true)
	void setSeassonInfoById(String token, String phone, String status);
}
