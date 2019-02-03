package com.filemanager.springfilemanagerapp.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.filemanager.springfilemanagerapp.model.FileData;
import com.filemanager.springfilemanagerapp.model.LineData;
import com.filemanager.springfilemanagerapp.repository.LineDataRepository;

/**
 * Class that performs operations related to the line models
 * @author Sergio Díez
 *
 */
@Service
public class LineDataService {

	private static final Logger log = LoggerFactory.getLogger(LineDataService.class);
	
	private LineDataRepository lineDataRepository;
	
	public LineDataService(LineDataRepository lineDataRepository) {
		this.lineDataRepository = lineDataRepository;
	}
	
	/**
	 * Get all line models
	 * @return List of line models
	 */
	public List<LineData> getAllLines(){
		log.info("Request to retrieve all line");
		return lineDataRepository.findAll();
	}
	
	/**
	 * Save new line model in the database
	 * @param line
	 * @return
	 */
	public LineData saveLine(LineData line) {
		log.info("Request to create a new line");
		return lineDataRepository.save(line);
	}
	
	/**
	 * Get number of lines of the specfied file
	 * @param file File model
	 * @return Number of lines
	 */
	public Long linesOfFile(FileData file) {
		log.info("Request to get the number of linesfrom file");
		return lineDataRepository.countByFile(file);
	}
	
	/**
	 * Get line model identified by its line number inside the specified file
	 * @param file File model
	 * @param lineNumber Line number
	 * @return Line model
	 */
	public Optional<LineData> getLineFromFile(FileData file, Long lineNumber) {
		log.info("Request to get line from file");
		return lineDataRepository.findByFileAndLineNumber(file, lineNumber);
	}
	
	/**
	 * Get all line models from a specified file
	 * @param file File model
	 * @return List of lines of the specified file
	 */
	public Optional<List<LineData>> getLinesFromFile(FileData file){
		log.info("Request to get all lines from file");
		return lineDataRepository.findByFile(file);
	}
	
	/**
	 * Search line models which content contains the specified text inside a specified file
	 * @param content Content to search
	 * @param file File model
	 * @return List of line models which content matches the searched text
	 */
	public Optional<List<LineData>> getLinesFromFileContaining(String content, FileData file){
		log.info("Request to get lines containint text from file");
		return lineDataRepository.findByContentLikeAndFile(content, file);
	}
	
}
