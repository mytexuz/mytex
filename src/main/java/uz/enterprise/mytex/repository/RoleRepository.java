package uz.enterprise.mytex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.enterprise.mytex.entity.Role;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 10:57 AM 10/24/22 on Monday in October
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(nativeQuery = true,
            value = "select r.* from roles r join user_roles ur on r.id = ur.role_id join users u on u.id = ur.user_id where u.id= :id")
    Optional<Role> findByUserId(@Param(value = "id") Long userId);
}
