package uz.enterprise.mytex.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.enterprise.mytex.entity.Localization;
import uz.enterprise.mytex.enums.Lang;
import static uz.enterprise.mytex.helper.SecurityHelper.getCurrentUser;
import uz.enterprise.mytex.repository.LocalizationRepository;
import uz.enterprise.mytex.security.CustomUserDetails;

@Service
@RequiredArgsConstructor
public class LocalizationService {
    private final LocalizationRepository localizationRepository;

    public String getMessage(String key) {
        CustomUserDetails currentUser = getCurrentUser();
        Lang lang = Optional.ofNullable(currentUser).map(CustomUserDetails::getLang).orElse(Lang.UZ);
        return localizationRepository.findByKeyAndLang(key, lang)
                .map(Localization::getMessage)
                .orElse(key);
    }
}
