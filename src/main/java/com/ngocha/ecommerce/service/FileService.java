package com.ngocha.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    InputStream getResource

    String uploadImage(String path, MultipartFile file) throws IOException;
}
