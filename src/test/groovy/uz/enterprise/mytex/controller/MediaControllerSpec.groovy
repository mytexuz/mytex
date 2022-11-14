package uz.enterprise.mytex.controller

import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.exception.GlobalExceptionHandler
import uz.enterprise.mytex.service.MediaService

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart

class MediaControllerSpec extends BaseSpecification {
    private MediaService mediaService = Mock()
    private GlobalExceptionHandler handler = Mock()
    private MockMvc mockMvc
    private def baseUrl = "/api/v1/media"

    void setup() {
        def mediaController = new MediaController(mediaService)
        mockMvc = MockMvcBuilders.standaloneSetup(mediaController)
                .setControllerAdvice(handler)
                .build()
    }

    def "test upload file -> success"() {
        given:
        def file = random.nextObject(MockMultipartFile)
        def token = "ALSKMALKSMALKMLKMALAMSLAMS"
        def generatedName = "/88787238728728723"
        def filePath = "." + generatedName

        def data = [filePath: filePath]

        def url = "$baseUrl/upload"

        def time = "14.11.2022 10:38:24"

        def successResponse = new ResponseEntity([
                data     : data,
                message  : "Successfully",
                timestamp: time
        ], HttpStatus.OK)

        def expectedResponse = '''
            {
                "data":{
                        "filePath":"./88787238728728723"
                },
                "message":"Successfully",
                "timestamp":"14.11.2022 10:38:24"      
            }
'''

        when:
        def result = mockMvc.perform(multipart(url)
                .file("file", file.bytes)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("Content-Type", "application/form-data")
                .header("token", token)
        ).andReturn()

        then:
        1 * mediaService.upload(_ as MockMultipartFile) >> successResponse

        def response = result.response

        response.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(expectedResponse, response.contentAsString, false)
    }
}
