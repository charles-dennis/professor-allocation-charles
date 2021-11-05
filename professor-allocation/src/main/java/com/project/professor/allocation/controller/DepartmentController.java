package com.project.professor.allocation.controller;

import java.util.List;

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

import com.project.professor.allocation.entity.Department;
import com.project.professor.allocation.service.DepartmentService;

@RestController
@RequestMapping(path="/departments")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping(path= "/{department_id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Department> findById(@PathVariable(name = "department_id") Long id) {
		Department department = departmentService.findById(id);
		if(department == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
			return new ResponseEntity<>(department, HttpStatus.OK);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Department>> findAll(@RequestParam(name = "name", required = false) String name){
		List<Department> depts = departmentService.findAll(name);
		return new ResponseEntity<>(depts,HttpStatus.OK);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Department> create(@RequestBody Department department){
		
		try {
			Department depart = departmentService.save(department);
			return new ResponseEntity<>(depart,HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@PutMapping(path = "/{department_id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Department> update(@PathVariable(name = "department_id") Long id, @RequestBody Department depto){
		try {
			depto.setId(id);
			Department dept = departmentService.update(depto);
			if(depto == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity<>(dept, HttpStatus.OK);				
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(path = "/{department}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteById(@PathVariable(name = "department") Long id){
		departmentService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
}
