package uz.enterprise.mytex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.enterprise.mytex.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query(nativeQuery = true,
            value = "select u.* from users u where u.username =:username or u.email =:username")
    Optional<User> findByUsernameOrEmail(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findFirstByFirstNameAndLastName(String firstName, String lastName);
}
