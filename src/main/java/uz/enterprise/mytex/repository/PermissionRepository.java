package uz.enterprise.mytex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.enterprise.mytex.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(nativeQuery = true,
            value = "select p.* from permissions p join role_permissions rp on p.id = rp.permission_id join roles r on r.id = rp.role_id where r.id= :id and p.status='ACTIVE'")
    List<Permission> findAllByRoleId(@Param(value = "id") Long roleId);
}
