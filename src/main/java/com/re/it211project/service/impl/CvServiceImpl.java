package com.re.it211project.service.impl;

import com.re.it211project.dto.response.CvResponse;
import com.re.it211project.entity.User;
import com.re.it211project.exception.BadRequestException;
import com.re.it211project.repository.UserRepository;
import com.re.it211project.service.CvService;
import com.re.it211project.util.SecurityUtils;
import com.re.it211project.validator.PdfFileValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CvServiceImpl implements CvService {

    private final UserRepository userRepository;

    @Override
    public CvResponse uploadCv(MultipartFile file) {
        User user = SecurityUtils.getCurrentUser();

        PdfFileValidator.validate(file);

        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/cv/";

            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = System.currentTimeMillis()
                    + "_"
                    + file.getOriginalFilename();

            File destination = new File(uploadDir + fileName);

            file.transferTo(destination);

            String cvUrl = "/uploads/cv/" + fileName;

            user.setCvUrl(cvUrl);
            userRepository.save(user);

            return CvResponse.builder()
                    .cvUrl(cvUrl)
                    .build();

        } catch (IOException e) {
            throw new BadRequestException("Upload CV thất bại");
        }
    }

    @Override
    public CvResponse getMyCv() {
        User user = SecurityUtils.getCurrentUser();

        return CvResponse.builder()
                .cvUrl(user.getCvUrl())
                .build();
    }

    @Override
    public void deleteMyCv() {
        User user = SecurityUtils.getCurrentUser();

        user.setCvUrl(null);
        userRepository.save(user);
    }
}