package com.filemanager.springfilemanagerapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.filemanager.springfilemanagerapp.model.FileData;
import com.filemanager.springfilemanagerapp.model.LineData;
import com.filemanager.springfilemanagerapp.service.LineDataService;
import com.filemanager.springfilemanagerapp.utils.GenericBuilder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller class containing the line models management endpoints
 * @author Sergio Díez
 *
 */
@RestController
@RequestMapping("/api/lineData")
public class LineDataController {

	private static final Logger log = LoggerFactory.getLogger(LineDataController.class);

	private LineDataService lineDataService;

	public LineDataController(LineDataService lineDataService) {
		this.lineDataService = lineDataService;
	}

	@ApiOperation(value = "Get the list of lines from file")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "List of line models", response= LineData.class, responseContainer="List"),
		@ApiResponse(code = 404, message = "Any lines found for file")})
	@RequestMapping(value = "/{fileDataId}", method = RequestMethod.GET)
	public ResponseEntity<List<LineData>> getLinesFromFile(@ApiParam(value = "Identifier of the file model to get lines from") @PathVariable Long fileDataId) {
		log.info("REST request to get all lines from file");
		Assert.isTrue(fileDataId > 0, "File identifier is not valid");
		return lineDataService
				.getLinesFromFile(new GenericBuilder<>(FileData::new).with(FileData::setId, fileDataId).build())
				.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

}
