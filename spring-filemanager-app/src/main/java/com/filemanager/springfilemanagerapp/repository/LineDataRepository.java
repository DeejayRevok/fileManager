package com.filemanager.springfilemanagerapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filemanager.springfilemanagerapp.model.FileData;
import com.filemanager.springfilemanagerapp.model.LineData;

/**
 * Line models repository class
 * @author Sergio Díez
 *
 */
@Repository
public interface LineDataRepository extends JpaRepository<LineData, Long> {
	
	public Long countByFile(FileData file);
	
	public Optional<LineData> findByFileAndLineNumber(FileData file, Long lineNumber);
	
	public Optional<List<LineData>> findByFile(FileData file);
	
	public Optional<List<LineData>> findByContentLikeAndFile(String content, FileData file);

}
