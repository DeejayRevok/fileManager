package com.filemanager.springfilemanagerapp.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import com.filemanager.springfilemanagerapp.exceptions.FileNotFoundException;
import com.filemanager.springfilemanagerapp.exceptions.FileProcessingException;
import com.filemanager.springfilemanagerapp.exceptions.LineException;
import com.filemanager.springfilemanagerapp.exceptions.SpringFileManagerAppException;
import com.filemanager.springfilemanagerapp.model.FileData;
import com.filemanager.springfilemanagerapp.model.LineData;
import com.filemanager.springfilemanagerapp.utils.GenericBuilder;

/**
 * Class that performs operations with files, both models and filesystem
 * @author Sergio Díez
 *
 */
@ConditionalOnBean(value = { FilesystemService.class, FileDataService.class, LineDataService.class })
@Service
public class FileService {

	private static final Logger log = LoggerFactory.getLogger(FileService.class);

	private FilesystemService filesystemService;

	private FileDataService fileDataService;

	private LineDataService lineDataService;
	
	public FileService(FilesystemService filesystemService, FileDataService fileDataService, LineDataService lineDataService) {
		this.filesystemService = filesystemService;
		this.fileDataService = fileDataService;
		this.lineDataService = lineDataService;
	}

	/**
	 * Create new file
	 * @param name File name
	 * @return File model for the created file
	 * @throws SpringFileManagerAppException
	 */
	public FileData createFile(String name) throws SpringFileManagerAppException {
		log.info("Reques to create new file {}", name);
		File file = filesystemService.createFile(name);
		FileData fileData = new GenericBuilder<>(FileData::new).with(FileData::setFileName, name)
				.with(FileData::setFilePath, file.getAbsolutePath()).build();
		fileDataService.saveFile(fileData);
		return fileData;
	}

