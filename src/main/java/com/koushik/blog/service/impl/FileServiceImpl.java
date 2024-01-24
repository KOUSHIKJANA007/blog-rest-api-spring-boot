package com.koushik.blog.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.koushik.blog.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		String name=file.getOriginalFilename();
		String randomID=UUID.randomUUID().toString();
		String filemane1=randomID.concat(name.substring(name.lastIndexOf(".")));
		String filepath=path+File.separator+filemane1;
		File f=new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		Files.copy(file.getInputStream(), Paths.get(filepath));
		return filemane1;
	}

	@Override
	public InputStream getResources(String path, String filename) throws FileNotFoundException {
		String fullpath=path+File.separator+filename;
		InputStream is=new FileInputStream(fullpath);
		return is;
	}

}
