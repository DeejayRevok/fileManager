package com.filemanager.springfilemanagerapp.exceptions;

import com.filemanager.springfilemanagerapp.constants.ErrorConstants;

/**
 * Generi app exception class
 * @author Sergio Díez
 *
 */
public class SpringFileManagerAppException extends Exception {

	private static final long serialVersionUID = 4875829408414617534L;
	
	private final String errorCode = ErrorConstants.GENERIC_EXCEPTION_CODE; 
	
	public SpringFileManagerAppException() {
		super();
	}
	
	public SpringFileManagerAppException(String message, Throwable t) {
		super(message, t);
	}
	
	public SpringFileManagerAppException(String message) {
		super(message);
	}
	
	public SpringFileManagerAppException(Throwable t) {
		super(t);
	}

	public String getErrorcode() {
		return errorCode;
	}

}
