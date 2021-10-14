package com.project.professor.allocation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.professor.allocation.entity.Allocation;
import com.project.professor.allocation.entity.Course;
import com.project.professor.allocation.repository.AllocationRepository;
import com.project.professor.allocation.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepo;
	
	public List<Course> findAll(String name){
		if(name == null) {
			return courseRepo.findAll();
		}
		return courseRepo.findByNameContainingIgnoreCase(name);
		
	}
	public Course findById(Long id) {
		return courseRepo.findById(id).orElse(null);		
	}
	public Allocation update(Allocation allocation) {
		Long id = allocation.getId();
		if (id != null && courseRepo.existsById(id)) {
			return courseRepo.save(id);
		}
		return null;

	}

	public void deleteId(Long id) {
		if (id != null && courseRepo.existsById(id)) {
			courseRepo.deleteById(id);

		}
	}

}
