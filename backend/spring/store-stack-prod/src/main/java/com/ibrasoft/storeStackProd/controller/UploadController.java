package com.ibrasoft.storeStackProd.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ibrasoft.storeStackProd.util.Utils;
import com.ibrasoft.storeStackProd.util.Constants;
import com.ibrasoft.storeStackProd.util.UploadResponse;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.service.UploadService;

@RestController
@RequestMapping("file")
@CrossOrigin("*")
public class UploadController {

	@Value("${application.upload-directory}")
	private String uploadDirectory;

	@Autowired
	UploadService uploadService;

	public UploadController() {

	}

	@PostMapping("uploadimage")
	public ResponseEntity<List<UploadResponse>> uploadImageFile(@RequestParam("files") MultipartFile[] multiFiles)
			throws Exception {
		ArrayList<UploadResponse> list = new ArrayList<UploadResponse>();
		for (int i = 0; i < multiFiles.length; i++) {
			if (!(multiFiles[i].isEmpty())) {
				try {
					UploadResponse upload = uploadService.processUpload(Utils.LOCAL_IMAGE_STORAGE, multiFiles[i]);
					if (upload != null)
						list.add(upload);
				} catch (Exception e) {
					throw new Exception();
				}
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping("deletefile")
	public ResponseEntity<?> deleteFile(String fileKey) throws ClinicException {
		String storagePath = fileKey;
		if (!uploadDirectory.endsWith("/"))
			uploadDirectory += "/";
		String path = uploadDirectory + storagePath;
		File file = new File(path);
		if (file.exists() && file.delete())
			return new ResponseEntity<>(HttpStatus.OK);
		else {
			throw new ClinicException(Constants.SERVER_ERROR, Constants.COULD_NOT_DELETE_FILE);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/download", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FileSystemResource> downloadFile(@RequestParam("fileKey") String fileKey) throws IOException {
		String storagePath = fileKey;
		if (!uploadDirectory.endsWith("/"))
			uploadDirectory += "/";
		String path = uploadDirectory + storagePath;
		File fileDownload = new File(path);
		String[] parts = fileKey.split("/");
		long fileLength = fileDownload.length();

		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentLength(fileLength);
		respHeaders.setContentDispositionFormData("attachment", parts[parts.length - 1]);

		return new ResponseEntity<FileSystemResource>(new FileSystemResource(fileDownload), respHeaders, HttpStatus.OK);
	}

	@GetMapping("/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) throws ClinicException {
		Resource file = uploadService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

}
