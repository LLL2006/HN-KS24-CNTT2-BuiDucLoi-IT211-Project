package com.re.it211project.validator;

import com.re.it211project.exception.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

public class PdfFileValidator {

    private static final long MAX_SIZE = 15 * 1024 * 1024;

    private PdfFileValidator() {
    }

    public static void validate(MultipartFile file) {
        FileValidator.validateNotEmpty(file);
        FileValidator.validateMaxSize(file, MAX_SIZE);

        if (!"application/pdf".equals(file.getContentType())) {
            throw new BadRequestException("Chỉ được upload file PDF");
        }

        String filename = file.getOriginalFilename();

        if (filename == null || !filename.toLowerCase().endsWith(".pdf")) {
            throw new BadRequestException("File phải có định dạng .pdf");
        }
    }
}