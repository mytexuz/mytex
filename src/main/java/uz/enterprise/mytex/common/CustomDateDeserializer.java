package uz.enterprise.mytex.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateDeserializer extends JsonDeserializer<LocalDateTime> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser,
                                     DeserializationContext context) throws IOException {
        var dateTimeStr = jsonParser.getValueAsString();
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}
