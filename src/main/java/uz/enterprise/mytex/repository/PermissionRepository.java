package uz.enterprise.mytex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.enterprise.mytex.entity.Permission;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 10:58 AM 10/24/22 on Monday in October
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(nativeQuery = true,
            value = "select p.* from permissions p join role_permissions rp on p.id = rp.permission_id join roles r on r.id = rp.role_id where r.id= :id")
    List<Permission> findAllByRoleId(@Param(value = "id") Long roleId);
}
