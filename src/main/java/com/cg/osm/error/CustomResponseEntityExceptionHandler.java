package com.cg.osm.error;

import java.util.Date;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ CustomerNotFoundException.class })
	public final ResponseEntity<?> handleUserNotFoundException(CustomerNotFoundException ex, WebRequest req) {
		System.out.println("Inside ResponseEntityExceptionHandler() of CustomerNotFoundException");
		ExceptionResponse expResp = new ExceptionResponse(new Date(), ex.getMessage(),
				"The Customer details requested are not present");
		return new ResponseEntity<Object>(expResp, HttpStatus.NOT_FOUND);

	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		System.out.println("Inside handleTypeMismatch()");
		ExceptionResponse expRes = new ExceptionResponse(new Date(), "Validation Failed", ex.getErrorCode());
		return new ResponseEntity<Object>(expRes, HttpStatus.BAD_REQUEST); // 400
	}

	@ExceptionHandler({ SweetOrderNotFoundException.class }) // just like catch(SweetOrderNotFoundException e)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ExceptionResponse handleSweetOrderNotFoundException(SweetOrderNotFoundException ex, WebRequest req) {
		System.out.println("inside handleSweetOrderNotFoundException() ");
		ExceptionResponse expResp = new ExceptionResponse(new Date(), "There is no order found with the given details",
				ex.getMessage());
		return expResp;
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		System.out.println("inside handleMethodArgumentNotValid()");
		ExceptionResponse expRes = new ExceptionResponse(new Date(), ex.getMessage().toString(), "Argument not found");
		return new ResponseEntity<Object>(expRes, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ProductNotFoundException.class })
	public final ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex, WebRequest rq) {
		System.out.println("Inside ResponseEntityExceptionHandler() of ProductNotFoundException");
		ExceptionResponse expResp = new ExceptionResponse(new Date(),
				"Invalid Product Details/The product which you are trying to fetch is not available", ex.getMessage());
		return new ResponseEntity<Object>(expResp, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ CategoryNotFoundException.class })
	public final ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException ex, WebRequest rq) {
		System.out.println("Inside ResponseEntityExceptionHandler() of CategoryNotFoundException");
		ExceptionResponse expResp = new ExceptionResponse(new Date(),
				"Invalid Category Details/The category which you are trying to fetch is not available",
				ex.getMessage());
		return new ResponseEntity<Object>(expResp, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest req) {

		ExceptionResponse expResp = new ExceptionResponse(new Date(), ex.getMessage(), "General Exception");
		return new ResponseEntity<Object>(expResp, HttpStatus.BAD_REQUEST);
	}

}
