package com.filemanager.springfilemanagerapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * File model class
 * @author Sergio Díez
 *
 */
@ApiModel(value="FileData", description="File model containing details about one file")
@Entity
@Table(name = "file")
public class FileData {
	
	@ApiModelProperty(value="Identifier of the model in the database")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ApiModelProperty(value="Name of the file")
	@Column(name="file_name")
	private String fileName;
	
	@ApiModelProperty(value="Path where the file is stored")
	@Column(name="file_path")
	private String filePath;
	
	public FileData() {
		
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