	/**
	 * Append line to file
	 * @param fileName File name
	 * @param content Content of the line to append
	 * @return Line model of the appended line
	 * @throws SpringFileManagerAppException
	 */
	@Transactional
	public LineData addLine(String fileName, String content) throws SpringFileManagerAppException {
		log.info("Request to add {} to {}", content, fileName);
		FileData file = fileDataService.getByName(fileName)
				.orElseThrow(() -> new FileNotFoundException("File not found in database"));
		if (filesystemService.fileExists(file.getFilePath())) {
			LineData line = new GenericBuilder<>(LineData::new).with(LineData::setContent, content)
					.with(LineData::setLineNumber, lineDataService.linesOfFile(file) + 1).with(LineData::setFile, file)
					.build();
			lineDataService.saveLine(line);
			try {
				Files.write(Paths.get(file.getFilePath()),
						(content + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
				return line;
			} catch (IOException e) {
				log.error("Error trying to append line to file");
				throw new FileProcessingException("Error trying to append line to file", e);
			}
		} else {
			log.error("File {} not found in the fileSystem", fileName);
			throw new FileNotFoundException("File not found in the filesystem");
		}
	}

	/**
	 * Number of lines of file
	 * @param fileName File name
	 * @return Number of lines of the specified file
	 * @throws FileNotFoundException
	 */
	public Long getNumberLines(String fileName) throws FileNotFoundException {
		log.info("Request to get number of lines from {}", fileName);
		FileData file = fileDataService.getByName(fileName)
				.orElseThrow(() -> new FileNotFoundException("File not found in database"));
		return lineDataService.linesOfFile(file);
	}

	/**
	 * Get the content of the line identified by its number inside its file
	 * @param fileName File name
	 * @param lineNumber Line number inside the file
	 * @return Content of the line in the specified number
	 * @throws SpringFileManagerAppException
	 */
	public String getLineByNumber(String fileName, Long lineNumber) throws SpringFileManagerAppException {
		log.info("Request to get line {} from {}", lineNumber, fileName);
		FileData file = fileDataService.getByName(fileName)
				.orElseThrow(() -> new FileNotFoundException("File not found in database"));
		LineData line = lineDataService.getLineFromFile(file, lineNumber)
				.orElseThrow(() -> new LineException("Line not found for the specified file"));
		return line.getContent();
	}

	/**
	 * Edit content of one line identified by its number inside the specified file
	 * @param fileName File name
	 * @param lineNumber Number of the line inside its file
	 * @param content Content to set in the line
	 * @return Line model of the edited line
	 * @throws SpringFileManagerAppException
	 */
	@Transactional
	public LineData editLine(String fileName, Long lineNumber, String content) throws SpringFileManagerAppException {
		log.info("Request to edit line {} from {} with {}", lineNumber, fileName, content);
		FileData file = fileDataService.getByName(fileName)
				.orElseThrow(() -> new FileNotFoundException("File not found in database"));
		if (filesystemService.fileExists(file.getFilePath())) {
			List<LineData> lines = lineDataService.getLinesFromFile(file)
					.orElseThrow(() -> new FileProcessingException("File is empty"));
			if (lines.size() >= lineNumber) {
				lines.sort(Comparator.comparing(LineData::getLineNumber));
				lines.get(lineNumber.intValue() - 1).setContent(content);
				try {
					Files.write(Paths.get(file.getFilePath()),
							lines.stream().map(tmpLine -> tmpLine.getContent()).collect(Collectors.toList()),
							StandardCharsets.UTF_8);
				} catch (IOException e) {
					log.error("Error writing to file");
					throw new FileProcessingException("Error writing to file", e);
				}
				return lineDataService.saveLine(lines.get(lineNumber.intValue() - 1));
			} else {
				log.error("Line {} not found in file", lineNumber);
				throw new LineException("Line not found for the specified file");
			}
		} else {
			log.error("File {} not found in the fileSystem", fileName);
			throw new FileNotFoundException("File not found in the filesystem");
		}
	}

	/**
	 * Get the text of one file
	 * @param fileName File name
	 * @return File text content
	 * @throws SpringFileManagerAppException
	 */
	public String getText(String fileName) throws SpringFileManagerAppException {
		log.info("Request to get all text from {}", fileName);
		FileData file = fileDataService.getByName(fileName)
				.orElseThrow(() -> new FileNotFoundException("File not found in database"));
		if (filesystemService.fileExists(file.getFilePath())) {
			try {
				return new String(Files.readAllBytes(Paths.get(file.getFilePath())));
			} catch (IOException e) {
				log.error("Error reading file {} from system", fileName);
				throw new FileProcessingException("Error reading file from system", e);
			}
		} else {
			log.error("File {} not found in the fileSystem", fileName);
			throw new FileNotFoundException("File not found in the filesystem");
		}
	}
	
	/**
	 * Get all lines containing a specified text inside the specified file
	 * @param fileName File name
	 * @param text Text to search
	 * @return Line models for the lines that matches the searched text
	 * @throws FileNotFoundException
	 */
	public List<LineData> getLinesContaining(String fileName, String text) throws FileNotFoundException {
		log.info("Request to get all lines from {} containing {}", fileName, text);
		FileData file = fileDataService.getByName(fileName)
				.orElseThrow(() -> new FileNotFoundException("File not found in database"));
		return lineDataService.getLinesFromFileContaining("%"+text+"%", file).orElse(null);
	}
	
	/**
	 * Edit one line from multiple files or append it to all of them
	 * @param fileNames List of file names
	 * @param content Content of the line to edit or append
	 * @param lineNumber Number of the line in case of edition, if it's null, then the appended of new line in all files is performed
	 * @return Line models of the appended or edited lines in all files
	 */
	public List<LineData> editMultipleLineFiles(List<String> fileNames, String content, Long lineNumber){
		log.info("Reques to edit files {} with content {}",fileNames, content);
		List<LineData> editedLines = new ArrayList<>();
		fileNames.parallelStream().forEach(name -> {
			LineData editedLine = null;
			try {
				editedLine = (lineNumber!=null && lineNumber>0) ? editLine(name, lineNumber, content) : addLine(name, content);
			} catch (SpringFileManagerAppException e) {
				log.error("Error editing file {}", name);
			}
			if(editedLine!=null) {
				editedLines.add(editedLine);
			}
		});
		return editedLines;
	}

}
