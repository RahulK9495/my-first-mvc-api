package com.spring.exception;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.spring.model.MyCustomError;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<String>   handleSQLException(Exception ex)
	{
		System.out.println("Inside sql exception..");
		return new ResponseEntity<String>("Database error", HttpStatus.BAD_GATEWAY);
	}

	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<MyCustomError>   handleStudentNotFoundException(Exception ex)
	{
		MyCustomError customError=new MyCustomError();
		customError.setMessage(ex.getMessage());
		customError.setRootCause("Student not found");
		customError.setStatusCode(401);
		
		return new ResponseEntity<MyCustomError>(customError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidStudentException.class)
	public ResponseEntity<MyCustomError> handleInvalidStudentNameEception(Exception ex)
	{
		MyCustomError customError=new MyCustomError();
		customError.setMessage(ex.getMessage());
		customError.setRootCause("Invalid student name");
		customError.setStatusCode(400);
		
		return new ResponseEntity<MyCustomError>(customError, HttpStatus.BAD_REQUEST);
	}

}
