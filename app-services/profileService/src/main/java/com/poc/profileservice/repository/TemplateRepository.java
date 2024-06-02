package com.poc.profileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.profileservice.model.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer> {

	Template findByStatus(String status);
}

