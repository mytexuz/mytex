package uz.enterprise.mytex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.enterprise.mytex.entity.Permission;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 10:58 AM 10/24/22 on Monday in October
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
