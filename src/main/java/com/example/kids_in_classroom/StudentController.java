package com.example.kids_in_classroom;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class StudentController {

	private final StudentRepository repository;

	StudentController(StudentRepository repository) {
		this.repository = repository;
	}

	// CRUD End Points
	@GetMapping("/students")
	Resources<Resource<Student>> getAllStudents() {
		List<Resource<Student>> listOfAllStudents = repository.findAll().stream()
				.map(oneStudentInList -> new Resource<>(oneStudentInList,
					linkTo(methodOn(StudentController.class).getSingleStudentById(oneStudentInList.studentId)).withSelfRel(),
					linkTo(methodOn(StudentController.class).getAllStudents()).withRel("students")))
				.collect(Collectors.toList());
		
		return new Resources<>(listOfAllStudents,
				linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel()
				);
				
	}
	
	/*@GetMapping("/students")
	List<Student> getAllStudents() {
		return repository.findAll();
	}*/
	
	@PostMapping("/students")
	Resource<Student> createNewStudent(@RequestBody Student newStudent) {
		// TODO add automated studentId creation
		if (newStudent.studentId == null) {
			newStudent.studentId = 9876543;
		}
		// Set activeRecord to true
		newStudent.setActiveRecordTrue();
		
		Student postedStudent = repository.save(newStudent);
		
		return new Resource<>(postedStudent,
				linkTo(methodOn(StudentController.class).getSingleStudentById(postedStudent.studentId)).withSelfRel(),
				linkTo(methodOn(StudentController.class).getAllStudents()).withRel("students")
				);
	}

	/*@PostMapping("/students")
	Student createNewStudent(@RequestBody Student newStudent) {
		return repository.save(newStudent);
	}*/
	
	// The follow mapping is a RESTful mapping
	@GetMapping("/students/{id}")
	Resource<Student> getSingleStudentById(@PathVariable Integer id) {

		Student singleStudent = repository.findById(id)
			.orElseThrow(() -> new StudentNotFoundException(id));
		
		return new Resource<>(singleStudent,
				linkTo(methodOn(StudentController.class).getSingleStudentById(id)).withSelfRel(),
				linkTo(methodOn(StudentController.class).getAllStudents()).withRel("students")
				);
	}
	/* We know the following works
	@GetMapping("/students/{id}")
	Student getSingleStudentById(@PathVariable Integer id) {

		return repository.findById(id)
			.orElseThrow(() -> new StudentNotFoundException(id));
	}*/

	@PutMapping("/students/{id}")
	Resource<Student> replaceOrCreateStudent(@RequestBody Student newStudent, @PathVariable Integer id) {

		Student putStudent = repository.findById(id)
			// Update Existing Student
			.map(student -> {
				if (newStudent.firstName != null)
					student.firstName = newStudent.firstName;
				if (newStudent.lastName != null)
					student.lastName = newStudent.lastName;
				if (newStudent.getGraduationYear() != -33333)
					student.setGraduationYear(newStudent.getGraduationYear());
				if (newStudent.getMiddleName() != null)
					student.setMiddleName(newStudent.getMiddleName());
				
				// Set activeRecord to true
				newStudent.setActiveRecordTrue();
				
				return repository.save(student);
			
			})
			// Create new Student with passed Id
			.orElseGet(() -> {
				newStudent.studentId = id;
				
				// Set activeRecord to true
				newStudent.setActiveRecordTrue();
				
				return repository.save(newStudent);
			});
		//PUT updates occur outside the mapping
		return new Resource<>(putStudent,
				linkTo(methodOn(StudentController.class).getSingleStudentById(id)).withSelfRel(),
				linkTo(methodOn(StudentController.class).getAllStudents()).withRel("students")
				);
	}

	//TODO Change delete to MoveToInactive
	@DeleteMapping("/students/{id}")
	void deleteStudentById(@PathVariable Integer id) {
		repository.deleteById(id);
	}
	
	// Query Endpoints
	
	@GetMapping("/students/fname/{firstName}")	
	Resources<Resource<Student>> getAllStudentsByFirstName(@PathVariable String firstName) {
		List<Resource<Student>> listOfAllStudentsByFirstName = repository.findByFirstName(firstName).stream()
				.map(oneStudentInList -> new Resource<>(oneStudentInList,
					linkTo(methodOn(StudentController.class).getSingleStudentById(oneStudentInList.studentId)).withSelfRel(),
					linkTo(methodOn(StudentController.class).getAllStudents()).withRel("students")))
				.collect(Collectors.toList());
		
		return new Resources<>(listOfAllStudentsByFirstName,
				linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel()
				);	
	}
	
	@GetMapping("/students/lname/{lastName}")
	Resources<Resource<Student>> getAllStudentsByLastName(@PathVariable String lastName) {
		List<Resource<Student>> listOfAllStudentsByLastName = repository.findByLastName(lastName).stream()
				.map(oneStudentInList -> new Resource<>(oneStudentInList,
					linkTo(methodOn(StudentController.class).getSingleStudentById(oneStudentInList.studentId)).withSelfRel(),
					linkTo(methodOn(StudentController.class).getAllStudents()).withRel("students")))
				.collect(Collectors.toList());
		
		return new Resources<>(listOfAllStudentsByLastName,
				linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel()
				);	
	}
}
