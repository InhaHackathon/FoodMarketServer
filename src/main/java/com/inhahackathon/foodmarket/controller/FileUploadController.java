package com.inhahackathon.foodmarket.controller;

import com.inhahackathon.foodmarket.exception.FileUploadFailException;
import com.inhahackathon.foodmarket.service.FileUploadService;
import com.inhahackathon.foodmarket.type.dto.ResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "File Upload")
@Slf4j
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @Operation(summary = "파일 업로드", description = "Long boardId(게시글 업로드일 때만 입력)" +
            "MultipartFile multipartFile(필수)")
//    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseModel upload(
            @RequestParam(value = "id", required = false) Long boardId,
            @RequestParam(value = "file", required = true) MultipartFile multipartFile
    ) throws FileUploadFailException {
        String url = fileUploadService.saveFile(boardId, multipartFile);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("url", url);
        return responseModel;
    }

}