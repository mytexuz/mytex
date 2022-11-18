package uz.enterprise.mytex.service

import io.minio.MinioClient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockMultipartFile
import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.constant.AppConstants
import uz.enterprise.mytex.dto.response.ResponseData
import uz.enterprise.mytex.entity.Localization
import uz.enterprise.mytex.enums.Lang
import uz.enterprise.mytex.exception.CustomException
import uz.enterprise.mytex.helper.ResponseHelper

import static uz.enterprise.mytex.util.DateUtil.getTime

class MediaServiceSpec extends BaseSpecification {
    private MediaService mediaService
    private PropertyService propertyService = Mock()
    private ResponseHelper responseHelper = Mock()
    private MinioClient minioClient = Mock()

    void setup() {
        mediaService = new MediaService(responseHelper, propertyService, minioClient)
    }

    def "Upload file -> success"() {
        given: "fake multipart file and localized success message"
        def file = random.nextObject(MockMultipartFile)
        def localization = new Localization(1, "success", Lang.UZ, "Successful!")

        and: "property service that always returns bucket name"
        def bucket = 'mytex'
        1 * propertyService.getValue(AppConstants.BUCKET) >> bucket

        and: "property service that always returns minio endpoint"
        def minioEndpoint = 'https://bucket.minio.asgardia.uz'
        1 * propertyService.getMinioEndpoint() >> minioEndpoint

        and: "construct resulting file path in bucket and response"
        def filePath = minioEndpoint + '/' + bucket + '/' + file.getOriginalFilename()
        def data = [filePath: filePath]
        def successResponse = new ResponseEntity([
                data     : data,
                message  : localization.message,
                timestamp: getTime()
        ], HttpStatus.OK)

        when:
        def response = mediaService.upload(file)

        then:
        1 * responseHelper.success(_ as Map) >> successResponse
        def actual = response as ResponseEntity<Map<Object, Object>>

        actual.statusCode == HttpStatus.OK
        actual.body != null

    }

}

