package com.fisi.sgapcbackend.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadPhotoBrandService {
    public Resource upload(String namePhoto) throws MalformedURLException;
    public String copy(MultipartFile file) throws IOException;
    public boolean delete(String namePhoto);
    public Path getPath(String namePhoto);
}
