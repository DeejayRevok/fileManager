package com.filemanager.springfilemanagerapp.exceptions;

import com.filemanager.springfilemanagerapp.constants.ErrorConstants;

/**
 * Line exception class
 * @author Sergio Díez
 *
 */
public class LineException extends SpringFileManagerAppException {

	private static final long serialVersionUID = -3127949468772105975L;

	private final String errorCode = ErrorConstants.LINE_EXCEPTION_CODE;

	public LineException() {
		super();
	}

	public LineException(String message, Throwable t) {
		super(message, t);
	}

	public LineException(String message) {
		super(message);
	}

	public LineException(Throwable t) {
		super(t);
	}

	public String getErrorcode() {
		return errorCode;
	}
}
