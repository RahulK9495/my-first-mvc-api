package com.spring.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.StudentDao;
import com.spring.entity.Student;
import com.spring.exception.InvalidStudentException;

@Service
public class StudentServiceImp implements StudentService {

	@Autowired
	StudentDao studentDao;

	@Override
	public Boolean saveStudent(Student student) {

		if (validateStudent(student)) {
			return studentDao.saveStudent(student);
		} else {
			return false;
		}

	}

	@Override
	public List<Student> getAllStudents() {

		return studentDao.getAllStudent();
	}

	public Student getStudentById(int id) {

		return studentDao.getStudentById(id);
	}

	@Override
	public List<Student> getAllStudentSearchBy(String city) {
		// TODO Auto-generated method stub
		return studentDao.getAllStudentSearchBy(city);
	}

	@Override
	public List<Student> findAllBySortedField(String field) {
		// TODO Auto-generated method stub
		return studentDao.finaAllBysortedField(field);
	}

	@Override
	public boolean updateStudentById(Student student) {
		// TODO Auto-generated method stub
		return studentDao.updateStudentById(student);
	}

	@Override
	public Boolean deleteStudentById(int id) throws SQLException {

		return studentDao.deleteStudentById(id);
	}

	private Boolean validateStudent(Student student) {
		if (student.getName().length() <= 3) {
			throw new InvalidStudentException("Invalid student");
		} else
			return true;
	}
}
