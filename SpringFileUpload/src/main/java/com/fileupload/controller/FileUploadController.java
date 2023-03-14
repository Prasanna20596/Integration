package com.fileupload.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fileupload.service.UploadService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class FileUploadController {

	@Autowired
	private UploadService service;

	@PostMapping(value = "/upload")
	public void fileUpload(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		service.uploadFile(file);
	}

	@GetMapping("/files")
	public List<String> getUploadFiles() {
		return service.getUploadFilesList();
	}

	@GetMapping("/download/{name}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("name") String name) throws IOException {
		 return service.downloadfile(name);
	}

	@PostMapping("/multifileupload")
	public void uploadMultipleFiles(@RequestParam("file") MultipartFile[] file) throws IOException {
		 service.uploadMultifiles(file);
	}

}
