package com.fisi.sgapcbackend.services.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fisi.sgapcbackend.services.IUploadPhotoBrandService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UploadPhotoBrandServiceImpl implements IUploadPhotoBrandService {

	private final Logger log = LoggerFactory.getLogger(IUploadPhotoBrandService.class);

	private final static String DIRECTORY_UPLOAD = "brandsfiles";

	@Override
	public Resource upload(String namePhoto) throws MalformedURLException {
		Path pathFile = getPath(namePhoto);
		log.info(pathFile.toString());
		Resource resource = new UrlResource(pathFile.toUri());

		if (!resource.exists() && !resource.isReadable()) {
			pathFile = Paths.get("src/main/resources/static/images").resolve("no-image.png").toAbsolutePath();
			resource = new UrlResource(pathFile.toUri());
			log.error("Error no se puede cargar la imagen: " + namePhoto);
		}
		return resource;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");
		Path pathFile = getPath(fileName);
		log.info(pathFile.toString());
		Files.copy(file.getInputStream(), pathFile);

		return fileName;
	}

	@Override
	public boolean delete(String namePhoto) {
		if (namePhoto !=null && namePhoto.length() > 0){
            Path lastPathPhoto = Paths.get("brandsfiles").resolve(namePhoto).toAbsolutePath();
            File lastFilePhoto = lastPathPhoto.toFile();
            if (lastFilePhoto.exists() && lastFilePhoto.canRead()){
                lastFilePhoto.delete();
                return true;
            }
        }
        return false;
	}

	@Override
	public Path getPath(String namePhoto) {
        return Paths.get(DIRECTORY_UPLOAD).resolve(namePhoto).toAbsolutePath();

	}

}
