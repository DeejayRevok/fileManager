package com.filemanager.springfilemanagerapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Line model class
 * @author Sergio Díez
 *
 */
@ApiModel(value="LineData", description="Line model containing data about one line of one file")
@Entity
@Table(name="line")
public class LineData {

	@ApiModelProperty(value="Identifier of the model in the database")
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@ApiModelProperty(value="Line content")
	@Lob
	private String content;
	
	@ApiModelProperty(value="Number of line inside its file")
	private Long lineNumber;
	
	@ApiModelProperty(value="File model identifying the file that contains this line")
	@ManyToOne(optional=false)
	private FileData file;
	
	public FileData getFile() {
		return file;
	}

	public void setFile(FileData file) {
		this.file = file;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Long lineNumber) {
		this.lineNumber = lineNumber;
	}
}
