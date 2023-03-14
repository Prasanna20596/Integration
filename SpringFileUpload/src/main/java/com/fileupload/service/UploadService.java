package com.fileupload.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

	private static final String path = "/home/tts_466-prasanna/Files/";

	public void uploadFile(MultipartFile file) throws Exception {
		if (!file.getOriginalFilename().isEmpty()) {
			FileOutputStream outputStream = new FileOutputStream(new File(path, file.getOriginalFilename()));
			outputStream.write(file.getBytes());
			
		} else {
			System.out.println("File not found");
		}
		
	}

	public List<String> getUploadFilesList() {
		List<String> list = new ArrayList<>();
		File file = new File(path);
		String[] fileLiSt = file.list();
		for (String name : fileLiSt) {
			list.add(name);
		}
		return list;
	}
	
	public ResponseEntity<Resource> downloadfile(String name) throws IOException {
		File file=new File(path+name);
		Path path=Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		
		return ResponseEntity.ok()
				  .contentType(MediaType.parseMediaType("application/octet-stream"))
				  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name)
				  .body(resource);
	} 	  
	
	public void uploadMultifiles(MultipartFile[] file) throws IOException {
		
		for(int files=0;files<file.length;files++) {
			FileOutputStream outputStream = new FileOutputStream(new File(path, file[files].getOriginalFilename()));
			outputStream.write(file[files].getBytes());
		}
		
	}
		
}
