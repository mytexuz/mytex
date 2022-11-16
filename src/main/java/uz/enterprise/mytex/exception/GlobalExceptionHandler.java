package uz.enterprise.mytex.exception;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.enterprise.mytex.common.bot.MonitoringBot;
import uz.enterprise.mytex.enums.Lang;
import uz.enterprise.mytex.helper.ResponseHelper;
import uz.enterprise.mytex.security.CustomUserDetails;
import uz.enterprise.mytex.service.LocalizationService;
import uz.enterprise.mytex.service.PropertyService;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import static uz.enterprise.mytex.helper.SecurityHelper.getCurrentUser;
import static uz.enterprise.mytex.util.CommonUtil.getFlagByLang;
import static uz.enterprise.mytex.util.DateUtil.getTime;
import static uz.enterprise.mytex.util.ErrorUtil.getStacktrace;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ResponseHelper responseHelper;
    private final LocalizationService localizationService;
    private final PropertyService propertyService;
    private final MonitoringBot monitoringBot;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(final Exception e) throws TelegramApiException {
        var stacktrace = getStacktrace(e);
        logger.error("unexpected error: {}", getStacktrace(e));
        try {
            CustomUserDetails userDetails = getCurrentUser();
            boolean isLoggedIn = !Objects.isNull(userDetails);
            String fullName = isLoggedIn ? userDetails.getFullName() : "Administrator";
            Long userId = isLoggedIn ? userDetails.getId() : -1L;
            String lang = getFlagByLang(isLoggedIn ? userDetails.getLang() : Lang.UZ);
            String currentDateTime = getTime();
            String chatId = propertyService.getChatId();

            var message = new SendMessage();
            message.enableHtml(true);
            message.setChatId(chatId);

            var content = String.format("""
                <b> Monitoring 🔔</b>
                <b> 😎 user id: </b> %s
                <b> 📄 full name: </b> %s
                <b> 🌎 localization: </b> %s
                <b> 📅 current date: </b> %s
                <b> 💻 active profile: </b> %s
               """, userId, fullName, lang, currentDateTime, activeProfile);
            message.setText(content);
            var document = new SendDocument();
            document.setChatId(chatId);
            document.setCaption("Error stacktrace");
            var file = new InputFile();
            file.setMedia(IOUtils.toInputStream(stacktrace, StandardCharsets.UTF_8), "stacktrace.txt");
            document.setDocument(file);
            monitoringBot.execute(message);
            monitoringBot.execute(document);
            return responseHelper.internalServerError();
        } catch (Exception ex) {
            return responseHelper.internalServerError();
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(final BadCredentialsException e) {
        logger.error("bad credentials error: {}", getStacktrace(e));
        return responseHelper.incorrectPassword();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(final AccessDeniedException e) {
        logger.error("access denied error: {}", getStacktrace(e));
        return responseHelper.unauthorized();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxUploadSizeExceededException(final MaxUploadSizeExceededException e) {
        logger.error("max file upload size error: {}", getStacktrace(e));
        return responseHelper.invalidFileSize();
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(final CustomException e) {
        return e.getResponse();
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<?> handleCustomAuthenticationException(final CustomAuthenticationException e) {
        return e.getResponse();
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        Map<Object, Object> validation = new HashedMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    String message = localizationService.getMessage(error.getDefaultMessage());
                    String field = error.getField();
                    validation.put(field, message);
                });
        return responseHelper.prepareValidationResponse(validation);
    }

}
