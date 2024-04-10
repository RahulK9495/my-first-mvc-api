package com.spring.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.Student;
import com.spring.exception.StudentNotFoundException;
import com.spring.exception.invalidCityNameException;
import com.spring.model.MyCustomError;
import com.spring.service.StudentService;

@RestController
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@PostMapping(value = "/students")
//	@ResponseBody
	public ResponseEntity<String> getStudent(@RequestBody Student student)
	{
		System.out.println("Inside getStudent method of controller...");
		Boolean res=studentService.saveStudent(student);
		
		if(res)
			return new ResponseEntity<String>("Student saved successfully..", HttpStatus.CREATED);
		else 
			return new ResponseEntity<String>("Error while saving student", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping(value="/students")
	public List <Student> getAllStudent()
	{
		System.out.println("Inside getall student mapping method of controller");
		List<Student> res=studentService.getAllStudents();
		return res;
	}
	
	@GetMapping(value="/students/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable int id) throws StudentNotFoundException
	{
		System.out.println("Inside get student by id method....");
		

		if(id==0)
			throw new StudentNotFoundException(id);
		
		HttpHeaders header=new HttpHeaders();
		header.set("my header", "header value");
		header.set("1333", "2133");
		return new ResponseEntity<Student>(studentService.getStudentById(id), header, HttpStatus.OK);		
	}
	
	@GetMapping(value="/students/search")
	public List<Student> getAllStudentSearchBy(@RequestParam("city") String city ) throws invalidCityNameException
	{
		System.out.println("Inside get student by search method....");
		
		if (city.length()>=8)
			throw new invalidCityNameException();
		return studentService.getAllStudentSearchBy(city);
	}
	
	@GetMapping(value="/students/sort")
	public List<Student> findAllByfield(@RequestParam("field") String field )
	{
		System.out.println("Inside get student by sort method....");
		return studentService.findAllBySortedField(field);
	}

	@PutMapping(path="/students")
	public ResponseEntity<String> updateStudentById(@RequestBody Student student)
	{
		System.out.println("Inside user updated method of controller..");
		boolean res = studentService.updateStudentById(student);
		if(res)
			return new ResponseEntity<String>("User upadated successfully", HttpStatus.CREATED);

		else
			return new ResponseEntity<String>("Error while updating user", HttpStatus.NOT_FOUND);

		
	}
	@DeleteMapping(value="/students/{id}")
	public ResponseEntity<String> deleteStudentbyId(@PathVariable int id) throws SQLException
	{
		System.out.println("Inside get student by id method....");

		return studentService.deleteStudentById(id) ? new ResponseEntity<String>("student deleted successfully", HttpStatus.NO_CONTENT): new ResponseEntity<String>("Error while deleting student", HttpStatus.NOT_FOUND);
				
	}
	
//	@ExceptionHandler(StudentNotFoundException.class)
//	public ResponseEntity<MyCustomError>  handleStudentNotFoundException ()
//	{
//		System.out.println("Inside exception handler method....");
//		MyCustomError customError=new MyCustomError();
//		customError.setMessage("Student not found");
//		customError.setRootCause("id not found in DB");
//		customError.setStatusCode(401);
//		
//		return new ResponseEntity<MyCustomError>(customError, HttpStatus.BAD_GATEWAY);
//	}
	
	@ExceptionHandler(invalidCityNameException.class)
	public ResponseEntity<MyCustomError>  handleinvalidCityException()
	{
		MyCustomError customError=new MyCustomError();
		customError.setMessage("invalid city name");
		customError.setRootCause("city name is beyond limit");
		customError.setStatusCode(401);
		
//		return customError;
		return new ResponseEntity<MyCustomError>(customError, HttpStatus.BAD_REQUEST);
	}
	
}
