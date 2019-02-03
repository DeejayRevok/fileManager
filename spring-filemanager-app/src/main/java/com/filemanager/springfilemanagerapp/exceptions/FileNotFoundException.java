package com.filemanager.springfilemanagerapp.exceptions;

import com.filemanager.springfilemanagerapp.constants.ErrorConstants;

/**
 * File not found expception class
 * @author Sergio Díez
 *
 */
public class FileNotFoundException extends SpringFileManagerAppException {

	private static final long serialVersionUID = -8706594180046681256L;

	private final String errorCode = ErrorConstants.FILE_NOT_FOUND_EXCEPTION_CODE;

	public FileNotFoundException() {
		super();
	}

	public FileNotFoundException(String message, Throwable t) {
		super(message, t);
	}

	public FileNotFoundException(String message) {
		super(message);
	}

	public FileNotFoundException(Throwable t) {
		super(t);
	}

	public String getErrorcode() {
		return errorCode;
	}
}
