package com.filemanager.springfilemanagerapp.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.filemanager.springfilemanagerapp.model.FileData;
import com.filemanager.springfilemanagerapp.repository.FileDataRepository;

/**
 * Class that performs operations related with the file models
 * @author Usuario
 *
 */
@Service
public class FileDataService {

	private static final Logger log = LoggerFactory.getLogger(FileDataService.class);
	
	private FileDataRepository fileDataRepository;
	
	public FileDataService(FileDataRepository fileDataRepository) {
		this.fileDataRepository= fileDataRepository;
	}
	
	/**
	 * Get the list of file models
	 * @return List of file models
	 */
	public List<FileData> getAllFiles(){
		log.info("Request to retrieve all files");
		return fileDataRepository.findAll();
	}
	
	/**
	 * Save a new file in the database
	 * @param file File model to save
	 * @return File model saved
	 */
	public FileData saveFile(FileData file) {
		log.info("Request to create a new file");
		return fileDataRepository.save(file);
	}
	
	/**
	 * Search file model by file name
	 * @param fileName File name
	 * @return File model for the specified file name
	 */
	public Optional<FileData> getByName(String fileName) {
		log.info("Reques to get file {}",fileName);
		return fileDataRepository.findByFileName(fileName);
	}
	
}
