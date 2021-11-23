package cundi.edu.co.demo.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionHandlerPer extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ModelNotFoundException.class)
	public final ResponseEntity<ExceptionWrapper> handlerModelNotFoundException(ModelNotFoundException e, WebRequest request){
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.NOT_FOUND,  HttpStatus.NOT_FOUND.toString(), e.getMessage(),  request.getDescription(false));
		return new ResponseEntity<ExceptionWrapper>(ew, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ArithmeticException.class)
	public final ResponseEntity<ExceptionWrapper> handlerModelArithmeticException(ArithmeticException e, WebRequest request){
		e.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Error aritmetico", request.getDescription(false));
		return new ResponseEntity<ExceptionWrapper>(ew, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@ExceptionHandler(IntegridadException.class)
	public final ResponseEntity<ExceptionWrapper> handlerIntegridadException(IntegridadException e, WebRequest request){
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.CONFLICT,  HttpStatus.CONFLICT.toString(), "El correo ya esta registrado",  request.getDescription(false));
		return new ResponseEntity<ExceptionWrapper>(ew, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionWrapper> handlerModelException(Exception e, WebRequest request){
		e.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Ha ocurrido un error", request.getDescription(false));
		return new ResponseEntity<ExceptionWrapper>(ew, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.UNSUPPORTED_MEDIA_TYPE, HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(ew, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(ew, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED.toString(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(ew, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(ew, HttpStatus.BAD_REQUEST);	
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		List<String> details = new ArrayList<>();
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
	    }
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), details.toString(), request.getDescription(false));
		return new ResponseEntity<Object>(ew, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(ew, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.toString(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(ew, HttpStatus.NOT_FOUND);
	}		
} 