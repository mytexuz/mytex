package uz.enterprise.mytex.service

import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.entity.Property
import uz.enterprise.mytex.repository.PropertyRepository

class PropertyServiceSpec extends BaseSpecification {
    private PropertyService propertyService
    private PropertyRepository propertyRepository = Mock()

    void setup() {
        propertyService = new PropertyService(propertyRepository)
    }

    def "get value of property by key -> success"(Long id, String key, String value) {
        given:
        def property = new Property(id, key, value)
        when:
        def actual = propertyService.getValue(key)
        then:
        1 * propertyRepository.findByKey(key) >> Optional.of(property)
        assert actual == value
        where:
        id | key             | value
        1  | "TB_USER"       | "users"
        2  | "TB_USER_ROLE"  | "user_roles"
        3  | "TB_DEVICE"     | "devices"
        4  | "TB_PERMISSION" | "permissions"
    }
}