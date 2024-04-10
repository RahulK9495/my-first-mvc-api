package com.spring.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.entity.Student;


public interface StudentDao {

	public Boolean saveStudent(Student student);

	public List<Student> getAllStudent();

	public Student getStudentById(int id);

	public List<Student> getAllStudentSearchBy(String city);

	public List<Student> finaAllBysortedField(String field);

	public boolean updateStudentById(Student student);

	public Boolean deleteStudentById(int id) throws SQLException;

}
