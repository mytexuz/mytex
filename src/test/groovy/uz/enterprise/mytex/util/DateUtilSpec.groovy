package uz.enterprise.mytex.util

import uz.enterprise.mytex.BaseSpecification

import static uz.enterprise.mytex.util.DateUtil.time

class DateUtilSpec extends BaseSpecification {
    private DateUtil dateUtil

    void setup() {
        dateUtil = new DateUtil()
    }

    def "getTime() with format -> success"() {
        when:
        def time = getTime()

        then:
        assert !time.isEmpty()
        assert !time.isBlank()
        assert time instanceof String
    }
}
