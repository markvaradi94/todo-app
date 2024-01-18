package ch.cern.todo.config.converter;

import ch.cern.todo.exception.model.ValidationException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    private static final List<DateTimeFormatter> FORMATTERS = List.of(
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    );

    @Override
    public LocalDateTime convert(@NonNull String source) {
        return FORMATTERS.stream()
                .flatMap(formatter -> tryParseLocalDateTime(source, formatter).stream())
                .findFirst()
                .orElseThrow(() -> new ValidationException("Invalid date format %s.".formatted(source)));
    }

    private Optional<LocalDateTime> tryParseLocalDateTime(String source, DateTimeFormatter formatter) {
        try {
            return of(LocalDateTime.parse(source, formatter));
        } catch (DateTimeParseException e) {
            return tryParseLocalDate(source, formatter);
        }
    }

    private Optional<LocalDateTime> tryParseLocalDate(String source, DateTimeFormatter formatter) {
        try {
            LocalDate date = LocalDate.parse(source, formatter);
            return of(date.atStartOfDay());
        } catch (DateTimeParseException e) {
            return empty();
        }
    }
}
