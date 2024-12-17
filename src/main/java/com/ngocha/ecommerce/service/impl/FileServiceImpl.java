package com.ngocha.ecommerce.service.impl;

import com.ngocha.ecommerce.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;

        return new FileInputStream(filePath);
    }

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String originFileName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();

        assert originFileName != null;
        String fileName = randomId.concat(originFileName.substring(originFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;

        File folder = new File(path);

        if(!folder.exists()) {
            folder.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }
}
