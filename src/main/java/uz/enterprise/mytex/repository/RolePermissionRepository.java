package uz.enterprise.mytex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.enterprise.mytex.entity.RolePermission;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

}
