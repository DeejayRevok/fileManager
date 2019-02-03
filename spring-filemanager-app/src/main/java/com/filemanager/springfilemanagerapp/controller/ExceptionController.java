package com.filemanager.springfilemanagerapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.filemanager.springfilemanagerapp.constants.ErrorConstants;
import com.filemanager.springfilemanagerapp.domain.ErrorBody;
import com.filemanager.springfilemanagerapp.exceptions.SpringFileManagerAppException;
import com.filemanager.springfilemanagerapp.utils.GenericBuilder;

/**
 * Controller advice to intercept exceptions in the applications and translate it to a generic response
 * @author Sergio Díez
 *
 */
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(value = IllegalArgumentException.class)
	protected ResponseEntity<ErrorBody> handleIllegalArguments(IllegalArgumentException ex) {
		logException(ex);
		ErrorBody error = new GenericBuilder<>(ErrorBody::new)
				.with(ErrorBody::setErrorCode, ErrorConstants.ILLEGAL_ARGUMENT_EXCEPTION_CODE)
				.with(ErrorBody::setErrorMessage, ex.getMessage())
				.with(ErrorBody::setEx, ex)
				.build();
		return new ResponseEntity<ErrorBody>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = SpringFileManagerAppException.class)
	protected ResponseEntity<ErrorBody> handleAppException(SpringFileManagerAppException ex) {
		logException(ex);
		ErrorBody error = new GenericBuilder<>(ErrorBody::new)
				.with(ErrorBody::setErrorCode, ex.getErrorcode())
				.with(ErrorBody::setErrorMessage, ex.getMessage())
				.with(ErrorBody::setEx, ex)
				.build();
		return new ResponseEntity<ErrorBody>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = Exception.class)
	protected ResponseEntity<ErrorBody> handleUnknownException(Exception ex) {
		logException(ex);
		ErrorBody error = new GenericBuilder<>(ErrorBody::new)
				.with(ErrorBody::setErrorCode, ErrorConstants.GENERIC_EXCEPTION_CODE)
				.with(ErrorBody::setErrorMessage, ex.getMessage())
				.with(ErrorBody::setEx, ex)
				.build();
		return new ResponseEntity<ErrorBody>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private void logException(Exception ex) {
		log.error("Handling exception ",ex);
	}

}
