package com.filemanager.springfilemanagerapp.domain;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Request body for the multiple files edition
 * @author Sergio Díez
 *
 */
@ApiModel(value="MultipleEditRequest", description="Multiple files edition request")
public class MultipleEditRequest {
	
	@NotNull
	@ApiModelProperty(value="List of file names to edit")
	private List<String> fileNames;
	
	@ApiModelProperty(value="Line number to edit in all the files specified")
	private Long lineNumber;
	
	@ApiModelProperty(value="Content to append or to modify in the specified line")
	private String content;
	
	public MultipleEditRequest() {
		
	}

	public List<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public Long getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
