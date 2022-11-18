package uz.enterprise.mytex.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.dto.ChangeStatusDto
import uz.enterprise.mytex.dto.RegisterDto
import uz.enterprise.mytex.dto.UserDto
import uz.enterprise.mytex.dto.request.UserUpdateRequest
import uz.enterprise.mytex.enums.Lang
import uz.enterprise.mytex.enums.Status
import uz.enterprise.mytex.exception.GlobalExceptionHandler
import uz.enterprise.mytex.service.UserService

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

class UserControllerSpec extends BaseSpecification {
    private GlobalExceptionHandler handler = Mock()
    private UserService userService = Mock()
    private MockMvc mockMvc
    private def baseUrl = "/api/v1/user"

    void setup() {
        def userController = new UserController(userService)
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(handler)
                .build()
    }

    def "Test User registration"() {
        given:
        def registerDto = new RegisterDto("John", "John",
                "+998998653816", "", "jones", "zuh@gmail.team", Lang.UZ)
        def requestBody = new ObjectMapper().writeValueAsString(registerDto)
        def token = UUID.randomUUID().toString()
        def expectedResponse = '''
            {
                "data": {
                      "id": 1,
                      "firstName": "John",
                      "lastName": "Jones",
                      "phoneNumber": "+998998653816",
                      "photo": "",
                      "password": "PFz%L5u0(6",
                      "username": "jones",
                      "email": "zuh@gmail.team",
                      "status": "PENDING",
                      "registeredDate": "10.11.2022 18:49:59"
                      },
                "message": "Successfully",
                "timestamp": "10.11.2022 18:49:59"
            }
'''
        def url = "$baseUrl/register"

        def userDto = new UserDto(1, "John", "Jones",
                "+998998653816", "", "PFz%L5u0(6",
                "jones", "zuh@gmail.team", "PENDING", 'UZ', "10.11.2022 18:49:59")

        def successResponse = new ResponseEntity([
                data     : userDto,
                message  : "Successfully",
                timestamp: "10.11.2022 18:49:59"
        ], HttpStatus.OK)

        when:
        def result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .header("token", token)
                .content(requestBody)
        ).andReturn()

        then:
        1 * userService.register(_ as RegisterDto) >> successResponse

        def response = result.response
        response.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(expectedResponse, response.contentAsString, false)
    }

    def "Test update user's information"() {
        given:
        def updateDto = new UserUpdateRequest(1, "John", "John",
                "+998998653816", "", "jones", "zuh@gmail.team", Lang.UZ)
        def requestBody = new ObjectMapper().writeValueAsString(updateDto)
        def token = UUID.randomUUID().toString()
        def expectedResponse = '''
            {
                "data": {
                      "id": 1,
                      "firstName": "John",
                      "lastName": "Jones",
                      "phoneNumber": "+998998653816",
                      "photo": "",
                      "password": "PFz%L5u0(6",
                      "username": "jones",
                      "email": "zuh@gmail.team",
                      "status": "PENDING",
                      "registeredDate": "10.11.2022 18:49:59"
                      },
                "message": "Successfully",
                "timestamp": "10.11.2022 18:49:59"
            }
'''
        def url = "$baseUrl/update"

        def userDto = new UserDto(1, "John", "Jones",
                "+998998653816", "", "PFz%L5u0(6",
                "jones", "zuh@gmail.team", "PENDING", 'UZ', "10.11.2022 18:49:59")

        def successResponse = new ResponseEntity([
                data     : userDto,
                message  : "Successfully",
                timestamp: "10.11.2022 18:49:59"
        ], HttpStatus.OK)

        when:
        def result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .header("token", token)
                .content(requestBody)
        ).andReturn()

        then:
        1 * userService.update(_ as UserUpdateRequest) >> successResponse

        def response = result.response
        response.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(expectedResponse, response.contentAsString, false)
    }

    def "Test change user's status"() {
        given:
        def updateDto = new ChangeStatusDto(1, Status.ACTIVE)
        def requestBody = new ObjectMapper().writeValueAsString(updateDto)
        def token = UUID.randomUUID().toString()
        def expectedResponse = '''
            {
                "data": {
                      "status": "ACTIVE",
                      "userId": 1
                },
                "message": "Successfully",
                "timestamp": "10.11.2022 18:49:59"
            }
'''
        def url = "$baseUrl/change-status"

        def data = [
                status: "ACTIVE",
                userId: 1
        ]

        def successResponse = new ResponseEntity([
                data     : data,
                message  : "Successfully",
                timestamp: "10.11.2022 18:49:59"
        ], HttpStatus.OK)

        when:
        def result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .header("token", token)
                .content(requestBody)
        ).andReturn()

        then:
        1 * userService.changeStatus(_ as ChangeStatusDto) >> successResponse

        def response = result.response
        response.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(expectedResponse, response.contentAsString, false)
    }

    def "Test get user by id"() {
        given:
        def userId = 1
        def token = UUID.randomUUID().toString()
        def expectedResponse = '''
            {
                "data": {
                      "id": 1,
                      "firstName": "John",
                      "lastName": "Jones",
                      "phoneNumber": "+998998653816",
                      "photo": "",
                      "password": "PFz%L5u0(6",
                      "username": "jones",
                      "email": "zuh@gmail.team",
                      "status": "PENDING",
                      "registeredDate": "10.11.2022 18:49:59"
                      },
                "message": "Successfully",
                "timestamp": "10.11.2022 18:49:59"
            }
'''
        def url = "$baseUrl/get?id=$userId"

        def userDto = new UserDto(1, "John", "Jones",
                "+998998653816", "", "PFz%L5u0(6",
                "jones", "zuh@gmail.team", "PENDING",'UZ' ,"10.11.2022 18:49:59")

        def successResponse = new ResponseEntity([
                data     : userDto,
                message  : "Successfully",
                timestamp: "10.11.2022 18:49:59"
        ], HttpStatus.OK)

        when:
        def result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .header("token", token)
        ).andReturn()

        then:
        1 * userService.getUserById(_ as Long) >> successResponse

        def response = result.response
        response.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(expectedResponse, response.contentAsString, false)
    }
}
