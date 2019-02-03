package com.filemanager.springfilemanagerapp.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.PreDestroy;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.filemanager.springfilemanagerapp.exceptions.FileExistsException;
import com.filemanager.springfilemanagerapp.exceptions.FileProcessingException;
import com.filemanager.springfilemanagerapp.exceptions.SpringFileManagerAppException;

/**
 * Class that performs operations related with the filesystem storage
 * @author Sergio Díez
 *
 */
public class FilesystemService {
	
	private static final Logger log = LoggerFactory.getLogger(FilesystemService.class);
	
	private final String storagePath;
	
	public FilesystemService(String storagePath) {
		this.storagePath=storagePath;
	}
	
	/**
	 * Create a file in the filesystem
	 * @param name Name of the file to create
	 * @return Created file
	 * @throws SpringFileManagerAppException
	 */
	public File createFile(String name) throws SpringFileManagerAppException {
		log.info("Request to create a new file {}",name);
		try {
			return Files.createFile(Paths.get(storagePath).resolve(name)).toFile();
		} catch (FileAlreadyExistsException e) {
			log.error("File already exists");
			throw new FileExistsException("File already exists", e);
		} catch (IOException e) {
			log.error("Error creating file");
			throw new FileProcessingException("Error creating file", e);
		}
	}
	
	/**
	 * Check if file exists in the filesystem
	 * @param filePath Path of the file to check existence
	 * @return File exists or not
	 */
	public boolean fileExists(String filePath) {
		log.info("Checking filessystem file {}",filePath);
		return Files.exists(Paths.get(filePath));
	}
	
	/**
	 * Clear the filesystem storage when the application is closed
	 */
	@PreDestroy
	public void clearStorage() {
		log.info("Clearing storage");
		try {
			FileUtils.deleteDirectory(Paths.get(storagePath).toFile());
		} catch (IOException e) {
			log.error("Error clearing storage ",e);
		}
	}
}
