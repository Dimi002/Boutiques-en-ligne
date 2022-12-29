package com.ibrasoft.storeStackProd.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.util.UploadResponse;

/**
 * @author Maestros
 * @email temateuroslynf32@gmail.com
 */
@Service
public interface UploadService {

    public void initUploadDirectory(String storagePath);

    public Resource load(String filename) throws ClinicException;

    public UploadResponse processUpload(String storagePath, MultipartFile fileData) throws IOException;

}
