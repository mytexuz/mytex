package uz.enterprise.mytex.util

import io.jsonwebtoken.SignatureException
import uz.enterprise.mytex.BaseSpecification


class JwtUtilSpec extends BaseSpecification {
    private JwtUtil jwtUtil
    private String secret
    private String subject

    void setup() {
        jwtUtil = new JwtUtil()
        secret = "U0RGVyQ0MzUzZnNkRyUkXiQlXjxERkhHPE9ZVUslJF4+SkhGR0pUUllVJV4="
        subject = "subject"
    }

    def "JsonWebToken generate -> success"() {
        when:
        def token = jwtUtil.jwt(subject, secret)
        then:
        assert !token.isEmpty()
        assert !token.isBlank()
        assert token instanceof String
    }

    def "JsonWebToken generate -> failed"() {
        when:
        jwtUtil.jwt(null, null)
        then:
        thrown(NullPointerException.class)
    }

    def "JsonWebToken get subject -> success"() {
        when:
        def token = jwtUtil.jwt(subject, secret)
        def actualSubject = jwtUtil.getSubject(token, secret)
        then:
        assert !actualSubject.isBlank()
        assert !actualSubject.isEmpty()
        assert actualSubject instanceof String
        assert actualSubject == subject
    }

    def "JsonWebToken get subject -> failed"() {
        when:
        def token = jwtUtil.jwt(subject, secret)
        def subject = jwtUtil.getSubject(token, secret)
        then:
        assert subject != "anotherSubject"
    }

    def "JsonWebToken is valid -> true"() {
        when:
        def token = jwtUtil.jwt(subject, secret)
        def isValid = jwtUtil.isTokenValid(token, secret)
        then:
        assert isValid
        assert isValid instanceof Boolean
    }

    def "JsonWebToken is valid -> false"() {
        when:
        def token = jwtUtil.jwt(subject, secret)
        jwtUtil.isTokenValid(token, "ASOIJAOISJOIAJOIA23HHDISSI")
        then:
        thrown(SignatureException.class)
    }
}
