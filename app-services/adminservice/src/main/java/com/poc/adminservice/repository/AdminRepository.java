package com.poc.adminservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.adminservice.model.Template;

@Repository
public interface AdminRepository extends JpaRepository<Template, Integer> {
	
	Template findByStatus(String status);

}
