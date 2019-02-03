package com.filemanager.springfilemanagerapp.exceptions;

import com.filemanager.springfilemanagerapp.constants.ErrorConstants;

/**
 * File processing exception class
 * @author Sergio Díez
 *
 */
public class FileProcessingException extends SpringFileManagerAppException {

	private static final long serialVersionUID = -4007669799142781722L;

	private final String errorCode = ErrorConstants.FILE_PROCESSING_EXCEPTION_CODE; 
	
	public FileProcessingException() {
		super();
	}
	
	public FileProcessingException(String message, Throwable t) {
		super(message, t);
	}
	
	public FileProcessingException(String message) {
		super(message);
	}
	
	public FileProcessingException(Throwable t) {
		super(t);
	}

	public String getErrorcode() {
		return errorCode;
	}
}
