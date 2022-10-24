package uz.enterprise.mytex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.enterprise.mytex.entity.UserGroup;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 4:12 PM 10/24/22 on Monday in October
 */
@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
