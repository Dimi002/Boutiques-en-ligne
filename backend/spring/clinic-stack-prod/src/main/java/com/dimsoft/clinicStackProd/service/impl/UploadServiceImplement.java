package com.dimsoft.clinicStackProd.service.impl;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.dimsoft.clinicStackProd.controller.UploadController;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.repository.UserRepository;
import com.dimsoft.clinicStackProd.service.UploadService;
import com.dimsoft.clinicStackProd.util.Constants;
import com.dimsoft.clinicStackProd.util.UploadResponse;
import com.dimsoft.clinicStackProd.util.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Maestros
 * @email temateuroslynf32@gmail.com
 */
@Service
public class UploadServiceImplement implements UploadService {
	@Value("${application.upload-directory}")
	private String uploadDirectory;

	@Autowired
	UserRepository usersRepository;

	private Path root = null;

	@Override
	public void initUploadDirectory(String storagePath) {
		Utils.createFolderIfNotExists(storagePath);
		root = Paths.get(storagePath);
	}

	@Override
	public Resource load(String filename) throws ClinicException {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new ClinicException(Constants.FILE_NOT_READ, Constants.COULD_NOT_READ_FILE);
			}
		} catch (MalformedURLException e) {
			throw new ClinicException(Constants.RUN_TIME, Constants.RUN_TIME_EXCEPTION);
		}
	}

	@Override
	public UploadResponse processUpload(String storagePath, MultipartFile fileData) throws IOException {
		if (!uploadDirectory.endsWith("/"))
			uploadDirectory += "/";
		String path = uploadDirectory + storagePath;
		initUploadDirectory(path);
		path = path.replace("\\", "/");
		if (!path.endsWith("/"))
			path += "/";
		String uploadedFileLocation = (new Date()).getTime() + "-" + fileData.getOriginalFilename();
		String fileKey = storagePath + "/" + uploadedFileLocation;
		uploadedFileLocation = path + uploadedFileLocation;
		File f = new File(uploadedFileLocation);
		fileData.transferTo(f);
		Path pathFinal = Paths.get(uploadedFileLocation);
		String url = MvcUriComponentsBuilder
				.fromMethodName(UploadController.class, "getFile", pathFinal.getFileName().toString()).build()
				.toString();
		UploadResponse upload;
		String relativePath = url.substring(url.replaceAll("//", "|").replaceAll("/", "#").indexOf("#") + 1);
		upload = new UploadResponse(fileKey, relativePath);
		return upload;
	}

}
