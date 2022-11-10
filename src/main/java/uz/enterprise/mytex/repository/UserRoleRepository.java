package uz.enterprise.mytex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.enterprise.mytex.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
