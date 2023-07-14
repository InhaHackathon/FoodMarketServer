package com.inhahackathon.foodmarket.service;

import com.inhahackathon.foodmarket.exception.FileUploadFailException;
import com.inhahackathon.foodmarket.handler.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadService {

    private final FileHandler fileHandler;

    @Value("${resource.file.url}")
    private String fileURL;

    public String saveFile(Long boardId, MultipartFile multipartFile) throws FileUploadFailException {
        String realFilename = fileHandler.saveFile(boardId, multipartFile);
        return fileURL + realFilename;
    }
}