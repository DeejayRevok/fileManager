package com.filemanager.springfilemanagerapp.exceptions;

import com.filemanager.springfilemanagerapp.constants.ErrorConstants;

/**
 * File exists exception class
 * @author Sergio Díez
 *
 */
public class FileExistsException extends SpringFileManagerAppException {

	private static final long serialVersionUID = -6158406826797530337L;
	
	private final String errorCode = ErrorConstants.FILE_EXISTS_EXCEPTION_CODE; 

	public FileExistsException() {
		super();
	}
	
	public FileExistsException(String message, Throwable t) {
		super(message, t);
	}
	
	public FileExistsException(String message) {
		super(message);
	}
	
	public FileExistsException(Throwable t) {
		super(t);
	}

	public String getErrorcode() {
		return errorCode;
	}
}
