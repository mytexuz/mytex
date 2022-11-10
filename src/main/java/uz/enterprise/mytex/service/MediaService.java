package uz.enterprise.mytex.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.enterprise.mytex.exception.CustomException;
import uz.enterprise.mytex.helper.ResponseHelper;

@Service
public class MediaService {
    private final ResponseHelper responseHelper;
    private final PropertyService propertyService;

    public MediaService(ResponseHelper responseHelper, PropertyService propertyService) {
        this.responseHelper = responseHelper;
        this.propertyService = propertyService;
    }

    @Transactional
    public ResponseEntity<?> upload(MultipartFile multipartFile) {
        try {
            Path rootPath = Path.of(propertyService.getValue("FILE_UPLOAD_PATH"));
            String contentType = multipartFile.getContentType();
            if (Objects.nonNull(contentType)) {
                String originalFilename = multipartFile.getOriginalFilename();
                String filenameExtension = StringUtils.getFilenameExtension(originalFilename);
                String generatedName = UUID.randomUUID() + "." + filenameExtension;
                Path uploadPath = rootPath.resolve(generatedName);
                Files.copy(multipartFile.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
                return responseHelper.successWithObject(Map.of("filePath", uploadPath.toString()));
            }
            throw new CustomException(responseHelper.invalidData());
        } catch (SizeLimitExceededException e) {
            throw new CustomException(responseHelper.invalidFileSize());
        } catch (IOException e) {
            throw new CustomException(responseHelper.invalidData());
        }
    }
}
