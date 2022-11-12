package uz.enterprise.mytex.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.enterprise.mytex.common.Generated;
import uz.enterprise.mytex.exception.CustomException;
import uz.enterprise.mytex.helper.ResponseHelper;

@Service
public class MediaService {
    private final ResponseHelper responseHelper;
    private final PropertyService propertyService;

    @Generated
    public MediaService(ResponseHelper responseHelper, PropertyService propertyService) {
        this.responseHelper = responseHelper;
        this.propertyService = propertyService;
    }

    @Transactional
    public ResponseEntity<?> upload(MultipartFile multipartFile) {
        try {
            Path rootPath = Path.of(propertyService.getValue("FILE_UPLOAD_PATH"));
            String originalFilename = multipartFile.getOriginalFilename();
            String filenameExtension = StringUtils.getFilenameExtension(originalFilename);
            String generatedName = UUID.randomUUID() + "." + filenameExtension;
            Path uploadPath = rootPath.resolve(generatedName);
            Files.copy(multipartFile.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
            return responseHelper.success(Map.of("filePath", uploadPath.toString()));
        } catch (Exception e) {
            throw new CustomException(responseHelper.invalidData());
        }
    }
}
