package com.re.it211project.validator;

import com.re.it211project.exception.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator {

    private FileValidator() {
    }

    public static void validateNotEmpty(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File không được để trống");
        }
    }

    public static void validateMaxSize(MultipartFile file, long maxSize) {
        if (file.getSize() > maxSize) {
            throw new BadRequestException("File vượt quá dung lượng cho phép");
        }
    }
}