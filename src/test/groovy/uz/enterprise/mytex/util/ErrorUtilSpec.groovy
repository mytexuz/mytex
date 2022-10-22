package uz.enterprise.mytex.util

import uz.enterprise.mytex.BaseSpecification


class ErrorUtilSpec extends BaseSpecification {
    private ErrorUtil errorUtil

    void setup(){
        errorUtil = new ErrorUtil()
    }

    def "convert exception into string -> success"(){
        when:
        def actual = errorUtil.getStacktrace(new NullPointerException("NullPointer exception thrown for testing"))

        then:
        assert !actual.isEmpty()
        assert !actual.isBlank()
        assert actual instanceof String
    }
}