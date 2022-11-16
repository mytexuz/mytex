package uz.enterprise.mytex.common.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.enterprise.mytex.service.PropertyService;

@Component
@RequiredArgsConstructor
public class MonitoringBot extends TelegramLongPollingBot {
    private final PropertyService propertyService;


    @Override
    public void onUpdateReceived(Update update) {
        /*
           left empty because monitoring bot does not
           have any business functionality
         */
    }

    @Override
    public String getBotUsername() {
        return propertyService.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return propertyService.getBotToken();
    }
}
