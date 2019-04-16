package com.example.kids_in_classroom;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Student {
	// Fields
	public String firstName;
	public String lastName;
	// Integer is the object version of int
	// Add  @GeneratedValue to auto increment ID
	public @Id Integer studentId;
	private int graduationYear;
	// Test of get/set
	private String middleName;
	private Boolean activeRecord;
	
	// Constructors
	public Student() {}
	public Student(String fn, String ln, int id, int gradYear) {
		firstName = fn;
		lastName = ln;
		studentId = id;
		this.setGraduationYear(gradYear);
		setActiveRecord(true);
	}
	
	// Methods
	public int getGraduationYear() {
		return graduationYear;
	}
	public void setGraduationYear(int graduationYear) {
		this.graduationYear = graduationYear;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public Boolean getActiveRecord() {
		return activeRecord;
	}
	public void setActiveRecord(Boolean activeRecord) {
		this.activeRecord = activeRecord;
	}
	public void setActiveRecordTrue() {
		this.activeRecord = true;
	}
	public void setActiveRecordFalse() {
		this.activeRecord = false;
	}
	
}