package com.re.it211project.service;

import com.re.it211project.dto.response.CvResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CvService {

    CvResponse uploadCv(MultipartFile file);

    CvResponse getMyCv();

    void deleteMyCv();
}