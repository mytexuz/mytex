package uz.enterprise.mytex.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 8:35 PM 10/29/22 on Saturday in October
 */
@RequiredArgsConstructor
public class BadRequestException extends RuntimeException {
    private final BindingResult bindingResult;

    public List<String> getMessages() {
        return getValidationMessage(this.bindingResult);
    }

    @Override
    public String getMessage() {
        return this.getMessages().toString();
    }

    private static List<String> getValidationMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(BadRequestException::getValidationMessage)
                .collect(Collectors.toList());
    }

    private static String getValidationMessage(ObjectError error) {
        if (error instanceof FieldError fieldError) {
            String message = fieldError.getDefaultMessage();
            return String.format("%s", message);
        }
        return String.format("%s: %s", error.getObjectName(), error.getDefaultMessage());
    }
}
