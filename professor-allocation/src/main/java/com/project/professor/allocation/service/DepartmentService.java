package com.project.professor.allocation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.professor.allocation.repository.AllocationRepository;

@Service
public class DepartmentService {
	
	@Autowired
	private AllocationRepository allocationRepo;

}
