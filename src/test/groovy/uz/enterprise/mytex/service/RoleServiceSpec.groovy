package uz.enterprise.mytex.service

import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.entity.Role
import uz.enterprise.mytex.repository.RoleRepository

class RoleServiceSpec extends BaseSpecification {
    private RoleService roleService
    private RoleRepository roleRepository = Mock()

    void setup() {
        roleService = new RoleService(roleRepository)
    }

    def "Get role by user id -> success"() {
        given:
        def userId = random.nextLong()
        def role = random.nextObject(Role)

        when:
        def actual = roleService.getRoleByUser(userId)

        then:
        1 * roleRepository.findByUserId(userId) >> Optional.of(role)

        actual == role
    }
}
