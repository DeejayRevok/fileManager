package com.filemanager.springfilemanagerapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filemanager.springfilemanagerapp.model.FileData;

/**
 * File models repository class
 * @author Sergio Díez
 *
 */
@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {

	public Optional<FileData> findByFileName(String fileName);
}
