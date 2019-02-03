package com.filemanager.springfilemanagerapp.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.filemanager.springfilemanagerapp.exceptions.SpringFileManagerAppException;
import com.filemanager.springfilemanagerapp.service.FilesystemService;

/**
 * File system storage configuration class
 * @author Sergio Díez
 *
 */
@Configuration
public class FilesystemStorageConfiguration {
	
	private static final Logger log =  LoggerFactory.getLogger(FilesystemStorageConfiguration.class);
	
	/**
	 * Creates a temporal directory to store all files
	 * @return Filesystem service configured with the temporal directory path
	 * @throws SpringFileManagerAppException
	 */
	@Bean
	public FilesystemService setFileSystemStorage() throws SpringFileManagerAppException{
		Path tempStorage = null;
		try {
			tempStorage = Files.createTempDirectory("qindelstorage");
		} catch (IOException e) {
			log.error("Error initializing file system storage");
			throw new SpringFileManagerAppException("Error initializing file system storage", e);
		}
		return new FilesystemService(tempStorage.toAbsolutePath().toString());
	}
}
