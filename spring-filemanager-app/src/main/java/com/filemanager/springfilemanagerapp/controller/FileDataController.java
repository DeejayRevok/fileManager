package com.filemanager.springfilemanagerapp.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.filemanager.springfilemanagerapp.model.FileData;
import com.filemanager.springfilemanagerapp.service.FileDataService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller class containing the file models management endpoints
 * @author Sergio Díez
 *
 */
@RestController
@RequestMapping("/api/fileData")
public class FileDataController {

	private static final Logger log = LoggerFactory.getLogger(FileDataController.class);

	private FileDataService fileDataService;
	
	public FileDataController(FileDataService fileDataService) {
		this.fileDataService = fileDataService;
	}

	@ApiOperation(value = "Get the list of files")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "List of file models", response= FileData.class, responseContainer="List"),
		@ApiResponse(code = 404, message = "Any files found")})
	@GetMapping
	public ResponseEntity<List<FileData>> getAllFiles() {
		log.info("REST request to get all files");
		return Optional.ofNullable(fileDataService.getAllFiles())
				.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

}
