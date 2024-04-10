package com.spring.service;

import java.sql.SQLException;
import java.util.List;

import com.spring.entity.Student;

public interface StudentService{

	public Boolean saveStudent(Student student);

	public List<Student> getAllStudents();

	public Student getStudentById(int id);

	public List<Student> getAllStudentSearchBy(String city);

	public List<Student> findAllBySortedField(String field);

	public boolean updateStudentById(Student student);

	public Boolean deleteStudentById(int id) throws SQLException;


}
