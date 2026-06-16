package br.com.oboticariorevenda.oboticario_revenda.validator;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<FileIsImage, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file.isEmpty()) {
            return true;
        }

        String contentType = file.getContentType();

        if (!contentType.startsWith("image/")) {
            return false;
        }

        return true;
    }
    
}
