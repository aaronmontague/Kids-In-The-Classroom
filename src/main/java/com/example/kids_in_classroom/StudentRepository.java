package com.example.kids_in_classroom;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

interface StudentRepository extends JpaRepository<Student, Integer>{
	
	List<Student> findByFirstName(String firstName);
	List<Student> findByLastName(String lastName);
	// Student findByStudentId(int studentId);
	
}
