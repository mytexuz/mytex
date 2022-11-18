package uz.enterprise.mytex.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.enterprise.mytex.common.Generated;
import uz.enterprise.mytex.constant.AppConstants;
import uz.enterprise.mytex.exception.CustomException;
import uz.enterprise.mytex.exception.FileException;
import uz.enterprise.mytex.helper.ResponseHelper;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

import static uz.enterprise.mytex.util.ErrorUtil.getStacktrace;

@Slf4j
@Service
public class MediaService {
    private final ResponseHelper responseHelper;
    private final PropertyService propertyService;
    private final MinioClient minioClient;

    @Generated
    public MediaService(ResponseHelper responseHelper,
                        PropertyService propertyService,
                        MinioClient minioClient) {
        this.responseHelper = responseHelper;
        this.propertyService = propertyService;
        this.minioClient = minioClient;
    }


    public ResponseEntity<?> upload(MultipartFile file) throws FileException{
        try {
            String filePath = uploadResource(file);
            return responseHelper.success(Map.of("filePath", filePath));
        } catch (Exception e) {
            log.error("failed to upload file: {}", getStacktrace(e));
            throw new FileException(responseHelper.operationFailed());
        }
    }


    public void removeResource(String filePath) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs
                        .builder()
                        .bucket(propertyService.getValue(AppConstants.BUCKET))
                        .object(filePath)
                        .build());
    }

    public String uploadResource(MultipartFile file) throws Exception {
        String bucket = propertyService.getValue(AppConstants.BUCKET);
        var objectName = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(bucket)
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());
        return propertyService.getMinioEndpoint() + "/" + bucket + "/" + objectName;
    }
}
