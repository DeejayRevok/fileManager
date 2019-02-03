package com.filemanager.springfilemanagerapp.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Generic error response body
 * @author Sergio Díez
 *
 */
@ApiModel(value="ErrorBody", description="Error model containing details about the error")
public class ErrorBody {

	@ApiModelProperty(value="Identifier od the error")
	private String errorCode;
	@ApiModelProperty(value="Message with a brief description of the error")
	private String errorMessage;
	@ApiModelProperty(value="Exception that causes the error")
	private Exception ex;
	
	public ErrorBody() {
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Exception getEx() {
		return ex;
	}
	public void setEx(Exception ex) {
		this.ex = ex;
	}
	
	
}
