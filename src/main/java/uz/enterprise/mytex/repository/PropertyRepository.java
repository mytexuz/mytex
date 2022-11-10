package uz.enterprise.mytex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.enterprise.mytex.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    Optional<Property> findByKey(String key);
}
