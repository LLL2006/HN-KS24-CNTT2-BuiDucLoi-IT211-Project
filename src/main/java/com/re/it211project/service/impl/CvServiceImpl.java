package com.re.it211project.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CvServiceImpl implements CvService {

    private final UserRepository userRepository;
    private final Cloudinary cloudinary;

    @Override
    public CvResponse uploadCv(MultipartFile file) {
        User user = SecurityUtils.getCurrentUser();

        PdfFileValidator.validate(file);

        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "auto",
                            "folder", "job-portal/cv",
                            "public_id", System.currentTimeMillis()
                                    + "_"
                                    + file.getOriginalFilename()
                    )
            );

            String cvUrl = uploadResult.get("secure_url").toString();

            user.setCvUrl(cvUrl);
            userRepository.save(user);

            return CvResponse.builder()
                    .cvUrl(cvUrl)
                    .build();

        } catch (IOException e) {
            throw new BadRequestException("Upload CV lên Cloudinary thất bại");
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