package uz.enterprise.mytex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.enterprise.mytex.entity.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
