package com.poc.managerService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.managerService.model.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer> {

	Template findByStatus(String status);
}
