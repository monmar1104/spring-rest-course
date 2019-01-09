package com.monmar.springrest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monmar.springrest.entity.Student;
import com.monmar.springrest.entity.StudentErrorResponse;
import com.monmar.springrest.exception.StudentNotFoundException;

@RestController
@RequestMapping("/api")
public class StudentRestController {
	
	List<Student> students;
	
	@PostConstruct
	public void loadData() {
		students = new ArrayList<Student>();
		students.add(new Student("Marcin", "Pastwa"));
		students.add(new Student("Monika", "Pastwa"));
		students.add(new Student("Marta", "Pastwa"));
		students.add(new Student("Maciej", "Pastwa"));
		students.add(new Student("Milena", "Pastwa"));
		students.add(new Student("Małgosia", "Pastwa"));
	}
	
	@GetMapping("/students")
	public List<Student> getStudents() {
		
		return students ;
	}
	
	@GetMapping("/students/{studentId}")
	public Student getStudent(@PathVariable int studentId) {
		
		if(studentId >= students.size() || studentId < 0) {
			throw new StudentNotFoundException("Student id not found - " + studentId);
		}
		return students.get(studentId);
	}
	
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleStudentNotFoundException(StudentNotFoundException exc) {
		
		StudentErrorResponse errorResponse = new StudentErrorResponse();
		
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setMessage(exc.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleException(Exception exc) {
		
		StudentErrorResponse errorResponse = new StudentErrorResponse();
		
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(exc.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
