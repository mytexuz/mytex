package uz.enterprise.mytex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.enterprise.mytex.entity.RolePermission;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 4:12 PM 10/24/22 on Monday in October
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
