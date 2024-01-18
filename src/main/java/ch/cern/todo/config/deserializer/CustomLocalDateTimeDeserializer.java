package ch.cern.todo.config.deserializer;

import ch.cern.todo.exception.model.ValidationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext context) {
        String dateStr;
        try {
            dateStr = jsonParser.getText();
        } catch (IOException e) {
            throw new ValidationException(e.getLocalizedMessage());
        }

        return LocalDateTime.parse(formatDateString(dateStr), ISO_LOCAL_DATE_TIME);
    }

    private String formatDateString(String dateStr) {
        if (!dateStr.contains("T")) {
            dateStr += "T00:00:00";
        }
        return dateStr;
    }
}
