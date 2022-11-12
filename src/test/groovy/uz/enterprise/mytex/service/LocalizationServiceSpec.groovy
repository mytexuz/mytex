package uz.enterprise.mytex.service

import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.entity.Localization
import uz.enterprise.mytex.enums.Lang
import uz.enterprise.mytex.repository.LocalizationRepository

class LocalizationServiceSpec extends BaseSpecification {
    private LocalizationService localizationService
    private LocalizationRepository localizationRepository = Mock()

    void setup() {
        localizationService = new LocalizationService(localizationRepository)
    }

    def "localization getMessage by key and language -> success"() {
        given:
        def localization = random.nextObject(Localization)
        def key = "success"
        def lang = Lang.UZ
        localization.key = key

        when:
        def actual = localizationService.getMessage(key)

        then:
        1 * localizationRepository.findByKeyAndLang(key, lang) >> Optional.of(localization)
        assert actual == localization.getMessage()
    }
}
