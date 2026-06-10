package com.re.it211project.controller;

import com.re.it211project.dto.response.ApiResponse;
import com.re.it211project.dto.response.CvResponse;
import com.re.it211project.service.CvService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@PreAuthorize("hasRole('CANDIDATE')")
@RestController
@RequestMapping("/api/v1/cv")
@RequiredArgsConstructor
public class CvController {

    private final CvService cvService;

    @PostMapping("/upload")
    public ApiResponse<CvResponse> uploadCv(
            @RequestParam("file") MultipartFile file
    ) {
        return ApiResponse.<CvResponse>builder()
                .success(true)
                .message("Upload CV thành công")
                .data(cvService.uploadCv(file))
                .build();
    }

    @GetMapping("/my-cv")
    public ApiResponse<CvResponse> getMyCv() {
        return ApiResponse.<CvResponse>builder()
                .success(true)
                .message("Lấy CV của tôi thành công")
                .data(cvService.getMyCv())
                .build();
    }

    @DeleteMapping("/my-cv")
    public ApiResponse<Void> deleteMyCv() {
        cvService.deleteMyCv();

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Xóa CV thành công")
                .data(null)
                .build();
    }
}