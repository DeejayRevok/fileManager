package com.filemanager.springfilemanagerapp.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.filemanager.springfilemanagerapp.domain.ErrorBody;
import com.filemanager.springfilemanagerapp.domain.MultipleEditRequest;
import com.filemanager.springfilemanagerapp.exceptions.FileNotFoundException;
import com.filemanager.springfilemanagerapp.exceptions.SpringFileManagerAppException;
import com.filemanager.springfilemanagerapp.model.FileData;
import com.filemanager.springfilemanagerapp.model.LineData;
import com.filemanager.springfilemanagerapp.service.FileService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller class containing the file management endpoints
 * @author Sergio Díez
 *
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

	private static final Logger log = LoggerFactory.getLogger(FileController.class);

	private FileService fileService;
	
	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@ApiOperation(value = "Create a new file")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "File model of the new file", response= FileData.class),
		@ApiResponse(code = 500, message = "Error ocurred in the execution", response= ErrorBody.class)})
	@RequestMapping(value = "/create/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<FileData> createNewFile(
			@ApiParam(value = "Name of the file to create") @PathVariable String fileName) throws SpringFileManagerAppException {
		log.info("REST request to create a new file");
		Assert.isTrue(!fileName.isEmpty(), "File name is missing");
		return Optional.ofNullable(fileService.createFile(fileName))
				.map(result -> new ResponseEntity<FileData>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@ApiOperation(value = "Append a new line to the specified file")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Line model of the appended line", response= LineData.class),
		@ApiResponse(code = 500, message = "Error ocurred in the execution", response= ErrorBody.class)})
	@RequestMapping(value = "/append/{fileName}", method = RequestMethod.PUT, consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<LineData> appendLineToFile(
			@ApiParam(value = "Name of the file to append the line") @PathVariable String fileName,
			@ApiParam(value = "Content of the line") @RequestBody String lineContent) throws SpringFileManagerAppException {
		log.info("REST request to append a new line to file");
		Assert.isTrue(!fileName.isEmpty(), "File name is missing");
		return Optional.ofNullable(fileService.addLine(fileName, lineContent))
				.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@ApiOperation(value = "Get the number of lines of the specified file")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Number of lines", response= Long.class),
		@ApiResponse(code = 500, message = "Error ocurred in the execution", response= ErrorBody.class)})
	@RequestMapping(value = "/lines/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<Long> getNumberOfLines(
			@ApiParam(value = "Name of the file to get number of lines") @PathVariable String fileName)
			throws FileNotFoundException {
		log.info("REST request to get the number of lines from file");
		Assert.isTrue(!fileName.isEmpty(), "File name is missing");
		return Optional.ofNullable(fileService.getNumberLines(fileName))
				.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@ApiOperation(value = "Get the content of one line identified by its number inside the specified file")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Content of the line", response= String.class),
		@ApiResponse(code = 500, message = "Error ocurred in the execution", response= ErrorBody.class)})
	@RequestMapping(value = "/line/{fileName}/{lineNumber}", method = RequestMethod.GET)
	public ResponseEntity<String> getLineContent(
			@ApiParam(value = "Name of the file to get line content") @PathVariable String fileName,
			@ApiParam(value = "Number of the line to get content") @PathVariable Long lineNumber)
			throws SpringFileManagerAppException {
		log.info("REST request to get one line from file");
		Assert.isTrue(!fileName.isEmpty(), "File name is missing");
		Assert.isTrue(lineNumber > 0, "Line number must be higher than 0");
		return Optional.ofNullable(fileService.getLineByNumber(fileName, lineNumber))
				.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@ApiOperation(value = "Edit the content of one line identified by its number inside the specified file")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Line model of the edited line", response= LineData.class),
		@ApiResponse(code = 500, message = "Error ocurred in the execution", response= ErrorBody.class)})
	@RequestMapping(value = "/line/{fileName}/{lineNumber}", method = RequestMethod.PUT, consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<LineData> editLine(
			@ApiParam(value = "Name of the file to edit") @PathVariable String fileName,
			@ApiParam(value = "Number of the line to edit") @PathVariable Long lineNumber,
			@ApiParam(value = "Content of the line") @RequestBody String content) throws SpringFileManagerAppException {
		log.info("REST request to edit line from file");
		Assert.isTrue(!fileName.isEmpty(), "File name is missing");
		Assert.isTrue(lineNumber > 0, "Line number must be higher than 0");
		return Optional.ofNullable(fileService.editLine(fileName, lineNumber, content))
				.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@ApiOperation(value = "Get the text of the specified file")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Text content of the file", response= String.class),
		@ApiResponse(code = 500, message = "Error ocurred in the execution", response= ErrorBody.class)})
	@RequestMapping(value = "/text/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<String> getTextFromFile(
			@ApiParam(value = "Name of the file to get text") @PathVariable String fileName) throws SpringFileManagerAppException {
		log.info("REST request to get text from file");
		Assert.isTrue(!fileName.isEmpty(), "File name is missing");
		return Optional.ofNullable(fileService.getText(fileName))
				.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@ApiOperation(value = "Search the lines of the specified file that contains some text")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Line models of the lines that matches the search", response= LineData.class, responseContainer="List"),
		@ApiResponse(code = 500, message = "Error ocurred in the execution", response= ErrorBody.class)})
	@RequestMapping(value = "/lines/contain/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<List<LineData>> getLinesContaining(
			@ApiParam(value = "Name of the file to search in") @PathVariable String fileName,
			@ApiParam(value = "Text to search inside the lines") @RequestParam(name = "text", required = true) String text)
			throws FileNotFoundException {
		log.info("REST request to get all lines containing text from file");
		Assert.isTrue(!fileName.isEmpty(), "File name is missing");
		return Optional.ofNullable(fileService.getLinesContaining(fileName, text))
				.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@ApiOperation(value = "Edit multiple files appending a new line or editing the content of one line identified by its number")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Line models of the edited or appended lines", response= LineData.class, responseContainer= "List"),
		@ApiResponse(code = 500, message = "Error ocurred in the execution", response= ErrorBody.class)})
	@RequestMapping(value = "/multi/line", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LineData>> editLinesMultipleFiles(
			@ApiParam(value = "Model containing the multiple files edition parameters") @RequestBody MultipleEditRequest editRequest) {
		log.info("REST request to edit lines from multiple files");
		Assert.notNull(editRequest, "Request data is missing");
		Assert.notNull(editRequest.getFileNames(), "File names is missing");
		Assert.isTrue(!editRequest.getFileNames().isEmpty(), "File names content is missing");
		return Optional
				.ofNullable(fileService.editMultipleLineFiles(editRequest.getFileNames(), editRequest.getContent(),
						editRequest.getLineNumber()))
				.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

}
