package com.project.professor.allocation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.professor.allocation.entity.Allocation;
import com.project.professor.allocation.entity.Course;
import com.project.professor.allocation.entity.Professor;
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
        return allocationRepo.findAll();
    }

    public Allocation findById(Long id) {
        return allocationRepo.findById(id).orElse(null);
    }

    public List<Allocation> findByProfessor(Long professorId) {
        return allocationRepo.findByProfessorId(professorId);
    }

    public List<Allocation> findByCourse(Long courseId) {
        return allocationRepo.findByCourseId(courseId);
    }

    public Allocation save(Allocation allocation) {
        allocation.setId(null);
        return saveInternal(allocation);
    }

    public Allocation update(Allocation allocation) {
        Long id = allocation.getId();
        if (id != null && allocationRepo.existsById(id)) {
            return saveInternal(allocation);
        } else {
            return null;
        }
    }

    public void deleteById(Long id) {
        if (id != null && allocationRepo.existsById(id)) {
            allocationRepo.deleteById(id);
        }
    }

    public void deleteAll() {
        allocationRepo.deleteAllInBatch();
    }

    private Allocation saveInternal(Allocation allocation) {
        if (!isEndHourGreaterThanStartHour(allocation) || hasCollision(allocation)) {
            throw new RuntimeException();
        } else {
            allocation = allocationRepo.save(allocation);

            Professor professor = professorService.findById(allocation.getProfessorId());
            allocation.setProfessor(professor);

            Course course = courseService.findById(allocation.getCourseId());
            allocation.setCourse(course);

            return allocation;
        }
    }

    boolean isEndHourGreaterThanStartHour(Allocation allocation) {
        return allocation != null && allocation.getStart() != null && allocation.getEnd() != null
                && allocation.getEnd().compareTo(allocation.getStart()) > 0;
    }

    boolean hasCollision(Allocation newAllocation) {
        boolean hasCollision = false;

        List<Allocation> currentAllocations = allocationRepo.findByProfessorId(newAllocation.getProfessorId());

        for (Allocation currentAllocation : currentAllocations) {
            hasCollision = hasCollision(currentAllocation, newAllocation);
            if (hasCollision) {
                break;
            }
        }

        return hasCollision;
    }

    private boolean hasCollision(Allocation currentAllocation, Allocation newAllocation) {
        return !currentAllocation.getId().equals(newAllocation.getId())
                && currentAllocation.getDay() == newAllocation.getDay()
                && currentAllocation.getStart().compareTo(newAllocation.getEnd()) < 0
                && newAllocation.getStart().compareTo(currentAllocation.getEnd()) < 0;
    }

}
