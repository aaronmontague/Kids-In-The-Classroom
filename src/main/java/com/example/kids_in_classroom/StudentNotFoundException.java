package com.example.kids_in_classroom;

class StudentNotFoundException extends RuntimeException {

	StudentNotFoundException(Integer id) {
		super("Could not find student record " + id);
	}
}
