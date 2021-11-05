package com.project.professor.allocation.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.professor.allocation.entity.Allocation;
import com.project.professor.allocation.entity.Professor;
import com.project.professor.allocation.service.AllocationService;

import io.swagger.models.Response;

@RestController
@RequestMapping(path = "/allocations")
public class AllocationController {

	@Autowired
	private AllocationService allocationService;

	@GetMapping(path = "/{allocation_id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Allocation> findById(@PathVariable(name = "allocation_id") Long id) {
		Allocation allocation = allocationService.findById(id);
		if (allocation == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(allocation, HttpStatus.OK);
		}
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Allocation>> findAll(@RequestParam(name = "name", required = false) String name) {
		List<Allocation> alocAll = allocationService.findAll();
		return new ResponseEntity<>(alocAll, HttpStatus.OK);
	}

	@GetMapping(path = "/professor/{professor_id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Allocation>> findByProfessor(@PathVariable(name = "professor_id") Long id) {
		List<Allocation> aloc = allocationService.findByProfessor(id);
		return new ResponseEntity<>(aloc, HttpStatus.OK);
	}

	@GetMapping(path = "/course/{course_id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Allocation>> findByCourse(@PathVariable(name = "course_id") Long id) {
		List<Allocation> aloc = allocationService.findByCourse(id);
		return new ResponseEntity<>(aloc, HttpStatus.OK);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Allocation> create(@RequestBody Allocation allocation) {
		try {
			Allocation aloc = allocationService.save(allocation);
			return new ResponseEntity<>(aloc, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping(path = "/{allocation_id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Allocation> update(@PathVariable(name = "allocation_id") Long id, @RequestBody Allocation allocation) {
		try {
			allocation.setId(null);
			if (allocation == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@DeleteMapping(path = "/{allocation}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteById(@PathVariable(name = "allocation") Long id) {
		allocationService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

}
