package com.example.Blogging.Application.Services.implementation;

import com.example.Blogging.Application.Services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

//        File name
        String name = file.getOriginalFilename();     // file name for ex. abc.png

//        Random name generate file
        String randomId = UUID.randomUUID().toString();
        String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));

//        Full Path of file
        String filePath = path + File.separator + fileName1;

//        create folder if not created
        File f = new File(path);    // path contains path till folder which contains images
        if (f.exists()){
            f.mkdir();              // create folder if file is not exist
        }

//        File copy
        Files.copy(file.getInputStream() , Paths.get(filePath));

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);

//        Db logic to return inputStream
        return is;

    }
}
