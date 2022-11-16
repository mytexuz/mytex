package uz.enterprise.mytex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.enterprise.mytex.entity.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query(nativeQuery = true,
            value = "select case when count(s) > 0 then true else false end from sessions s where s.user_id=:userId")
    Boolean existsSessionByUserId(@Param("userId") Long userId);

    @Query(nativeQuery = true,
            value = "select s.* from sessions s where s.user_id=:userId")
    Optional<Session> findSessionByUserId(Long userId);
}
