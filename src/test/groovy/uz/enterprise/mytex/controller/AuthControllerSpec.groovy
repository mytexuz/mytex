package uz.enterprise.mytex.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.dto.LoginDto
import uz.enterprise.mytex.dto.TokenResponseDto
import uz.enterprise.mytex.exception.GlobalExceptionHandler
import uz.enterprise.mytex.service.UserService

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class AuthControllerSpec extends BaseSpecification {
    private GlobalExceptionHandler handler = Mock()
    private UserService userService = Mock()
    private MockMvc mockMvc
    private def baseUrl = "/api/v1/auth"

    void setup() {
        def authController = new AuthController(userService)
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(handler)
                .build()
    }

    def "test authentication -> success"() {
        given:
        def loginDto = new LoginDto("awesome", "123")
        def requestBody = new ObjectMapper().writeValueAsString(loginDto)
        def token = "AAKSNKANSKASNAKJNSAKNAK232NK23NK2NK"

        def url = "$baseUrl/login"

        def time = "14.11.2022 10:20:24"
        def successResponse = new ResponseEntity([
                data     : new TokenResponseDto(token, loginDto.usernameOrEmail),
                message  : "Successfully",
                timestamp: time
        ], HttpStatus.OK)

        def expectedResponse = '''
            {
                "data":{
                        "token":"AAKSNKANSKASNAKJNSAKNAK232NK23NK2NK",
                        "fullName":"awesome"
                },
                "message":"Successfully",
                "timestamp":"14.11.2022 10:20:24"      
            }
'''

        when:
        def result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .content(requestBody)
        ).andReturn()

        then:
        1 * userService.login(_ as LoginDto) >> successResponse

        def response = result.response
        response.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(expectedResponse, response.contentAsString, false)
    }
}
