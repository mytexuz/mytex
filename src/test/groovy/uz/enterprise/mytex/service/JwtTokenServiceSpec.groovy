package uz.enterprise.mytex.service


import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.entity.Property
import uz.enterprise.mytex.repository.PropertyRepository
import uz.enterprise.mytex.util.JwtUtil

class JwtTokenServiceSpec extends BaseSpecification {
    private JwtTokenService tokenService
    private PropertyService propertyService
    private PropertyRepository propertyRepository = Mock()
    private JwtUtil jwtUtil = Mock()

    void setup() {
        propertyService = new PropertyService(propertyRepository)
        tokenService = new JwtTokenService(jwtUtil, propertyService)
    }

    def "JsonWebToken generate -> success"(Long id, String key, String value) {
        given:
        def property = new Property(id, key, value)
        def subject = "awesome"
        def token = "AJSNKANSKJANKSJNAKJNSAK"
        when:
        def actualToken = tokenService.generateToken(subject)
        then:
        1 * propertyRepository.findByKey(key) >> Optional.of(property)
        1 * jwtUtil.jwt(subject, value) >> token
        assert !actualToken.isEmpty()
        assert actualToken instanceof String
        assert actualToken == token
        where:
        id | key           | value
        1  | "secretToken" | "U0RGVyQ0MzUzZnNkRyUkXiQlXjxERkhHPE9ZVUslJF4"
    }

    def "JsonWebToken generate -> failed"() {
        when:
        tokenService.generateToken(null)
        then:
        thrown(NullPointerException)
    }

    def "JsonWebToken get subject -> success"(Long id, String key, String value) {
        given:
        def property = new Property(id, key, value)
        def subject = "awesome"
        def token = jwtUtil.jwt(subject, value)
        when:
        def actualSubject = tokenService.subject(token)
        then:
        1 * propertyRepository.findByKey(key) >> Optional.of(property)
        1 * jwtUtil.getSubject(token, value) >> subject
        assert !actualSubject.isEmpty()
        assert actualSubject instanceof String
        assert actualSubject == subject
        where:
        id | key           | value
        1  | "secretToken" | "U0RGVyQ0MzUzZnNkRyUkXiQlXjxERkhHPE9ZVUslJF4"
    }

    def "JsonWebToken get subject -> failed"(Long id, String key, String value) {
        given:
        def property = new Property(id, key, value)
        def subject = "awesome"
        def token = jwtUtil.jwt(subject, value)
        when:
        def actualSubject = tokenService.subject(token)
        then:
        1 * propertyRepository.findByKey(key) >> Optional.of(property)
        1 * jwtUtil.getSubject(token, value) >> "subject"
        assert !actualSubject.isEmpty()
        assert actualSubject instanceof String
        assert actualSubject != subject
        where:
        id | key           | value
        1  | "secretToken" | "U0RGVyQ0MzUzZnNkRyUkXiQlXjxERkhHPE9ZVUslJF4"
    }

    def "JsonWebToken is valid -> success"(Long id, String key, String value) {
        given:
        def property = new Property(id, key, value)
        def token = "NASNASNAKSNAJSNAKN"
        when:
        def isValid = tokenService.isValid(token)
        then:
        1 * propertyRepository.findByKey(key) >> Optional.of(property)
        1 * jwtUtil.isTokenValid(token, value) >> true
        assert isValid
        assert isValid instanceof Boolean
        where:
        id | key           | value
        1  | "secretToken" | "U0RGVyQ0MzUzZnNkRyUkXiQlXjxERkhHPE9ZVUslJF4"
    }

    def "JsonWebToken is valid -> failed"(Long id, String key, String value) {
        given:
        def property = new Property(id, key, value)
        def token = "NASNASNAKSNAJSNAKN"
        when:
        def isValid = tokenService.isValid(token)
        then:
        1 * propertyRepository.findByKey(key) >> Optional.of(property)
        1 * jwtUtil.isTokenValid(token, value) >> false
        assert !isValid
        assert isValid instanceof Boolean
        where:
        id | key           | value
        1  | "secretToken" | "U0RGVyQ0MzUzZnNkRyUkXiQlXjxERkhHPE9ZVUslJF4"
    }
}