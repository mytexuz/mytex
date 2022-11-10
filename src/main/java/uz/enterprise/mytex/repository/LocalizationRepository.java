package uz.enterprise.mytex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.enterprise.mytex.entity.Localization;
import uz.enterprise.mytex.enums.Lang;

public interface LocalizationRepository extends JpaRepository<Localization, Long> {
    Optional<Localization> findByKeyAndLang(String key, Lang lang);

    Optional<Localization> findByMessageAndLang(String message, Lang lang);

    Optional<Localization> findByMessage(String message);
}
