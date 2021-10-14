package com.project.professor.allocation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.professor.allocation.entity.Allocation;
import com.project.professor.allocation.repository.AllocationRepository;

@Service
public class AllocationService {

	@Autowired
	private AllocationRepository allocationRepo;
	@Autowired
	private ProfessorService professorService;
	@Autowired
	private CourseService courseService;

	public List<Allocation> findAll() {
		List<Allocation> allocations = allocationRepo.findAll();
		return allocations;
	}

	public Allocation findById(Long id) {
		Allocation allocations = allocationRepo.findById(id).orElse(null);
		return allocations;
	}
	
	public List<Allocation> findByProfessor(Long professorId){
		return allocationRepo.findByProfessorId(professorId);
	}
	
	public List<Allocation> findByCourse(Long courseId){
		return allocationRepo.findByCourseId(courseId);
	}

	public Allocation update(Allocation allocation) {
		Long id = allocation.getId();
		if (id != null && allocationRepo.existsById(id)) {
			return allocationRepo.save(id);
		}
		return null;

	}

	public void deleteId(Long id) {
		if (id != null && allocationRepo.existsById(id)) {
			allocationRepo.deleteById(id);

		}
	}

}
