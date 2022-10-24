package uz.enterprise.mytex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.enterprise.mytex.entity.Session;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 10:58 AM 10/24/22 on Monday in October
 */
public interface SessionRepository extends JpaRepository<Session, String> {
}
