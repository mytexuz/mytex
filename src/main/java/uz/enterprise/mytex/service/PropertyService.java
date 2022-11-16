package uz.enterprise.mytex.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.enterprise.mytex.entity.Property;
import uz.enterprise.mytex.repository.PropertyRepository;

import static uz.enterprise.mytex.constant.PropertyConstants.MONITORING_BOT_TOKEN;
import static uz.enterprise.mytex.constant.PropertyConstants.MONITORING_BOT_USERNAME;
import static uz.enterprise.mytex.constant.PropertyConstants.MONITORING_CHAT_ID;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public String getValue(final String key){
        return propertyRepository.findByKey(key)
                .map(Property::getValue)
                .orElse("");
    }

    public String getBotToken(){
        return getValue(MONITORING_BOT_TOKEN);
    }

    public String getBotUsername(){
        return getValue(MONITORING_BOT_USERNAME);
    }

    public String getChatId(){
        return getValue(MONITORING_CHAT_ID);
    }
}
