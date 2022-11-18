package uz.enterprise.mytex.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockMultipartFile
import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.dto.response.ResponseData
import uz.enterprise.mytex.entity.Localization
import uz.enterprise.mytex.entity.Property
import uz.enterprise.mytex.enums.Lang
import uz.enterprise.mytex.exception.CustomException
import uz.enterprise.mytex.helper.ResponseHelper

import static uz.enterprise.mytex.util.DateUtil.getTime

class MediaServiceSpec extends BaseSpecification {
    private MediaService mediaService
    private PropertyService propertyService = Mock()
    private ResponseHelper responseHelper = Mock()
    private String mockFolderPath = "./media"

    void setup() {
        mediaService = new MediaService(responseHelper, propertyService, minioClient)
        File mockFolder = new File(mockFolderPath)
        if (!mockFolder.exists()) {
            mockFolder.mkdir()
        }
    }

    def "Upload file -> success"() {
        given:
        def file = random.nextObject(MockMultipartFile)

        def property = random.nextObject(Property)
        property.value = mockFolderPath
        property.key = "FILE_UPLOAD_PATH"

        def localization = new Localization(1, "success", Lang.UZ, "Successful!")
        def filePath = property.value + file.getName()
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
        1 * propertyService.getValue("FILE_UPLOAD_PATH") >> property.value
        def actual = response as ResponseEntity<Map<Object, Object>>

        actual.statusCode == HttpStatus.OK
        actual.body != null

    }

    def "Upload file -> failed"() {
        given:
        def localization = new Localization(1, "invalid.data", Lang.UZ, "Invalid data!")
        def responseData = new ResponseData<>(null, localization.message)
        responseData.setTimestamp(getTime())
        def invalidData = new ResponseEntity(responseData, HttpStatus.UNAUTHORIZED)

        when:
        mediaService.upload(null)

        then:
        1 * responseHelper.invalidData() >> invalidData
        thrown(CustomException)
    }

    def cleanup() {
        File mockFolder = new File(mockFolderPath)
        if (mockFolder.exists()) {
            mockFolder.deleteDir()
        }
    }

}

